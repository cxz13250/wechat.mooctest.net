<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	import="java.util.*,java.io.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

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
<style type="text/css">
div#head h2 {
	text-align: left;
}
</style>
<title>课堂点名</title>
</head>

<body>
	<div id="container">
	<%
		String date = (String) request.getAttribute("date");
		String openid = (String) request.getAttribute("openid");
	%>
		
		<div id="div1">
			<div style="float: left">
				<span style="color: #49b7e8; text-align: left;"><%=date%></span>
			</div>
			<div style="float: right">
				<span style="color: #000; text-align: right;">点名</span>
			</div>
			<div style="clear: both; color: #000;"></div>
		</div>
		<div id="div">
			<p id="info" style="color: red; text-align: center;">正在获取地理位置信息</p>
		</div>

		<div id="div2">
			<div id="head">
				<h1 style="color: #000;"></h1>
			</div>
			<div id="description">
				<p>
				</p>
			</div>
			<div id="main">
				<form name="form" method="post" action="q/rollcall/submit_rollcall_location">
				<input type="hidden" name="openid" value="<%=openid %>">
				<input type="hidden" id="latitude" name="latitude" value="">
				<input type="hidden" id="longitude" name="longitude" value="">
					<div>
						<br> <a href="javascript:submitRollcallLocation();"
							class="submit_button">参与点名</a> <br> <br> <br>
					</div>
				</form>
			</div>
		</div>


	</div>
	<div>
			<p style="color:red;" id="warning"></p>
	</div>
	<div id="div3">
		<p style="color:white;">请等待获取地理位置后点击<span style="color:red;">参与点名</span>按钮</p>
	</div>
</body>
<script>
function getLocation()
{
if (navigator.geolocation)
  {
  navigator.geolocation.getCurrentPosition(showPosition);
  }
else{document.getElementById("warning").innerHTML="Geolocation is not supported by this browser.";}
}
function showPosition(position)
{
	document.getElementById("info").innerHTML="成功获取到地理位置";
	document.getElementById("latitude").value=position.coords.latitude;
	document.getElementById("longitude").value=position.coords.longitude;
	if(document.getElementById("warning").innerHTML==""){
		
	}else{
		document.getElementById("warning").innerHTML="请再次点击【参与点名】";
	}
}
function submitRollcallLocation()
{
	if (document.getElementById("latitude").value == "" || document.getElementById("longitude").value == ""){
		document.getElementById("warning").innerHTML="浏览器尚未获得您的地理位置，请稍等片刻后再次尝试";
	}else{
		form.submit();		
	}
}
getLocation();
</script>
</html>