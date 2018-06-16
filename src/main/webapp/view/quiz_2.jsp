<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mooctest.weixin.manager.Managers" %>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	import="java.util.*,java.io.*,com.mooctest.weixin.model.PreparedQuiz,com.mooctest.weixin.model.QuizItem,java.sql.Timestamp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<%
	String basePath = Managers.config.getBaseUrl();
%>
<base href="<%=basePath%>">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>课堂小测</title>

<style type="text/css">
div#head h2 {
	text-align: left;
}
</style>
</head>
<jsp:include page="header.jsp" flush="true"></jsp:include>
<body>
	<br>
	<%
		QuizItem quiz = (QuizItem) request.getAttribute("quiz");
		int quizType = quiz.getType();
		String date = (String) request.getAttribute("date");
		List<String> options = (List<String>) request.getAttribute("options");
		String index=(String)request.getAttribute("index");
	%>
	<div id="container">

		<div id="div1">
			<div style="float: left">
				<span style="color: #49b7e8; text-align: left;"><%=date%></span>
			</div>
			<div style="float: right">
				<span style="color: #000; text-align: right;">多选题</span>
			</div>
			<div style="clear: both; color: #000;"></div>
		</div>

		<div id="div2">
			<div id="head">
				<h1 style="color: #000;"></h1>
			</div>
			<div id="description">
				<p>
					“<%=quiz.getTitle()%>”
				</p>
			</div>
			<div id="main">
				<form id="answer" name="form" method="post" action="<%=basePath%>q/quiz/submit_answer">
				<input type="hidden" name="quiz_type" value="<%= quizType %>">
				<input type="hidden" name="openid" value="<%=quiz.getWorOpenId()%>">
				<input type="hidden" name="index" value="<%=index%>">
				<input type="hidden" name="param" id="param">
				<div>
						<input type="checkbox" name="answer" id="quiz_<%=0%>"
							value="A" checked> <label for="quiz_<%=0%>"><%="A" + ". " + options.get(0)%></label>					
					<%
						for (int i = 1; i < options.size(); i++) {
							String optionValue = String.valueOf((char) ('A' + i));
							String optionName = options.get(i);
							System.out.println(optionValue + ". " + optionName);
					%>

					<input type="checkbox" name="answer" id="quiz_<%=i%>"
							value="<%=optionValue%>"> <label for="quiz_<%=i%>"><%=optionValue + ". " + optionName%></label>
					</div>
					<%
						}
					%>
					<div>
						<a href="javascript:void(0);" class="weui_btn weui_btn_primary" id="nextBtn">下一题</a>
						<a href="javascript:void(0);" class="weui_btn weui_btn_primary" id="submitBtn">提交回答</a>
					</div>
				</form>
			</div>
		</div>


	</div>
	<div id="div3">
		<p style="color:white;">请完成回答后点击<span style="color:red;">提交回答</span>按钮</p>
	</div>
	<script type="text/javascript">
	$(function() {
		var judge=document.getElementById("judge").value;
		if(judge.trim()=="0"){
			$('#nextBtn').hide();
		}else{
			$('#submitBtn').hide();
		}
	});
	$('#submitBtn').click(function() {
	    $.confirm("您确定要提交回答?", "确认提交?", function() {
	    	$('#param').val('finish');
			$('#answer').submit();
	    }, function() {
	      //取消操作
	    });
	  });
		
		$('#nextBtn').click(function() {
			$('#param').val('next');
			$('#answer').submit();
		});
	</script>
</body>

</html>