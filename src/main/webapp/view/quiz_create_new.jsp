<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mooctest.weixin.manager.Managers" %>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	import="java.util.*,java.io.*,com.mooctest.weixin.data.Group,com.mooctest.weixin.manager.Managers,com.mooctest.weixin.model.PreparedQuiz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<%
	String basePath = Managers.config.getBaseUrl();
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet" type="text/css" href="css/quiz_base.css" />
<link rel="stylesheet" href="http://weixin.yoby123.cn/weui/style/weui.css"/>
<link rel="stylesheet" href="http://weixin.yoby123.cn/weui/style/weui2.css"/>
<link rel="stylesheet" href="http://weixin.yoby123.cn/weui/style/weui3.css"/>
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script type="text/javascript" src="js/application-util.js"></script>
<script src="http://weixin.yoby123.cn/weui/zepto.min.js"></script>
<title>创建小测</title>
<style type="text/css">
a {
	text-decoration: none;
	color: #2b6893;
}

.ml36 {
	margin-left: 36px;
}

.quiz-option {
	margin-bottom: 10px;
}

.quiz-option label {
	margin-right: 20px;
	display: inline-block;
}

.quiz-option input[type=text] {
	width: 50%;
}

.myCustomAppend-remove-link {
	margin-left: 10px;
}

textarea {
	width: 100%;
	overflow-y: scroll;
}
</style>
</head>

