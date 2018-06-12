<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.mooctest.weixin.manager.Managers" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String openid=(String)request.getAttribute("openid");

%>
<head>
<%
	String basePath = Managers.config.getBaseUrl();
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账号绑定</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<link rel="stylesheet" href="http://cdn.bootcss.com/weui/1.1.1/style/weui.min.css">
	<link rel="stylesheet" href="http://cdn.bootcss.com/jquery-weui/1.0.1/css/jquery-weui.min.css">
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
				<form name="form" id="loginForm" style="width:100%;" method="post" action="<%=basePath%>q/account/check">
					<input type="hidden" value="<%=openid%>" name="openid">					
					<div class="weui-cells weui-cells_form">
						<div class="weui-cell" align="center">
							<input type="radio" value="0" name="type" checked>&nbsp;学生&nbsp;&nbsp;
							<input type="radio" value="1" name="type">&nbsp;老师
						</div>
						<div class="weui-cell">
							<div class="weui-cell__hd"><label class="weui-label">账号</label></div>
							<div class="weui-cell__bd weui-cell_primary">
								<input class="weui-input" type="text" required placeholder="请输入慕测账号" name="username">
							</div>
						</div>
						<div class="weui-cell">
							<div class="weui-cell__hd"><label class="weui-label">密码</label></div>
							<div class="weui-cell__bd weui-cell_primary">
								<input class="weui-input" type="password" required placeholder="请输入慕测账号密码" name="password">
							</div>
						</div>
						<div class="myform-row">
							<a href="javascript:;" class="weui-btn weui-btn_primary" id="sd2" onclick="toBindAccount()">绑定账号</a>
						</div>
					</div>
				</form>

			</div>
		</div>
	</div>
<script type="text/javascript">
	$(function(){
	    console.log('<%=basePath%>');
	});
	function toBindAccount() {
        $.confirm("您确定要绑定此账号?","确认绑定?",function(){
            $('#loginForm').submit();
        },function(){

        });
    }
</script>
	<script src="http://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
	<script src="http://cdn.bootcss.com/jquery-weui/1.0.1/js/jquery-weui.min.js"></script>
	<script src="http://cdn.bootcss.com/jquery-weui/1.0.1/js/swiper.min.js"></script>
	<script src="http://cdn.bootcss.com/jquery-weui/1.0.1/js/city-picker.min.js"></script>
</body>


</html>