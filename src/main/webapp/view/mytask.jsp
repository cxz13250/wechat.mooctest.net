<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="com.mooctest.weixin.pojo.JSApiTicket,com.mooctest.weixin.util.JsSDKSign,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String title="我的任务";
	List<String> list=(List<String>)request.getAttribute("list");
	JSApiTicket jsApiTicket = (JSApiTicket)request.getAttribute("JSApiTicket");
    String appid=request.getParameter("appid");

	String url = "http://mooctest.net/weixin/q/test/close";
	String jsapi_ticket = jsApiTicket.getTicket();
	Map<String, String> sign = JsSDKSign.sign(jsapi_ticket, url);
	String timestamp = sign.get("timestamp");
	String nonceStr = sign.get("nonceStr");
	String signature = sign.get("signature");
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
<link rel="stylesheet" type="text/css" href="../css/quiz_base.css">
</head>
<body>
	<input type="hidden" name="appId" id="appId" value="<%=appid%>">
	<input type="hidden" name="timestamp" id="timestamp"
		value="<%=timestamp%>">
	<input type="hidden" name="nonceStr" id="nonceStr"
		value="<%=nonceStr%>">
	<input type="hidden" name="signature" id="signature"
		value="<%=signature%>">
	<div id="container">
		<div id="div1">
			<h1>任务列表</h1>
		</div>
		<div id="div2">
			<div id="main">
				<table border='0' width=100%>
					<tr><td width=60%>任务名称</td>
						<td width=40%>任务入口</td></tr>
					<% for(String name:list){ if(name!=null){%> 
					<tr><td width=60%><%=name%></td>
						<td width=40%><a href='q/task/taskinfo?openid=${openid}&name=${name}'>执行任务</a></td></tr>
					<%}else{%>
					<tr><td width=60%>无</td>
						<td width=40%>无</td></tr>
					<%}}%>
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