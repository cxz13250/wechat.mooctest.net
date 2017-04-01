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
</head>
<body>
<div id="container">
    <div id="div1">
        <h1 align="center">我的群组</h1>
    </div>
    <div id="div2">
        <div id="main">
            <table border='0' width=100% class="weui-table weui-border-tb">
                <thead>
                <tr><td width=60%>群主名称</td>
                    <td width=40%>群组名称</td></tr>
                </thead>
                <tbody>
                <% for(Group name:list){%>
                <tr><td width=60%><%=name.getManagerName()%></td>
                    <td width=40%><%=name.getGroupName()%></td>
                </tr>
                <%}%>
                </tbody>
            </table>
        	<a href='<%=basePath%>q/group/tojoin?openid=<%=openid%>'>加入群组</a>
        </div>
    </div>
</div>
<br>
<br>
<br>
</body>
</html>