<%--
  Created by IntelliJ IDEA.
  User: ROGK
  Date: 2017/5/8
  Time: 9:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mooctest.weixin.manager.Managers" %>
<html>
<%
    String data_arr=(String)request.getAttribute("data_arr");
    String text_arr=(String)request.getAttribute("text_arr");
    String[] text=text_arr.split(";");
    String[] data=data_arr.split(";");
%>
<head>
    <%
        String basePath = Managers.config.getBaseUrl();
    %>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>本题结果</title>
</head>
<jsp:include page="header.jsp" flush="true"></jsp:include>
<body>
<div id="container" align="center">
    <input type="hidden" id="data_arr" value="<%=data_arr%>" />
    <input type="hidden" id="text_arr" value="<%=text_arr%>" />
    <div id="pie" style="width: 80%;height:300px"></div>
    <div id="table">
        <table class="weui-table weui-border-tb">
            <thead>
            <tr><th width=50%>选项</th>
                <th width=50%>人数</th></tr>
            </thead>
            <tbody>
            <% for(int i=0;i<data.length;i++){%>
            <tr><td width=50%><%=text[i]%></td>
                <td width=50%><%=data[i]%></td>
            </tr>
            <%}%>
            </tbody>
        </table>
    </div>
    <div>
        <p class="weui_btn_area">
            <a onclick="javascript:history.back()" class="weui_btn weui_btn_primary">返回</a>
        </p>
    </div>
</div>
<script type="text/javascript">

    var arr1 = document.getElementById("data_arr").value;
    var arr3 = document.getElementById("text_arr").value;
    var data_arr=arr1.trim().split(';');
    var text_arr=arr3.trim().split(';');

    var seriesdata=[];
    for(var i=0;i<data_arr.length-1;i++)
    { var obj=new Object();
        obj.name=text_arr[i];
        obj.value=data_arr[i];
        seriesdata[i]=obj;
    }

    var myChart = echarts.init(document.getElementById('pie'));
    var option = {
        title : {
            text: '本题结果统计',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:text_arr
        },
        toolbox: {

        },
        calculable : true,
        series : [
            {
                name:'该选项人数',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:seriesdata
            }
        ]
    };

    myChart.setOption(option);
</script>
</body>
</html>
