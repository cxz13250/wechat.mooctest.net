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
<title>加入群组</title>
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
				<span style="color: #000; text-align: right;"></span>
			</div>
			<div style="clear: both; color: #000;"></div>
		</div>


		<div id="div2">
			<div id="main">
				<h1>加入群组</h1>

				<form name="form" id="join" style="width:100%;" method="post" action="<%=basePath%>q/group/join">
					<div class="weui-cells weui-cells_form">
						<input type="hidden" value="<%=openid%>" name="openid">
						<div class="weui-cell">
							<div class="weui-cell__hd"><label class="weui-label">群主姓名</label></div>
							<div class="weui-cell__bd weui-cell_primary">
								<input class="weui-input" type="text" required placeholder="请输入群主姓名" id="managerName" name="managerName">
							</div>
						</div>
						<div class="weui-cell">
							<div class="weui-cell__hd"><label class="weui-label">群组编号</label></div>
							<div class="weui-cell__bd weui-cell_primary">
								<input class="weui-input" type="password" required placeholder="请输入群组编号" id="groupId" name="groupId">
							</div>
						</div>
						<div class="myform-row">
							<a href="javascript:;" class="weui-btn weui-btn_primary" id="sd2" onclick="toJoinGroup()">加入群组</a>
						</div>
					</div>
				</form>


			</div>
		</div>
	</div>
<script type="text/javascript">
	function toJoinGroup() {
        $.confirm("您确定要加入此群组?", "确认加入?", function() {
            var form=document.getElementById("join");
            form.submit();
        }, function() {
            //取消操作
        });
	}
</script>
	<script src="http://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
	<script src="http://cdn.bootcss.com/jquery-weui/1.0.1/js/jquery-weui.min.js"></script>
	<script src="http://cdn.bootcss.com/jquery-weui/1.0.1/js/swiper.min.js"></script>
	<script src="http://cdn.bootcss.com/jquery-weui/1.0.1/js/city-picker.min.js"></script>
</body>

</html>