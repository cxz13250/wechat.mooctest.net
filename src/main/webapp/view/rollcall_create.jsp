<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mooctest.weixin.manager.Managers" %>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String basePath = Managers.config.getBaseUrl();
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/quiz_base.css" />
<title>课堂点名</title>
</head>
<body>
	<%
		String openid = (String) request.getAttribute("openid");
		String date = (String) request.getAttribute("date");
	%>

	<div id="container">
		<div id="div1">
			<div style="float: left">
				<span style="color: #49b7e8; text-align: left;"><%=date%></span>
			</div>
			<div style="float: right">
				<span style="color: #000; text-align: right;">创建点名</span>
			</div>
			<div style="clear: both; color: #000;"></div>
		</div>
		<div>
			<p id="info" style="color: red; text-align: center;">正在获取地理位置信息</p>
		</div>

		<div id="div2">
			<div id="main">
				<form name="rollcall_info" method="post" action="<%=basePath%>q/rollcall/submit_rollcall">
					<input type="hidden" id="openid" name="openid" value="<%=openid%>">
					<input type="hidden" id="latitude" name="latitude" value="">
					<input type="hidden" id="longitude" name="longitude" value="">		
					<div>
						<br>选择班级&nbsp; <select id="classId" name="classId">
							<%
								List<String> classIdList = (List<String>) request
										.getAttribute("groupIdList");
								List<String> classNameList = (List<String>) request
										.getAttribute("groupNameList");
								for (int i = 0; i < classIdList.size(); i++) {
									String classId = classIdList.get(i);
									String className = classNameList.get(i);
							%>
							<option value="<%=classId%>"><%=className%></option>
							<%
								}
							%>
						</select>
					</div>
				</form>
			</div>
		</div>
		
		<div>
			<p style="color:red;" id="warning"></p>
		</div>

		<br>
		<br>
		<br>
		<div id="div3">
		<!--  javascript:rollcall_info.submit(); -->
			<a href="javascript:submitRollcall();" class="bottom_bar" id="submit_rollcall" hidden="true">开始点名</a>
		</div>
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
	document.getElementById("submit_rollcall").hidden=false;	
	if(document.getElementById("warning").innerHTML==""){
		
	}else{
		document.getElementById("warning").innerHTML="请再次点击【开始点名】";
	}
  }
function submitRollcall()
	{
		if (document.getElementById("latitude").value == "" || document.getElementById("longitude").value == ""){
			document.getElementById("warning").innerHTML="浏览器尚未获得您的地理位置，请稍等片刻后再次尝试";
		}else{
			rollcall_info.submit();		
		}
	}
getLocation();
</script>
</html>