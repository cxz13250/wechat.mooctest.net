<%@page import="com.mooctest.weixin.entity.Group"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String openid =(String)request.getAttribute("openid");
	List<Group> list=(List<Group>)request.getAttribute("list");
%>
<head>
<%
     String path = request.getContextPath();
     String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>我的群组</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/quiz_base.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/weui.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/weui2.css" />
</head>
<style type="text/css">
#main h1, #main p {
	text-align: center;/*  */
}
.myform-row {
	padding: 5px;
}
</style>
<body ontouchstart style="background-color: #f8f8f8;">
<div id="container">
    <div id="div1">
    </div>
    <div id="div2">
        <div id="main">
        	<h1>我的群组</h1>
            <table class="weui-table weui-border-tb">
                <thead>
                <tr><th width=50%>群主名称</th>
                    <th width=50%>群组名称</th></tr>
                </thead>
                <tbody>
                <% for(Group name:list){%>
                <tr><td width=50%><%=name.getManagerName()%></td>
                    <td width=50%><%=name.getGroupName()%></td>
                </tr>
                <%}%>
                </tbody>
            </table> 
        </div>
        <div class="myform-row">
            <a href='<%=basePath%>q/group/tojoin?openid=<%=openid%>' class="weui-btn weui-btn_primary">加入群组</a>    	
        </div>
    </div>
</div>
<br>
<br>
<br>
</body>
</html>