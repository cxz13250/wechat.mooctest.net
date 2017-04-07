<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String openid=(String)request.getAttribute("openid");
 	String title=(String)request.getAttribute("title");
%>
<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title><%=title%></title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/quiz_base.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/weui.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/weui2.css" />
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<style type="text/css">
#main h1, #main p {
	text-align: center;
}
.myform-row {
	padding: 5px;
}
</style>
</head>
<body ontouchstart style="background-color: #f8f8f8;">
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
				<h1>账号信息</h1>
				<table class="weui-table weui-border-tb">
				<tbody>
				<tr><td width=50%>账号邮箱</td>
					<td width=50%>无</td>
				</tr>
				<tr><td width=50%>账号手机</td>
					<td width=50%>无</td>
				</tr>
				<tr><td width=50%>账号等级</td>
					<td width=50%>无</td>
				</tr>
				</tbody>
				</table>
				<h1>基本信息</h1>
				<table class="weui-table weui-border-tb">
				<tbody>
				<tr><td width=50%>姓名</td>
					<td width=50%>无</td>
				</tr>
				<tr><td width=50%>单位</td>
					<td width=50%>无</td>
				</tr>
				</tbody>
				</table>
			</div>
			<div class="myform-row">
            <a href='<%=basePath%>q/account/cancel?openid=<%=openid%>' class="weui-btn weui-btn_primary">解除绑定</a>    	
        	</div>
		</div>
	</div>
	<br>
	<br>
	<br>
</body>
</html>