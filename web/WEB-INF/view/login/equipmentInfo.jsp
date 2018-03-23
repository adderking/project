<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include  file="basePath.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="utf-8">
    <title>WI-FI视频联动管理系统</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta name="description" content="">
    <meta name="author" content="">
    <script type="application/javascript" src="<%=path%>/assets/js/jquery.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=nkQsVOzZVYGf1IBz6Bz7BgW8sLjLzCfb"></script>
    <%--<script type="text/javascript" src="<%=path%>/heatMap/js/apiv2.0.min.js"></script>--%>
    <style type="text/css">
        body, html,#mapContainer {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
    </style>
</head>
<html>

<div id="mapContainer"></div>

</html>
<script type="text/javascript">
    // Document is ready.
    var map = new BMap.Map("mapContainer",{minZoom:12,maxZoom:18});
    var point = new BMap.Point(116.339878,39.73606);
    var marker = new BMap.Marker(point);  // 创建标注
    marker.setAnimation(BMAP_ANIMATION_DROP);
    map.clearOverlays();
    map.enableScrollWheelZoom(true);
    map.addOverlay(marker);// 将标注添加到地图中
    map.centerAndZoom(point,15);
    var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
    var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
    var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
    /*缩放控件type有四种类型:
    BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*/
    map.addControl(top_left_control);
    map.addControl(top_left_navigation);
    map.addControl(top_right_navigation);

   $.ajax({
        type:"get",
        url:"<%=basePath%>/videoEquipLocaiton",
        dataType:"json",
        success:function(data){
            for(var i = 0 ;i<data.length;i++) {
                var point = new BMap.Point(data[i].langitude, data[i].latitude);
                var content = data[i].equipmentId + "," + data[i].equipmentLocation;
                var label = new BMap.Label(content, {offset: new BMap.Size(20, -10)});
                var icon = new BMap.Icon("offlinemap/images/113equipment.png",new BMap.Size(19,25));
                var mark = new BMap.Marker(point,{icon:icon});
                mark.setLabel(label);
                map.addOverlay(mark);
                // marker.addEventListener("click", clickHandler(content,point));
             }
        }
    });
    $.ajax({
        type:"get",
        url:"<%=basePath%>/wifiEquipLocaiton",
        dataType:"json",
        success:function(data){
            for(var i = 0 ;i<data.length;i++) {
                var point = new BMap.Point(data[i].langitude, data[i].latitude);
                var content = data[i].equipmentId + "," + data[i].equipmentLocation;
                var label = new BMap.Label(content, {offset: new BMap.Size(20, -10)});
                var icon = new BMap.Icon("offlinemap/images/wifiequipment.png",new BMap.Size(19,25));
                var mark = new BMap.Marker(point,{icon:icon});
                // mark.setLabel(label);
                map.addOverlay(mark);

            }
        }
    });
    /*translatecallback = function (data){
        if(data.status === 0) {
            console.log(data.points[0])
            var marker = new BMap.Marker(data.points[0]);
            map.addOverlay(marker);
            var label = new BMap.Label("德贤路五环路北",{offset:new BMap.Size(20,-10)});
            marker.setLabel(label); //添加百度label
            map.setCenter(data.points[0]);
        }
    }
    var convert = new BMap.Convertor();
    var point = new BMap.Point(116.423453,39.792246);
    var ggpoint = [];
    ggpoint.push(point);
    convert.translate(ggpoint, 1, 5, translatecallback);*/


</script>