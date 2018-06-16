<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="utf-8" %>
<html>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <title>结果页</title>
</head>
<jsp:include page="header.jsp" flush="true"></jsp:include>
<body>

    <div class="weui-msg">
        <div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg"></i></div>
        <div class="weui-msg__text-area">
            <h2 class="weui-msg__title"><%=request.getAttribute("msg_title")%></h2>
            <p class="weui-msg__desc"><%=request.getAttribute("msg")%></p>
        </div>
        <div class="weui-msg__opr-area">
            <p class="weui-btn-area">
                <a href="javascript:history.back();" class="weui-btn weui-btn_primary">返回</a>
                <a onclick="WeixinJSBridge.call('closeWindow');" class="weui-btn weui-btn_default">关闭</a>
            </p>
        </div>
        <div class="weui-msg__extra-area">
            <div class="weui-footer">
                <p class="weui-footer__links">
                    <a href="javascript:void(0);" class="weui-footer__link">幕测</a>
                </p>
                <p class="weui-footer__text">Copyright &copy; 2016-2017 MoocTest</p>
            </div>
        </div>
    </div>
</body>
</html>
