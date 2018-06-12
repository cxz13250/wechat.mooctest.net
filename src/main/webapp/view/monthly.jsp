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
        body{overflow-x:hidden;overflow-y:auto;}
        form{background:url("http://mooctest-site.oss-cn-shanghai.aliyuncs.com/wechat/monthly_match.jpg");
            height: 1730px;
            background-repeat:no-repeat;
            background-position: center center;
            background-size: 100% 100%;}
        form .title{width:600px;height: 600px;border-radius: 50%;background-color:rgb(57,40,135);opacity: 0.4;border: 1px #ffffff;margin: 0 auto}
        form .discription div{color: #fff;opacity: 1;}
        form .address {padding-top: 350px}
        form .signUp {text-align: center;padding-top: 100px}
        form .address span{padding-left:100px;font-size: 45px;padding-right: 50px;color: #ffffff;text-align: center}
        form .address input{height: 80px;font-size: 45px;width: 80%;margin-left: 10%;}
        form .signUp input{width: 300px;font-size: 45px;background: rgb(84,74,155);color:#fff;border-radius: 35px;}
        .info {  text-align: center;font-size: 70px;color: #ffff;  padding-top: 40%;}
        /*.addressStr {width: 100%}*/
    </style>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery.blockUI.js"></script>
</head>
<body>
<div id="main">
    <form>
        <div style="padding-bottom: 100px"></div>
        <div class="title"></div>
        <div class="discription">
            <div style="font-size: 70px;text-align:center;color: #fff;position: absolute;margin-top: -100px;margin-left: 38%">月度赛</div>
            <div style="text-align:center;margin-top: -340px;color: #fff;position: relative;letter-spacing: 8px;font-size: 80px">开发者测试</div>
            <div style="font-size: 38px;text-align: center;color: #fff;position: relative;">Developer Testing</div>
        </div>
        <% if(!flag){%>
        <div class="address" id="address">
            <tr>
                <td><span>收货地址:</span></td><br>
                <td><input type="text" size="28" id="addressStr" class="addressStr"></td>
            </tr>
        </div>
        <div class="signUp" id="signUp"><input type="button" value="立即报名" id="sign"></div>
        <%}%>
            <div id="info" class="info">
            <label>已成功报名</label>
        </div>
    </form>
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
