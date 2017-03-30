<%@page import="com.mooctest.weixin.model.AccountInfo"%>
<%@page import="com.mooctest.weixin.util.JsSDKSign"%>
<%@page import="java.util.Map"%>
<%@page import="com.mooctest.weixin.pojo.JSApiTicket"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	AccountInfo accountInfo=(AccountInfo)request.getAttribute("accountInfo");
	String appId=request.getParameter("appid");
	String title="个人信息";
	String url="http://mooctest.net/weixin/q/test/close";
	JSApiTicket jsApiTicket=(JSApiTicket)request.getAttribute("JSApiTicket");
	String jsapi_ticket=jsApiTicket.getTicket();
	Map<String,String> sign=JsSDKSign.sign(jsapi_ticket, url);
	String timestamp=sign.get("timestamp");
	String nonceStr=sign.get("nonceStr");
	String signature=sign.get("signature");
%>
<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet" type="text/css" href="css/quiz_base.css" />
<title><%=title%></title>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
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
<body>
	<input type="hidden" name="appId" id="appId" value="<%=appId%>">
	<input type="hidden" name="timestamp" id="timestamp"
		value="<%=timestamp%>">
	<input type="hidden" name="nonceStr" id="nonceStr"
		value="<%=nonceStr%>">
	<input type="hidden" name="signature" id="signature"
		value="<%=signature%>">
	<div id="container">
		<div id="div1">
			<div style="float: left">
				<span style="color: #49b7e8; text-align: left;"></span>
			</div>
			<div style="float: right">
				<span style="color: #000; text-align: right;"></span>
			</div>
			<div style="clear: both; color: #000;"></div>
		</div>
		<div id="div2">
			<div id="main">
				<h1>账号信息</h1>
				<table border='0' width=100%>
				<tr><td width=30%>账号邮箱</td>
					<td width=40%><%=accountInfo.getEmail()%></td>
					<td width=30%><a href=''>修改</a></tr>
				<tr><td width=30%>绑定手机</td>
					<td width=40%><%=accountInfo.getMobile()%></td>
					<td width=30%><a href=''>修改</a></tr>
				<tr><td width=30%>账号密码</td>
					<td width=40%><a href=''>修改密码</a></td>
					<td width=30%></td></tr>
				</table>
				<h1>基本信息</h1>
				<table border='0' width=100%>
				<tr><td width=30%>姓名</td>
					<td width=40%><%=accountInfo.getName()%></td>
					<td width=30%><a href=''>修改</a></td></tr>
				<tr><td width=30%>所在单位</td>
					<td width=40%><%=accountInfo.getCompany()%></td>
					<td width=30%><a href=''>修改</a></td></tr>	
				</table>
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
	var timestamp=Document.getElementById("timestamp");
	var appId=Document.getElementById("appId");
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
	document.querySelector('#closewindow').onclick = function(){
		wx.closewindow();
	}
</script>
</html>