<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="com.mooctest.weixin.pojo.JSApiTicket,com.mooctest.weixin.util.JsSDKSign,java.util.*"%>
<%@ page import="com.mooctest.weixin.entity.TaskInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String title="我的任务";
	List<TaskInfo> list=(List<TaskInfo>)request.getAttribute("list");
%>
<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=title%></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/weui.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/weui2.css" />
	<script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.js"></script>

</head>
<body>
	<div id="container">
		<div id="div1">
			<h1 class="weui-header-title">任务列表</h1>
		</div>
		<div id="div2">
			<div id="main">
				<table border='0' width=100% class="weui-table weui-border-tb">
					<thead>
					<tr><td width=60%>任务名称</td>
						<td width=40%>任务密码</td></tr>
					</thead>
					<tbody>
					<% for(TaskInfo name:list){%>
					<tr><td width=60%><%=name.getTaskName()%></td>
						<td width=40%><%=name.getPassword()%></td>
					</tr>
					<%}%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<br>
	<br>
	<br>
</body>

</html>