// TODO: 依赖于jAlert，挪出去改成errorCallback
// TODO: 依赖于btn样式class，单独定义插件的css文件
// written by lyn
//由于本项目使用的jquery-1.9.1跟lyn使用的版本不一样，所以部分函数所使用的live()方法，必须改为on()方法

(function($) {
	// ================================
	// global helper
	// ================================
	window.myGlobal = $.extend({
		// 动画
		// =======================
		scrollTo : function($el) {
			$('html,body').animate({
				scrollTop : $el.offset().top
			}, 'normal');
		},

		scrollBottom : function() {
			// FUCK height计算
			$('html,body').animate({
				scrollTop : $(document).height()
			}, 'normal');
		},

		innerScrollTop : function($parent) {
			$parent.animate({
				scrollTop : 0
			}, 'normal');
		},

		innerScrollBottom : function($parent) {
			var $target = $parent.children().last();
			var dist = $target.offset().top + $target.height()
					- ($parent.offset().top + $parent.height());
			$parent.animate({
				scrollTop : $parent.scrollTop() + dist
			}, 'normal');
		},

		removeFade : function($el, callback) {
			$el.fadeOut('normal', function() {
				$(this).remove();
				callback && callback();
			});
		},

		// 数据格式
		// =======================
		/*
		 * return Date 值合法时 undefined 值缺失或不合法
		 */
		parseDateTimeGroup : function(dateString, hh, mm) {
			if (myGlobal.notEmpty(dateString) && myGlobal.notEmpty(hh)
					&& myGlobal.notEmpty(mm)) {
				return new Date(dateString.replace(/-/g, '/') + ' ' + hh + ':'
						+ mm);
			}
			return undefined;
		},

		generateDateTimeGroup : function(date) {
			if (myGlobal.notEmpty(date)) {
				// NOTE: 括号不能少！
				return [
						date.getFullYear()
								+ '-'
								+ ((date.getMonth() < 9) ? ('0' + (date
										.getMonth() + 1))
										: (date.getMonth() + 1))
								+ '-'
								+ ((date.getDate() < 10) ? ('0' + date
										.getDate()) : date.getDate()),
						(date.getHours() < 10) ? ('0' + date.getHours()) : date
								.getHours(),
						(date.getMinutes() < 10) ? ('0' + date.getMinutes())
								: date.getMinutes() ];
			}
			return [ '', '', '' ];
		},

		/*
		 * return String format: yyyy-MM-dd HH:mm undefined 值缺失或不合法
		 */
		getDateTimeString : function(dateString, hh, mm) {
			var date = myGlobal.parseDateTimeGroup(dateString, hh, mm);
			if (myGlobal.notEmpty(date)) {
				return dateString + ' ' + hh + ':' + mm;
			}
			return undefined;
		},

		compareDate : function(date1, date2) {
			var compare = Date.parse(date1) - Date.parse(date2);
			return compare == 0 ? 0 : (compare < 0 ? -1 : 1);
		},

		calTimeInterval : function(fromDate, toDate) {
			var millisecond = Date.parse(toDate) - Date.parse(fromDate);
			// 毫秒 -> 分钟
			return (millisecond / 1000) / 60;
		},

		addTimeInterval : function(fromDate, minutes) {
			return new Date(fromDate.getTime() + minutes * 60 * 1000);
		},

		floatToPercent : function(val) {
			return Math.round(val * 100) + '%';
		},

		// 通用
		// =======================
		notEmpty : function(val) {
			if (typeof val === 'string') {
				return $.trim(val).length > 0;
			}
			if (typeof val === 'number' || typeof val === 'boolean'
					|| typeof val === 'function') {
				return true;
			}
			if (typeof val === 'object') {
				// TODO: empty {} []
				return true;
			}
			return false;
		},

		isEmpty : function(val) {
			return !myGlobal.notEmpty(val);
		},

		objectKeys : function(obj) {
			// 标准浏览器已实现
			if (Object.keys) {
				return Object.keys(obj);
			}

			var keys = [];
			for ( var key in obj) {
				obj.hasOwnProperty(key) && keys.push(key);
			}
			return keys;
		},

		quickSort : function(array) {
			if (array.length <= 1) {
				return array;
			}

			var pivotIndex = Math.floor(array.length / 2);
			var pivot = array.splice(pivotIndex, 1)[0];
			var left = [];
			var right = [];

			for (var i = 0; i < array.length; i++) {
				if (array[i] < pivot) {
					left.push(array[i]);
				} else {
					right.push(array[i]);
				}
			}

			// NOTE: 这里访问不到自身变量，而var quickSort = function(){}的写法可以
			return arguments.callee(left).concat([ pivot ],
					arguments.callee(right));
		},

		arrayIntersect : function(array1, array2) {
			var result = [];
			for (var i = 0; i < array1.length; i++) {
				for (var j = 0; j < array2.length; j++) {
					if (array1[i] == array2[j]) {
						result.push(array1[i]);
						break;
					}
				}
			}
			return result;
		},

		codeReplace : function(code) {
			return code.replace('>', '&gt;').replace('<', '&lt;').replace(
					/\n|\r/g, '<br>')
					.replace(/\t/g, '&nbsp;&nbsp;&nbsp;&nbsp;');
		},

		// 事件处理
		// =======================
		inputNumAllowed : function() {
			$(this).val($(this).val().replace(/\D/g, ''));
		},

		stopPropagation : function(event) {
			if (event && event.stopPropagation) {
				event.stopPropagation();
			} else {
				window.event.cancelBubble = true;
			}
		},

		preventDefault : function(event) {
			if (event && event.preventDefault)
				event.preventDefault();
			else
				window.event.returnValue = false;
			return false;
		},

		xxxxxx : function() {

		}
	}, window.myGlobal);

	// ================================
	// 自定义添加条目数目
	// ================================
	// 用法 demo
	// <div id="parentDiv">
	// <div class="children-item">
	// <!-- label元素以及input select等 -->
	// </div>
	// </div>
	// $('#parentDiv').myCustomAppend({
	// addLink: '#yourAddItemLink',
	// numLabel: 'label', // parentDiv的每个children中如何找到label
	// // 即$('#parentDiv').children().find(numLabel)
	// numText: 'alphabet', // label格式 'alphabet'或'number' optional
	// (default='number')
	// maxNum: 10, // 最大条目数 optional (default=10)
	// minNum: 2, // 最小条目数 optional (default=1)
	// initNum: 2 // 初始条目数 optional (default=children数)
	// });
	// ================================
	$.fn.myCustomAppend = function(options) {
		var CLASS_REMOVE_LINK = 'myCustomAppend-remove-link';
		var TEXT_REMOVE = '删除';// window.LANG_TEXT.DELETE;
		var TEXT_HINT = 'Hint';// window.LANG_TEXT.HINT;

		var initChildrenNum = this.children().length;
		var $removeCopy = $('<a href="javascript:void(0)" class="'
				+ CLASS_REMOVE_LINK + '">' + TEXT_REMOVE + '</a>');

		var opts = $.extend({
			numText : 'number',
			maxNum : 10,
			minNum : 1,
			initNum : initChildrenNum
		}, options);

		// storage: 数目状态
		var curNum;

		var getNextLabel = function($el) {
			var temp = $el.children().last().find(opts.numLabel).text();
			var curLabel = temp.split('.')[0];

			// 字母序
			if (opts.numText === 'alphabet') {
				return String.fromCharCode(curLabel.charCodeAt(0) + 1) + '.';
			}
			// 数字序
			else if (opts.numText === 'number') {
				return (Number(curLabel) + 1) + '.';
			}
			// 无序号
			else {
				return '';
			}
		};

		var initItemInput = function($item) {
			$item.find('input').each(function() {
				$(this).val('');
			});
			// TODO: select, checkbox, radio, textarea
		};

		var addChildren = function($el) {
			if (curNum >= opts.maxNum) {
				alert("超出最大选项数目");
				// jAlert(window.LANG_TEXT.MAX_ITEMS(opts.maxNum), TEXT_HINT);
				return false;
			}

			var $copy = $el.children().last().clone();

			var nextLabel = getNextLabel($el);
			$copy.find(opts.numLabel).text(nextLabel);
			initItemInput($copy);

			$copy.find('input').attr('placeholder',
					'选项' + nextLabel.replace('.', ''));

			// 添加removeLink
			if (curNum >= opts.minNum
					&& $copy.find('.' + CLASS_REMOVE_LINK).length == 0) {
				$copy.append($removeCopy);
			}

			$el.append($copy);
			curNum++;
		};

		var removeChildren = function($el, $item) {
			var removeIndex = $item.index();
			var $childrens = $el.children();

			// label逐个交换
			for (var i = curNum - 1; i >= removeIndex + 1; i--) {
				$childrens.eq(i).find(opts.numLabel).text(
						$childrens.eq(i - 1).find(opts.numLabel).text());
				$childrens.eq(i).find('input').attr(
						'placeholder',
						'选项'+ $childrens.eq(i - 1).find(opts.numLabel)
						.text().replace('.', ''));
			}
			$item.remove();
			curNum--;
		};

		var init = function($el) {
			var childrenNum = $el.children().length;
			// 保持数量一致
			if (childrenNum < opts.initNum) {
				// TODO: addChildren补齐
			}
			if (childrenNum > opts.initNum) {
				// TODO: removeChildren一致
			}

			// 数目状态
			curNum = opts.initNum;

			// 添加removeLink
			$el.children().each(function(i) {
				if (i + 1 > opts.minNum) {
					$(this).append($removeCopy.clone());
				}
			});

			$el.off('click', '.' + CLASS_REMOVE_LINK);
			// removeLink绑定
			$el.on('click', '.' + CLASS_REMOVE_LINK, function() {
				removeChildren($el, $(this).parent());
			});

			// addLink绑定
			$(opts.addLink).unbind('click').bind('click', function() {
				addChildren($el);
			});
		};

		// NOTE: throw Error会阻止其他js执行
		if (this.children().length == 0) {
			// throw new Error('No custom item template to append.');
		} else if (!opts.addLink) {
			// throw new Error('No trigger element.');
		} else {
			return init(this);
		}
	};

})(jQuery);
