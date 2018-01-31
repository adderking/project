<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<link href="<%=basePath %>static/css/hotMap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>WEB-INF/view/heatMap/css/bmap.css"/>
	<script type="text/javascript" src="<%=basePath %>static/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>WEB-INF/view/heatMap/js/apiv1.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>WEB-INF/view/heatMapjs/Heatmap_min.js"></script>
	<title>地图展示</title>
</head>
<body>
	<!-- 头部开始 -->
	<jsp:include page="../main.jsp"></jsp:include>
	<!-- 头部结束 -->
	<div id="allmap"></div>
	<div id="contentWrapper">
		<div class="hot-title">
			<h2>
			<i></i>
			<span>单点设备实时人数</span>
			</h2>
		</div>
		<div class="hot-stat">
			<table class="table-list">
			<thead>
			<tr>
			<th class="area">设备</th>
			<th>实时人数 (人)</th>
			<th>峰值</th>
			</tr>
			</thead>
			<tbody id="deviceTable">
			
			</tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		// 百度地图API功能
		var map = new BMap.Map("allmap");    // 创建Map实例
		map.addControl(new BMap.NavigationControl());               // 添加平移缩放控件
		map.addControl(new BMap.ScaleControl());                    // 添加比例尺控件
		map.addControl(new BMap.OverviewMapControl());   
		           //添加缩略地图控件
		map.disable3DBuilding();
		map.centerAndZoom(new BMap.Point(113.6774409, 22.941246), 14);  // 初始化地图,设置中心点坐标和地图级别
		map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
		map.setCurrentCity("厚街镇");          // 设置地图显示的城市 此项是必须设置的
		map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
		
		function drawHeatMap(data) {
			var points = data;
		    if(!isSupportCanvas()){
		    	alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~')
		    }
			//详细的参数,可以查看heatmap.js的文档 https://github.com/pa7/heatmap.js/blob/master/README.md
			//参数说明如下:
			/* visible 热力图是否显示,默认为true
		     * opacity 热力的透明度,1-100
		     * radius 势力图的每个点的半径大小   
		     * gradient  {JSON} 热力图的渐变区间 . gradient如下所示
		     *	{
					.2:'rgb(0, 255, 255)',
					.5:'rgb(0, 110, 255)',
					.8:'rgb(100, 0, 255)'
				}
				其中 key 表示插值的位置, 0~1. 
				    value 为颜色值. 
		     */
			heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":20});
			map.addOverlay(heatmapOverlay);
			heatmapOverlay.setDataSet({data:points});
			heatmapOverlay.show();
		}
		
		function refresh() {
			$.ajax({
				url:'<%=basePath%>realTimeView/heatMapByAjax.action',
				success:function(datas) {
					drawHeatMap(datas.data);
					drawTable(datas.list);
				},
				error:function() {
				
				}
			});
		}
		
		
		function drawTable(data) {
			var deviceTable = $("#deviceTable");
			deviceTable.empty();
			$(data).each(function(i,n) {
				var html = '<tr class="line" onclick="findDeviceInfoByDevicenum(\"'+n.devicenum+'\");">';
				html += '<td><span id="sort" class="sort2">'+(i+1)+'</span>'+n.devicename+'</td>';
				html += '<td id="fontb">'+n.count+'</td>';
				html += '<td id="fontb">'+n.number+'</td>';
				html += '</tr>';
				deviceTable.append(html);
			});
		}
		
		refresh();
		
		//是否显示热力图
	    function openHeatmap(){
	        heatmapOverlay.show();
	    }
		function closeHeatmap(){
	        heatmapOverlay.hide();
	    }
		
	    function setGradient(){
	     	/*格式如下所示:
			{
		  		0:'rgb(102, 255, 0)',
		 	 	.5:'rgb(255, 170, 0)',
			  	1:'rgb(255, 0, 0)'
			}*/
	     	var gradient = {};
	     	var colors = document.querySelectorAll("input[type='color']");
	     	colors = [].slice.call(colors,0);
	     	colors.forEach(function(ele){
				gradient[ele.getAttribute("data-key")] = ele.value; 
	     	});
	        heatmapOverlay.setOptions({"gradient":gradient});
	    }
		//判断浏览区是否支持canvas
	    function isSupportCanvas(){
	        var elem = document.createElement('canvas');
	        return !!(elem.getContext && elem.getContext('2d'));
	    }
	    
	    function findDeviceInfoByDevicenum(devicenum) {
	    	var url = '<%=basePath%>realTimeView/findDeviceInfoByDevicenum.action/'+devicenum;
	    	window.open(url,"_blank","width=80%,height=80%,menubar=yes,toolbar=yes,fullscreen=yes");
	    }
	</script>
	
</body>
</html>

