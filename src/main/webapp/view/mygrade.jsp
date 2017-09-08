<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page language="java" import="com.mooctest.weixin.pojo.JSApiTicket,com.mooctest.weixin.util.JsSDKSign,java.util.*"%>
<%@ page import="com.mooctest.weixin.entity.TaskInfo" %>
<%@ page import="com.mooctest.weixin.entity.FinishedTask" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    String title="我的成绩";
    List<FinishedTask> list=(List<FinishedTask>)request.getAttribute("list");
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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/rokg.css" />
</head>
<body ontouchstart style="background-color: #f8f8f8;">
<div id="container">
    <div id="div1">
        <h1 align="center">我的成绩</h1>
    </div>
    <div id="div2">
        <div id="main">
            <table class="weui-table weui-border-tb">
                <% if(list.isEmpty()||list == null){ %>
                    <p align="center">目前任务暂未打分</p>
                <% }else{%>
                <thead>
                <tr><th width=60%>任务名称</th>
                    <th width=40%>任务成绩</th></tr>
                </thead>
                <tbody>
                <% for(FinishedTask name:list){%>
                <tr><td width=60%><%=name.getTaskName()%></td>
                    <td width=40%><%=name.getScore()%></td>
                </tr>

                <%}}%>
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