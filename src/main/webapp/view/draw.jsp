<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String color_arr=(String)request.getAttribute("color_arr");
	String data_arr=(String)request.getAttribute("data_arr");
	String text_arr=(String)request.getAttribute("text_arr");
	String[] text=text_arr.split(";");
	List<String> list=(List<String>)request.getAttribute("arrList");
%>
<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://cdn.bootcss.com/html5shiv/3.7.7/html5shiv.min.js"></script> 
<title>小测结果</title>
<link rel="stylesheet" href="http://weixin.yoby123.cn/weui/style/weui.css"/>
<link rel="stylesheet" href="http://weixin.yoby123.cn/weui/style/weui2.css"/>
<link rel="stylesheet" href="http://weixin.yoby123.cn/weui/style/weui3.css"/>
<script type="text/javascript">  
            //绘制饼图  
            function drawCircle(canvasId, data_arr, color_arr, text_arr)  
            {  
                var c = document.getElementById(canvasId);  
                var ctx = c.getContext("2d");  
  
                var radius = c.height / 2 - 20; //半径  
                var ox = radius + 20, oy = radius + 20; //圆心  
  
                var width = 30, height = 10; //图例宽和高  
                var posX = ox * 2 + 20, posY = 30;   //  
                var textX = posX + width + 5, textY = posY + 10;  
  
                var startAngle = 0; //起始弧度  
                var endAngle = 0;   //结束弧度  
                for (var i = 0; i < data_arr.length; i++)  
                {  
                    //绘制饼图  
                    endAngle = endAngle + data_arr[i] * Math.PI * 2; //结束弧度  
                    ctx.fillStyle = color_arr[i];  
                    ctx.beginPath();  
                    ctx.moveTo(ox, oy); //移动到到圆心  
                    ctx.arc(ox, oy, radius, startAngle, endAngle, false);  
                    ctx.closePath();  
                    ctx.fill();  
                    startAngle = endAngle; //设置起始弧度  
  
                    //绘制比例图及文字  
                    ctx.fillStyle = color_arr[i];  
                    ctx.fillRect(posX, posY + 20 * i, width, height);  
                    ctx.moveTo(posX, posY + 20 * i);  
                    ctx.font = 'bold 12px 微软雅黑';    //斜体 30像素 微软雅黑字体  
                    ctx.fillStyle = color_arr[i]; //"#000000";  
                    var percent = text_arr[i] + "：" + 100 * data_arr[i] + "%";  
                    ctx.fillText(percent, textX, textY + 20 * i);  
                }  
            }  
  
            function init() {  
                //绘制饼图  
                //比例数据和颜色  
                var arr1 = document.getElementById("data_arr").value; 
                var arr2 = document.getElementById("color_arr").value;  
                var arr3 = document.getElementById("text_arr").value;  
                
                var data_arr=arr1.split(';');
                var color_arr=arr2.split(';');
                var text_arr=arr3.split(';');
  
                drawCircle("canvas_circle", data_arr, color_arr, text_arr);  
            }  
  
            //页面加载时执行init()函数  
            window.onload = init;  
</script> 
</head>
<style type="text/css">
#main h1, #main p {
	text-align: center;/*  */
}
.myform-row {
	padding: 5px;
}
</style>
<body>
<div id="main">
<input type="hidden" id="color_arr" value="<%=color_arr%>" />
<input type="hidden" id="data_arr" value="<%=data_arr%>" />
<input type="hidden" id="text_arr" value="<%=text_arr%>" />
<h1>小测结果</h1>  
       <div align="center">  
            <canvas id="canvas_circle" width="500" height="300" style="border:2px solid #0026ff;" >  
                浏览器不支持canvas  
            </canvas>  
        </div> 
        <div>
        <table class="weui-table weui-border-tb">
                <thead>
                <tr><th width=50%>选项</th>
                    <th width=50%>人数</th></tr>
                </thead>
                <tbody>
                <% for(int i=0;i<list.size();i++){%>
                <tr><td width=50%><%=text[i]%></td>
                    <td width=50%><%=list.get(i)%></td>
                </tr>
                <%}%>
                </tbody>
            </table> 
        </div> 
        <div>
         <p class="weui_btn_area">
                <a onclick="WeixinJSBridge.call('closeWindow');" class="weui_btn weui_btn_primary">确定</a>
         </p>
         </div>
</div>
</body>
</html>