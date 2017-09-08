<%@ page import="java.util.List" %>
<%@ page import="com.mooctest.weixin.entity.Task" %>
<%--
  Created by IntelliJ IDEA.
  User: ROGK
  Date: 2017/5/10
  Time: 13:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <%
        List<Task> list=(List<Task>)request.getAttribute("task");
    %>
<head>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
    %>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>任务列表</title>
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
    <link rel="stylesheet" href="http://cdn.bootcss.com/weui/1.1.1/style/weui.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/jquery-weui/1.0.1/css/jquery-weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/weui.css">
</head>
<body>
<div id="container" align="center">
    <div id="div1" >
        <h1 class="weui-header-title" align="center">任务列表</h1>
    </div> <br/>
    <div id="div2">
        <table class="weui-table weui-border-tb">
            <thead>
            <tr><th width="50%">任务名称</th>
                <th width="50%">查看详情</th>
            </tr>
            </thead>
            <tbody>
                <%for(Task task:list){%>
                <tr>
                    <td width="50%"><%=task.getTaskName()%></td>
                    <td width="50%"><a href="worker_grade?id=<%=task.getId()%>&name=<%=task.getTaskName()%>">详情</a></td>
                </tr>
                <%}%>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