<body>
	<%
		String openid = (String) request.getAttribute("openid");
		String date = (String) request.getAttribute("date");
	%>
	<div id="container">
		<div id="div1">
			<div style="float: left">
				<span style="color: #49b7e8; text-align: left;"><%=date%></span>
			</div>
			<div style="float: right">
				<span style="color: #000; text-align: right;">创建小测</span>
			</div>
			<div style="clear: both; color: #000;"></div>
		</div>

		<div id="div2">
			<div id="main">


				<form id="quizInfo" name="quiz_info" method="post"
					action="q/quiz/submit_quiz">
					<input type="hidden" name="openid" value="<%=openid%>">
					<input type="hidden" name="param" id="param">
					<div>
						<br>选择班级&nbsp; <select id="groupId" name="groupId"
							onchange="getPreparedQuizList(parseInt(this.value));">
							<%
								List<String> groupIdList = (List<String>) request.getAttribute("groupIdList");
								List<String> groupNameList = (List<String>) request.getAttribute("groupNameList");
								for (int i = 0; i < groupIdList.size(); i++) {
									String groupId = groupIdList.get(i);
									String groupName = groupNameList.get(i);
							%>
							<option value="<%=groupId%>"><%=groupName%></option>
							<%
								}
							%>
						</select>
					</div>
					<div>
						<br>选择小测&nbsp; <select name="quizList" id="quizList"
							onchange="getPreparedQuiz(parseInt(this.value));">
							<option value="-1">新的小测</option>
						</select>
					</div>
					<div>
						<br>小测类型&nbsp; <input id="type_single" type="radio" value="1"
							name="quiz_type" checked> <label for="type_single">单选题</label>
						<input id="type_multi" type="radio" value="2" name="quiz_type">
						<label for="type_multi">多选题</label> <input id="type_other"
							type="radio" value="3" name="quiz_type"> <label
							for="type_other">其他</label>
					</div>
					<div>
						<br>小测题目&nbsp; <input type="text" name="quiz_title"
							id="quiz_title" placeholder="老师您想问什么？">
					</div>

					<div id="opt4" style="display: none">
						<input type="hidden" name="quizid" id="quizid"> <br>题目描述：
						<pre id="quiz_content" style="word-wrap: break-word; color: #333;"></pre>
						<textarea rows="3" name="quizDescription"
							placeholder="请在此处填写题目描述（可选）"></textarea>
					</div>

					<div class="rowElem" id="optionEditDiv">
						<br> <label class="required-label">选项设计</label>
						<p></p>
						<div id="optionsDiv"></div>
						<a href="javascript:;" class="underline ml36"
							id="addOptionLink">+添加选项</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<br>
	<br>
	<br>
	<div id="div3">
		<a href="javascript:;" class="next_bar" id="nextBtn">下一题</a>
		<a href="javascript:;" class="bottom_bar" id="submitBtn">发布小测</a>
	</div>

	<script type="text/javascript">
		$(function() {
			init();

			//给选项设计部分添加两个空的选项
			initOptionEditDiv();

			$('#optionsDiv').myCustomAppend({
				addLink : '#addOptionLink',
				numLabel : 'label',
				numText : 'alphabet',
				maxNum : 10,
				minNum : 2,
				initNum : 2
			});

			$('#type_single, #type_multi').click(function() {
				$('#optionEditDiv').show();
				$('#opt4').hide();
			});//单选多选设计内容

			$('#type_other').click(function() {
				$('#optionEditDiv').hide();
				$('#opt4').show();
			});//问答设计内容

		});

		$('#submitBtn').click(function() {
			$.confirm("你确定发布该小测?","确认发布?",function(){
				$('#param').val('finish');
				$('#quizInfo').submit();
			},function(){
				
			});
		});
		
		$('#nextBtn').click(function() {
			$('#param').val('next');
			$('#quizInfo').submit();
		});

		function getPreparedQuizList(classId) {
			var param = {
				'clazzId' : classId,
			};
			$.ajax({
				type : 'GET',
				url : '<%=basePath%>q/quiz/get_prepared_quiz_list_new',
				data : param,
				dataType : 'json',
				beforeSend : function() {
					$.blockUI({
						message : '查询中...'
					});
				},
				complete : function() {
					$.unblockUI();
				},
				success : function(data) {
					//把获取到的预备小测列表显示到下拉框里
					$selectObject = $('#quizList');
					clearSelect($selectObject);
					addItemToSelect($selectObject, "新的小测", "-1");
					for (var i = 0; i < data.length; i++) {
						addItemToSelect($selectObject, data[i]['title'],
								data[i]['id']);
					}
					;
				},
				error : function() {
				}
			});
		}

		function getPreparedQuiz(quizId) {
			if (quizId == -1) {
				var selects = document.getElementsByName("quiz_type");
				for (var i = 0; i < selects.length; i++) {
					selects[i].disabled = false;
				}
				document.getElementById("type_single").checked = true;
				$('#optionEditDiv').show();
				$('#addOptionLink').show();
				$('#opt4').hide();
				$('#optionsDiv').html('');
				initOptionEditDiv();
				document.getElementById("quiz_title").disabled = false;
			} else {
				param = {
					'quizId' : quizId
				};

				$('#optionsDiv').html('');

				$
						.ajax({
							type : 'GET',
							url : '<%=basePath%>q/quiz/get_prepared_quiz_new',
							data : param,
							dataType : 'json',
							beforeSend : function() {
								$.blockUI({
									message : '查询中...'
								});
							},
							complete : function() {
								$.unblockUI();
							},
							success : function(data) {

								$('#quiz_title').val(data['title']);
								$('#quizid').val(quizId);
								var type = data['type'];
								if (type == 3) {
									$('#opt4').show();
									$('#optionEditDiv').hide();
								} else {
									$('#opt4').hide();
									$('#optionEditDiv').show();
								}
								;
								//设置题型选项
								$selects = $('input[name="quiz_type"]');
								for (var i = 0; i < $selects.length; i++) {
									if ($selects[i].value != type) {
										$selects[i].checked = false;
									} else {
										$selects[i].checked = true;
									}
									$selects[i].disabled = true;
								}
								$('#quiz_title').disabled = true;
								//将预备题目里的选项填写到optionDivs里
								var optionContent = data['content'];

								for (var i = 0; i < optionContent.length; i++) {
									var optionChar = String
											.fromCharCode(i + 65);
									$('#optionsDiv')
											.append(
													"<div class='quiz-option'><label>"
															+ optionChar
															+ '.'
															+ "</label> <input type='text' value="+optionContent[i]+" name='optionText' placeholder='请在此处填写题目描述（可选）'></div>");
								}
								;

								var initOptionNum = optionContent.length;
								if (initOptionNum == 0) {
									initOptionNum = 2;
								}

								$('#optionsDiv').myCustomAppend({
									addLink : '#addOptionLink',
									numLabel : 'label',
									numText : 'alphabet',
									maxNum : 10,
									minNum : 2,
									initNum : initOptionNum
								});
								
								$('#addOptionLink').hide();
							},
							error : function() {
								alert("不好意思,网页出错了!请稍候重试");
							}
						});
			}
		}

		function clearSelect(objSelect) {
			objSelect.find("option").remove();
		}

		function addItemToSelect(objSelect, objItemText, objItemValue) {
			var option = $("<option>").text(objItemText).val(objItemValue);
			objSelect.append(option);
		}

		function init() {
			var groupId = document.getElementById("groupId").value;
			getPreparedQuizList(groupId);
		}

		function initOptionEditDiv() {
			var initHtml = "<div class='quiz-option'><label>A.</label> <input type='text' name='optionText' placeholder='选项A'></div><div class='quiz-option'><label>B.</label> <input type='text' name='optionText' placeholder='选项B'></div>";
			$('#optionsDiv').html(initHtml);
		}
	</script>
</body>

</html>