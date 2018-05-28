<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.mooctest.weixin.manager.Managers" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String date=(String)request.getAttribute("date");
	String openid=(String)request.getAttribute("openid");
	String groupId=(String)request.getAttribute("groupId");
	String quizid=(String)request.getAttribute("quizid");
%>
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
					action="<%=basePath%>q/quiz/submit_quiz_next">
					<input type="hidden" name="openid" value="<%=openid%>">
					<input type="hidden" name="quizid" value="<%=quizid%>">
					<input type="hidden" name="groupId" value="<%=groupId%>">
					<input type="hidden" name="param" id="param">
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
					 <br>题目描述：
						<pre id="quiz_content" style="word-wrap: break-word; color: #333;"></pre>
						<textarea rows="3" name="quizDescription"
							placeholder="请在此处填写题目描述（可选）"></textarea>
					</div>

					<div class="rowElem" id="optionEditDiv">
						<br> <label class="required-label">选项设计</label>
						<p></p>
						<div id="optionsDiv"></div>
						<a href="javascript:void(0)" class="underline ml36"
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
		<a href="javascript:void(0);" class="next_bar" id="nextBtn">下一题</a>
		<a href="javascript:void(0);" class="bottom_bar" id="submitBtn">发布小测</a>
	</div>
	<script type="text/javascript">
	$(function(){
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
	
	function initOptionEditDiv() {
		var initHtml = "<div class='quiz-option'><label>A.</label> <input type='text' name='optionText' placeholder='选项A'></div><div class='quiz-option'><label>B.</label> <input type='text' name='optionText' placeholder='选项B'></div>";
		$('#optionsDiv').html(initHtml);
	}
	
	$('#submitBtn').click(function() {
		$.confirm("你确认发布该小测?","确认发布?",function(){
			$('#param').val('finish');
			$('#quizInfo').submit();},function(){
				//取消操作
			});
	});
	$('#nextBtn').click(function() {
		$('#param').val('next');
		$('#quizInfo').submit();
	});
	</script>
</body>
</html>