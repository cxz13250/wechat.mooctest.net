<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	import="java.util.*,java.io.*"%>

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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet" type="text/css" href="../css/quiz_base.css" />
<title>慕测平台</title>
<style type="text/css">
#main h1, #main p {
	text-align: center;
}
</style>
</head>

<body>
	<div id="container">
		<div id="div1">
			<div style="float: left">
				<span style="color: #49b7e8; text-align: left;"></span>
			</div>
			<div style="float: right">
				<span style="color: #000; text-align: right;"></span>
			</div>
			<div style="clear: both; color: #000;"></div>
		</div>
		<div id="div2">
			<div id="main">
				<h1>请关闭此页面</h1>
			</div>
		</div>
	</div>
	<div id="div3">
		<p style="color: white;">
			
		</p>
	</div>
</body>

</html>