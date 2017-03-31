<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="com.mooctest.weixin.pojo.JSApiTicket,java.util.*,com.mooctest.weixin.util.JsSDKSign,java.io.*,com.mooctest.weixin.manager.WitestManager" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String date = (String) request.getAttribute("date");
    String title = "账号绑定";
    String subtitle = "请输入慕测账号";
    
    JSApiTicket jsApiTicket = (JSApiTicket)request.getAttribute("JSApiTicket");
    String appid=request.getParameter("appid");
    
	///String url = "http://mooctest.net/weixin/q/test/close";
	//String jsapi_ticket = jsApiTicket.getTicket();
	//Map<String, String> sign = JsSDKSign.sign(jsapi_ticket, url);
	//String timestamp = sign.get("timestamp");
	//String nonceStr = sign.get("nonceStr");
	//String signature = sign.get("signature");
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
<link rel="stylesheet" type="text/css" href="css/quiz_base.css" />
<style type="text/css">
#main h1, #main p {
	text-align: center;
}
.myform-row {
	padding: 5px;
}
</style>
</head>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<body>
	<input type="hidden" name="appId" id="appId" value="<%=appid%>">
	<input type="hidden" name="timestamp" id="timestamp"
		value="">
	<input type="hidden" name="nonceStr" id="nonceStr"
		value="">
	<input type="hidden" name="signature" id="signature"
		value="">
	<div id="container">
		<div id="div1">
			<div style="float: left">
				<span style="color: #49b7e8; text-align: left;"><%=date%></span>
			</div>
			<div style="float: right">
				<span style="color: #000; text-align: right;"><%=subtitle%></span>
			</div>
			<div style="clear: both; color: #000;"></div>
		</div>
		<div id="div2">
			<div id="main">
				<h1>账号绑定</h1>
				<form name="form" style="width:100%;" method="post" action="q/account/check?openid=${openid} }">
					<div class="myform-row">
						<label for="usernameInput">帐号:&nbsp;</label>
						<input type="text" style="width:80%;" name="username" id="username" value="" />
					</div>
					<div class="myform-row">
						<label for="passwordInput">密码:&nbsp;</label>
						<input type="password" style="width:80%;" name="password" id="password"/>
					</div>
					<div class="myform-row">
						<br>
						<a href="" class="submit_button">账号注册</a>
						<a href="javascript:form.submit();" class="submit_button">确认绑定</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<br>
	<br>
	<br>
	<div id="div3">
		<a id="closeWindow" class="bottom_bar">关闭页面</a>
	</div>
</body>
<script type="text/javascript">
	var appid=Document.getElementById("appid");
	var timestamp=Document.getElementById("timestamp");
	var nonceStr=Document.getElementById("nonceStr");
	var signature=Document.getElementById("signature");
	wx.config({
		appid:appid,
		timestamp:timestamp,
		nonceStr:nonceStr,
		signature:signature,
		jsApiList:[ 'checkJsApi', 'onMenuShareTimeline',
			'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo',
			'onMenuShareQZone', 'hideMenuItems', 'showMenuItems',
			'hideAllNonBaseMenuItem', 'showAllNonBaseMenuItem',
			'translateVoice', 'startRecord', 'stopRecord',
			'onVoiceRecordEnd', 'playVoice', 'onVoicePlayEnd',
			'pauseVoice', 'stopVoice', 'uploadVoice', 'downloadVoice',
			'chooseImage', 'previewImage', 'uploadImage', 'downloadImage',
			'getNetworkType', 'openLocation', 'getLocation',
			'hideOptionMenu', 'showOptionMenu', 'closeWindow',
			'scanQRCode', 'chooseWXPay', 'openProductSpecificView',
			'addCard', 'chooseCard', 'openCard' ]
	});
	document.querySelector('#closeWindow').onclick = function() {
		wx.closeWindow();
	};
</script>
</html>