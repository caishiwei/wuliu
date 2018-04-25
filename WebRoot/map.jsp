<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
</style>
<script type="text/javascript" src="http://ff.kis.scr.kaspersky-labs.com/A59CADB3-00B8-0146-9BE6-6AA348729F8B/main.js" charset="UTF-8"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=yWCSnIMQgozkv02WLiN1STnc5Y6vyXTQ"></script>
<title>线路地图:${modifybean.name}</title>
</head>
<body>
<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
// 百度地图API功能
var map = new BMap.Map("allmap");
map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);
map.enableScrollWheelZoom(true);
var start = "${modifybean.startDept.address}";
var end = "${modifybean.endDept.address}";

var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
driving.search(start, end,{waypoints:[${waypoints}]});//waypoints表示途经点

</script>
