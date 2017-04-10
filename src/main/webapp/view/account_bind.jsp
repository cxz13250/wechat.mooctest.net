<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="com.mooctest.weixin.pojo.JSApiTicket,java.util.*,com.mooctest.weixin.util.JsSDKSign,java.io.*,com.mooctest.weixin.manager.WitestManager" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String date = (String) request.getAttribute("date");

	String openid=(String)request.getAttribute("openid");

%>
<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账号绑定</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/quiz_base.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/weui.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/weui2.css" />
<style type="text/css">
#main h1, #main p {
	text-align: center;/*  */
}
.myform-row {
	padding: 5px;
}
</style>
</head>

<body>
	<div id="container">
		<div id="div1">
			<div style="float: right">
				<span style="color: #000; text-align: right;">请输入慕测账号</span>
			</div>
			<div style="clear: both; color: #000;"></div>
		</div>
		<div id="div2">
			<div id="main">
				<h1>账号绑定</h1>
				<form name="form" id="loginForm" style="width:100%;" method="post" action="q/account/check">
					<input type="hidden" value="<%=openid%>" name="openid">					
					<div class="weui_cells weui_cells_form">
						<div class="weui_cell" align="center">
							<input type="radio" value="0" name="type" checked>&nbsp;学生&nbsp;&nbsp;
							<input type="radio" value="1" name="type">&nbsp;老师
						</div>
						<div class="weui_cell">
							<div class="weui_cell_hd"><label class="weui_label">账号</label></div>
							<div class="weui_cell_bd weui_cell_primary">
								<input class="weui_input" type="text" required placeholder="请输入慕测账号" name="username">
							</div>
						</div>
						<div class="weui_cell">
							<div class="weui_cell_hd"><label class="weui_label">密码</label></div>
							<div class="weui_cell_bd weui_cell_primary">
								<input class="weui-input" type="password" required placeholder="请输入慕测账号密码" name="password">
							</div>
						</div>
						<div class="myform-row">
							<input class="weui-btn weui-btn_primary" type="submit" value="账号登录">
						</div>
					</div>
				</form>

			</div>
		</div>
	</div>


</body>


</html>