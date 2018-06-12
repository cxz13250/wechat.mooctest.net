<%@ page import="com.mooctest.weixin.manager.Managers" %><%--
  Created by IntelliJ IDEA.
  User: ROKG
  Date: 2018/6/10
  Time: 下午11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%
    String openid=(String)request.getAttribute("openid");
    Boolean flag=(Boolean) request.getAttribute("flag");
%>
<html>
<%
    String basePath = Managers.config.getBaseUrl();
%>
<head>
    <title>月度比赛报名</title>
    <style>
        form{background:url("http://mooctest-site.oss-cn-shanghai.aliyuncs.com/wechat/monthly_match.jpg");
            height: 1730px;
            background-repeat:no-repeat;
            background-position: center center;
            background-size: 100% 100%;}
        form .address {padding-left:20%;padding-top: 900px;width: 60%;}
        form .signUp {text-align: center;padding-top: 100px}
        form .address span{font-size: 45px;padding-right: 50px;color: #ffffff;text-align: center}
        form .address input{height: 80px;font-size: 45px}
        form .signUp input{width: 300px;font-size: 45px;background: rgb(57,40,135);color:#fff}
        .info {  text-align: center;font-size: 70px;color: #ffff;  padding-top: 999px;}
        .addressStr {width: 100%}
    </style>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery.blockUI.js"></script>
</head>
<body>
<div id="main">
    <form>
        <% if(!flag){%>
        <div class="address" id="address"><tr><td><span>收货地址:</span></td><br><td><input type="text" size="28" id="addressStr" class="addressStr"></td></div>
        <div class="signUp" id="signUp"><input type="button" value="立即报名" id="sign"></div>
        <%}%>
            <div id="info" class="info">
            <label>已成功报名</label>
        </div>

</div>
</body>
<script>
    $(function() {
        var flag='<%=flag%>';
        //console.log(flag);
        if(flag=='false'){
            $('#info').hide();
        }
    });
    $('#sign').click(function() {
        var address=$('#addressStr').val();
        //console.log(address);
        join(address);
    });

    function join(address) {
        var param={
            "address":address,
            "openid":'<%=openid%>'
        };
        $.ajax({
            type: 'POST',
            url: '<%=basePath%>q/competition/enter',
            data : param,
            dataType : 'json',
            beforeSend : function(request) {
                request.setRequestHeader("Accept", "application/json; charset=utf-8");
            },
            complete : function() {
            },
            success : function(data) {
                if(data) {
                    $('#address').hide();
                    $('#signUp').hide();
                    $('#info').show();
                }
            },
            error : function() {
            }
        })
    }
</script>
</html>
