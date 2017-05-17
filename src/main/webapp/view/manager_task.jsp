<%@ page import="java.util.List" %>
<%@ page import="com.mooctest.weixin.entity.Task"%>
<%--
  Created by IntelliJ IDEA.
  User: ROGK
  Date: 2017/5/10
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <%
        List<Task> current=(List<Task>)request.getAttribute("current");
        List<Task> unstart=(List<Task>)request.getAttribute("unstart");
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
    <link rel="stylesheet" href="http://jqweui.com/dist/lib/weui.min.css"/>
    <link rel="stylesheet" href="http://jqweui.com/dist/demos/css/demos.css"/>
    <link rel="stylesheet" href="http://jqweui.com/dist/css/jquery-weui.css"/>
    <script src="http://jqweui.com/dist/lib/jquery-2.1.4.js"></script>
    <script src="http://jqweui.com/dist/lib/fastclick.js"></script>
    <script src="http://jqweui.com/dist/js/jquery-weui.js"></script>
    <script>
        $(function(){
            FastClick.attach(document.body);
        });
    </script>
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
<body>
<div id="container">
    <div class="weui-tab">
        <div class="weui-navbar">
            <a class="weui-navbar__item weui-bar__item--on" href="#tab1">
                进行中
            </a>
            <a class="weui-navbar__item" href="#tab2">
                未开始
            </a>
        </div>
        <div class="weui-tab__bd">
            <div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
                <h1 class="weui-header-title" align="center">任务列表</h1><br/>
                <table>
                    <thead>
                    <tr><td width="50%">任务名称</td>
                        <td width="50%">查看详情</td>
                    </tr>
                    </thead>
                    <tbody>
                    <%for(Task task:current){%>
                    <tr>
                        <td width="50%"><%=task.getTaskName()%></td>
                        <td width="50%"><a href="worker_task?id=<%=task.getId()%>&name=<%=task.getTaskName()%>">详情</a></td>
                    </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>
            <div id="tab2" class="weui-tab__bd-item">
                <h1 class="weui-header-title" align="center">任务列表</h1><br/>
                <table>
                    <thead>
                    <tr><th width="50%">任务名称</th>
                        <th width="50%">查看详情</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%for(Task task:unstart){%>
                    <tr>
                        <td width="50%"><%=task.getTaskName()%></td>
                        <td width="50%"><a href="q/task/worker_task?id=<%=task.getId()%>&name=<%=task.getTaskName()%>">详情</a></td>
                    </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>

</body>
</html>
