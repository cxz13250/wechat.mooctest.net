<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.mooctest.weixin.entity.Accountinfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String openid=(String)request.getAttribute("openid");
 	String title=(String)request.getAttribute("title");
 	
 	Accountinfo accountinfo=(Accountinfo)request.getAttribute("accouninfo");
 	String email=accountinfo.getEmail()!=null?accountinfo.getEmail():"无";
 	String mobile=accountinfo.getMobile()!=null?accountinfo.getMobile():"无";
 	String level=accountinfo.getLevel()==1?"教师用户":"普通用户";
 	String name=accountinfo.getName()!=null?accountinfo.getName():"无";
 	String company=accountinfo.getCompany()!=null?accountinfo.getCompany():"无";
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
	<link rel="stylesheet" href="http://cdn.bootcss.com/weui/1.1.1/style/weui.min.css">
	<link rel="stylesheet" href="http://cdn.bootcss.com/jquery-weui/1.0.1/css/jquery-weui.min.css">
	<link rel="stylesheet" href="<%=basePath%>css/rokg.css">
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
				<table class="weui-table">
				<tbody>
				<tr><td width=50%>账号邮箱</td>
					<td width=50%><%=email%></td>
				</tr>
				<tr><td width=50%>账号手机</td>
					<td width=50%><%=mobile%></td>
				</tr>
				<tr><td width=50%>账号等级</td>
					<td width=50%><%=level%></td>
				</tr>
				</tbody>
				</table>
				<h1>基本信息</h1>
				<table class="weui-table">
				<tbody>
				<tr><td width=50%>姓名</td>
					<td width=50%><%=name%></td>
				</tr>
				<tr><td width=50%>单位</td>
					<td width=50%><%=company%></td>
				</tr>
				</tbody>
				</table>
			</div>
			<div class="myform-row">
            <a href="javascript:;" class="weui-btn weui-btn_primary" id="sd2" onclick="toUnBindAccount()">解除绑定</a>
        	</div>
		</div>
	</div>
<script type="text/javascript">
	function toUnBindAccount() {
		$.confirm("您确定要解除绑定?","确认解除?",function(){
            window.location.href='<%=basePath%>q/account/cancel?openid=<%=openid%>';
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