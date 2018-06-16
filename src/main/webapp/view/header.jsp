<%@ page import="com.mooctest.weixin.manager.Managers" %><%--
  Created by IntelliJ IDEA.
  User: ROKG
  Date: 2018/6/15
  Time: 下午12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String basePath = Managers.config.getBaseUrl();
%>
<head>
    <link rel="stylesheet" href="http://cdn.bootcss.com/weui/1.1.1/style/weui.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/jquery-weui/1.0.1/css/jquery-weui.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/rokg.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/quiz_base.css" />
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery.blockUI.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/application-util.js"></script>
    <script src="<%=basePath%>echarts/echarts.js"></script>
    <script src="<%=basePath%>echarts/echarts-all.js"></script>
    <script src="http://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/jquery-weui/1.0.1/js/jquery-weui.min.js"></script>
    <script src="http://cdn.bootcss.com/jquery-weui/1.0.1/js/swiper.min.js"></script>
    <script src="http://cdn.bootcss.com/jquery-weui/1.0.1/js/city-picker.min.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>

</body>
</html>
