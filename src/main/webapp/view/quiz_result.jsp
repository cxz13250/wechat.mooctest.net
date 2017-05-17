<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	List<String> question=(List<String>)request.getAttribute("questionId");
%>
<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
             + path + "/";
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<link rel="stylesheet" href="http://weixin.yoby123.cn/weui/style/weui.css"/>
	<link rel="stylesheet" href="http://weixin.yoby123.cn/weui/style/weui2.css"/>
	<link rel="stylesheet" href="http://weixin.yoby123.cn/weui/style/weui3.css"/>
<title>小测结果</title>
	<style type="text/css">
		td{
			text-align: center;
		}
		table {
			width: 100%;
			border-collapse: collapse;
			background-color: #fff;
			line-height: 32px;
			text-align: center;
		}
		.table td, .table th {
			border-bottom: 1px solid #e0e0e0;
			border-right: 1px solid #e0e0e0;
			text-align: center; }
	</style>
</head>
<body ontouchstart style="background-color: #f8f8f8;">
	<div id="container">
		<div id="div1" >
			<h1 class="weui-header-title" align="center">题目列表</h1>
		</div> <br/>
		<div id="div2">
			<table style="width: 100%">
				<thead>
                <tr><th width=50%>小测题号</th>
                    <th width=50%>查看结果</th></tr>
                </thead>
				<tbody>
			<%int i=1;
			  for(String str:question) {%>
				<tr><td width=50%>第<%=i%>题</td>
					<td width=50%><a href="q/answer/cluster?questionid=<%=str%>">详情</a></td>
				</tr>
			<%i++;} %>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>