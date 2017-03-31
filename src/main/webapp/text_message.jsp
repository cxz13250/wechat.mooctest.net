<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	import="java.util.*,java.io.*,com.mooctest.weixin.pojo.JSApiTicket,com.mooctest.weixin.util.JsSDKSign"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<%
		String date = (String) request.getAttribute("date");
		String title = (String) request.getAttribute("title");
		String subtitle = (String) request.getAttribute("subtitle");
		String text = (String) request.getAttribute("text");
	%>

<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet" type="text/css" href="css/quiz_base.css" />
<title><%=title %></title>
<style type="text/css">
#main h1, #main p {
	text-align: center;
}
</style>
</head>

<body>
	<input type="hidden" name="appId" id="appId" value="">
	<input type="hidden" name="timestamp" id="timestamp"
		value="">
	<input type="hidden" name="nonceStr" id="nonceStr"
		value="">
	<input type="hidden" name="signature" id="signature"
		value="">
	<div id="container">
		<div id="div1">
			<div style="float: left">
				<span style="color: #49b7e8; text-align: left;"><%=date %></span>
			</div>
			<div style="float: right">
				<span style="color: #000; text-align: right;"><%=subtitle %></span>
			</div>
			<div style="clear: both; color: #000;"></div>
		</div>
		<div id="div2">
			<div id="main">
				<%=text %>
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
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	var appId = document.getElementById("appId").value;
	var timestamp = document.getElementById("timestamp").value;
	var nonceStr = document.getElementById("nonceStr").value;
	var signature = document.getElementById("signature").value;

	wx.config({
		debug : false,
		appId : appId,
		timestamp : timestamp,
		nonceStr : nonceStr,
		signature : signature,
		jsApiList : [ 'checkJsApi', 'onMenuShareTimeline',
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