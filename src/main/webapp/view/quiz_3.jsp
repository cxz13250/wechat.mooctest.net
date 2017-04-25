<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	import="java.util.*,java.io.*,com.mooctest.weixin.model.PreparedQuiz,com.mooctest.weixin.model.QuizItem,java.sql.Timestamp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>课堂小测</title>
<link rel="stylesheet" type="text/css" href="css/quiz_base.css" />
<style type="text/css">
div#head h2 {
	text-align: left;
}
</style>
</head>

<body>
	<br>
	<%
		//List<PreparedQuiz> list = test.getQuizzes();
		//out.write("size:" + list.size());
		QuizItem quiz = (QuizItem) request.getAttribute("quiz");
		int quizType = (int)quiz.getType();
		//out.write("TestInfo:" + quiz.getQuizTitle());
		String date = (String) request.getAttribute("date");
	%>
	<div id="container">

		<div id="div1">
			<div style="float: left">
				<span style="color: #49b7e8; text-align: left;"><%=date%></span>
			</div>
			<div style="float: right">
				<span style="color: #000; text-align: right;">问答题</span>
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
				<p>
					“<%=quiz.getContent()%>”
				</p>
			</div>
			<div id="main">
				<form name="form" method="post" action="q/quiz/submit_answer">
				<input type="hidden" name="quiz_type" value="<%= quizType %>">
				<input type="hidden" name="openid" value="<%=quiz.getWorOpenId()%>">

					<div>
						 <textarea placeholder="填写您的回答" name="answer" id="quiz_answer" style='border: 1px solid #94BBE2;width:100%;' rows=5 onpropertychange='this.style.posHeight=this.scrollHeight' id=textarea onfocus='textarea.style.posHeight=this.scrollHeight'></textarea>
					</div>

					<div>
						<br> <a href="javascript:form.submit();"
							class="submit_button">提交回答</a> <br> <br> <br>
					</div>
				</form>
			</div>
		</div>


	</div>
	<div id="div3">
		<p style="color:white;">请完成回答后点击<span style="color:red;">提交回答</span>按钮</p>
	</div>
</body>

</html>