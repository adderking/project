<%--
  Created by IntelliJ IDEA.
  User: fangshilei
  Date: 18/3/22
  Time: 下午9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include  file="basePath.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>大屏</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <meta name="apple-mobile-web-app-capable" content="yes"/>
  <link href="<%=path%>/demo/resources/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
  <link href="<%=path%>/demo/resources/css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
  <link href="<%=path%>/demo/data/styles.css" type="text/css" rel="stylesheet"/>
  <link href="<%=path%>/demo/files/daping/styles.css" type="text/css" rel="stylesheet"/>
  <script src="<%=path%>/demo/resources/scripts/jquery-1.7.1.min.js"></script>
  <script src="<%=path%>/demo/resources/scripts/jquery-ui-1.8.10.custom.min.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/axQuery.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/globals.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axutils.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/annotation.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/axQuery.std.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/doc.js"></script>
  <script src="<%=path%>/demo/data/document.js"></script>
  <script src="<%=path%>/demo/resources/scripts/messagecenter.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/events.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/recording.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/action.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/expr.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/geometry.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/flyout.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/ie.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/model.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/repeater.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/sto.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/utils.temp.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/variables.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/drag.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/move.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/visibility.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/style.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/adaptive.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/tree.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/init.temp.js"></script>
  <script src="<%=path%>/demo/files/daping/data.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/legacy.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/viewer.js"></script>
  <script src="<%=path%>/demo/resources/scripts/axure/math.js"></script>
  <script src="<%=path%>/demo/resources/scripts/echarts.min.js"></script>
  <script type="text/javascript">
    function startTime()
    {
      var today=new Date()
      var h=today.getHours()
      var m=today.getMinutes()
      var s=today.getSeconds()

      var myyear=today.getFullYear();
      var mymonth=today.getMonth();
      var monthArray=["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"]
      var mynowdate=today.getDate();
      var myday=today.getDay();
      var dayArray=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"]


      m=checkTime(m)
      s=checkTime(s)
      document.getElementById('dateYear').innerHTML=myyear+"年"+monthArray[mymonth]+mynowdate+"日";
      document.getElementById('time').innerHTML=h+":"+m+":"+s;
      setTimeout('startTime()',1000)
    }

    function checkTime(i)
    {
      if (i<10)
      {i="0" + i}
      return i
    }
  </script>
  <script type="text/javascript">
    $axure.utils.getTransparentGifPath = function() { return '<%=path%>/demo/resources/images/transparent.gif'; };
    $axure.utils.getOtherPath = function() { return '<%=path%>/demo/resources/Other.html'; };
    $axure.utils.getReloadPath = function() { return '<%=path%>/demo/resources/reload.html'; };
  </script>
</head>
<body onload="startTime()">
<div id="base" class="">

  <!-- Unnamed (Rectangle) -->
  <div id="u18" class="ax_default box_2">
    <img id="u18_img" class="img " src="<%=path%>/demo/images/daping/u18.png"/>

    <!-- Unnamed () -->
    <div id="u19" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u20" class="ax_default box_1">
    <img id="u20_img" class="img " src="<%=path%>/demo/images/daping/u20.png"/>
    <div id="container5" style="height: 100%"></div>
    <script type="text/javascript">
      var dom = document.getElementById("container5");
      var myChart = echarts.init(dom);
      var app = {};
      option = null;
      app.title = '坐标轴刻度与标签对齐';

      option = {
        backgroundColor:'#000066',
        color: ['#3398DB'],
        tooltip : {
          trigger: 'axis',
          axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '1%',
          bottom: '3%',
          containLabel: true
        },
        xAxis : [
          {
            type : 'category',
            data : ['WIFI探针', 'WIFI审计', '卡口过车', '视频数据', '人像数据'],
            axisTick: {
              alignWithLabel: true
            },
            axisLabel: {
              show: true,
              textStyle: {
                color: '#fff'
              }
            }
          }
        ],
        yAxis : [
          {
            type : 'value',
            axisLabel: {
              show: true,
              textStyle: {
                color: '#fff'
              }
            }
          }
        ],
        series : [
          {
            name:'总数',
            type:'bar',
            itemStyle: {
              normal: {
                label: {
                  show: true,
                  textStyle: {
                    color: '#800080'
                  }
                }
              }
            },
            barWidth: '60%',
            data:[400, 520, 300, 334, 290]
          }
        ]
      };
      ;
      if (option && typeof option === "object") {
        myChart.setOption(option, true);
      }
    </script>
    <!-- Unnamed () -->
    <div id="u21" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u22" class="ax_default box_2">
    <div id="u22_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u23" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u24" class="ax_default box_1">
    <img id="u24_img" class="img " src="<%=path%>/demo/images/daping/u20.png"/>
    <div id="container4" style="height: 100%;margin-top:20px;"></div>
    <script type="text/javascript">
      var dom = document.getElementById("container4");
      var myChart = echarts.init(dom);
      var app = {};
      option = null;
      var i=0;
      app.title = '环形图';
      var colors=['#81C0C0','#FFBD9D','#FFE66F','#59ccf7','#c3b4df'];
      option = {
        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        grid : {

          top:10
        },
        legend: {
          orient: 'vertical',
          top:50,
          x: 'left',
          textStyle: {
            color: '#fff'
          },
          data:['WIFI采集','车辆采集','人像采集']
        },
        series: [
          {
            itemStyle : {
              normal : {
                color:function(){
                  return colors[i++];
                },
                label : {
                  show : false
                },
                labelLine : {
                  show : false
                }
              },
              emphasis : {
                label : {
                  show : true,
                  position : 'center',
                  textStyle : {
                    fontSize : '30',
                    fontWeight : 'bold'
                  }
                }
              }
            },
            name:'今日采集',
            type:'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: false,
                position: 'center'
              },
              emphasis: {
                show: true,
                textStyle: {
                  fontSize: '20',
                  fontWeight: 'bold'
                }
              }
            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data:[
              {value:335, name:'WIFI采集'},
              {value:310, name:'车辆采集'},
              {value:234, name:'人像采集'}
            ]
          }
        ]
      };
      if (option && typeof option === "object") {
        myChart.setOption(option, true);
      }
    </script>
    <!-- Unnamed () -->
    <div id="u25" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u26" class="ax_default box_2">
    <div id="u26_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u27" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u28" class="ax_default box_1">
    <img id="u28_img" class="img " src="<%=path%>/demo/images/daping/u28.png"/>
    <div id="container" style="height: 100%"></div>
    <script type="text/javascript">
      var dom = document.getElementById("container");
      var myChart = echarts.init(dom);
      var app = {};
      option = null;
      app.title = '设备情况';

      option = {
        backgroundColor:'#000066',
        title: {
          text: '设备情况'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['正常情况','异常情况']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01],
          axisLabel: {
            show: true,
            textStyle: {
              color: '#fff'
            }
          }
        },
        yAxis: {
          type: 'category',
          data: ['WIFI采集设备','WIFI审计设备','视频采集设备','卡口采集设备'],
          axisLabel: {
            show: true,
            textStyle: {
              color: '#fff'
            }
          }
        },
        series: [
          {
            name: '异常情况',
            type: 'bar',
            data: [120, 348, 903, 349]
          },
          {
            name: '正常情况',
            type: 'bar',
            data: [123, 294, 310, 121]
          }
        ]
      };
      ;
      if (option && typeof option === "object") {
        myChart.setOption(option, true);
      }
    </script>
    <!-- Unnamed () -->
    <div id="u29" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u30" class="ax_default box_2">
    <div id="u30_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u31" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u32" class="ax_default heading_1">
    <div id="u32_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u33" class="text" style="visibility: visible;">
      <p><span>WIFI视频联动数据中心</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u34" class="ax_default label">
    <div id="u34_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u35" class="text" style="visibility: visible;">
      <p><span id="dateYear"></span><span id="time"></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u36" class="ax_default label">
    <div id="u36_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u37" class="text" style="visibility: visible;">
      <p><span>今日采集数据量</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u38" class="ax_default label">
    <div id="u38_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u39" class="text" style="visibility: visible;">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u40" class="ax_default label">
    <div id="u40_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u41" class="text" style="visibility: visible;">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u42" class="ax_default label">
    <div id="u42_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u43" class="text" style="visibility: visible;">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u44" class="ax_default label">
    <div id="u44_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u45" class="text" style="visibility: visible;">
      <p><span>设备情况</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u46" class="ax_default heading_3">
    <div id="u46_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u47" class="text" style="visibility: visible;">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u48" class="ax_default heading_3">
    <div id="u48_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u49" class="text" style="visibility: visible;">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u50" class="ax_default heading_3">
    <div id="u50_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u51" class="text" style="visibility: visible;">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u52" class="ax_default heading_3">
    <div id="u52_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u53" class="text" style="visibility: visible;">
      <p><span style="font-family:'ArialMT', 'Arial';"></span><span style="font-family:'PingFangSC-Regular', 'PingFang SC';"></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u54" class="ax_default heading_3">
    <div id="u54_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u55" class="text" style="visibility: visible;">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u56" class="ax_default heading_3">
    <div id="u56_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u57" class="text" style="visibility: visible;">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u58" class="ax_default label">
    <div id="u58_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u59" class="text" style="visibility: visible;">
      <p><span>采集数据总量</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u60" class="ax_default label">
    <div id="u60_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u61" class="text" style="visibility: visible;">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u62" class="ax_default label">
    <div id="u62_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u63" class="text" style="visibility: visible;">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u64" class="ax_default label">
    <div id="u64_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u65" class="text" style="visibility: visible;">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Horizontal Line) -->
  <div id="u66" class="ax_default line">

    <!-- Unnamed () -->
    <div id="u67" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Horizontal Line) -->
  <div id="u68" class="ax_default line">

    <!-- Unnamed () -->
    <div id="u69" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Horizontal Line) -->
  <div id="u70" class="ax_default line">

    <!-- Unnamed () -->
    <div id="u71" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Horizontal Line) -->
  <div id="u72" class="ax_default line">

    <!-- Unnamed () -->
    <div id="u73" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Horizontal Line) -->
  <div id="u74" class="ax_default line">

    <!-- Unnamed () -->
    <div id="u75" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Horizontal Line) -->
  <div id="u76" class="ax_default line">

    <!-- Unnamed () -->
    <div id="u77" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Image) -->
  <div id="u78" class="ax_default image">

    <!-- Unnamed () -->
    <div id="u79" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u80" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u81" class="ax_default line">

      <!-- Unnamed () -->
      <div id="u82" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Horizontal Line) -->
    <div id="u83" class="ax_default line">

      <!-- Unnamed () -->
      <div id="u84" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Horizontal Line) -->
    <div id="u85" class="ax_default line">

      <!-- Unnamed () -->
      <div id="u86" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Horizontal Line) -->
    <div id="u87" class="ax_default line">

      <!-- Unnamed () -->
      <div id="u88" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u89" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u90" class="ax_default line">
      <img id="u90_img" class="img " src="<%=path%>/demo/images/daping/u90.png"/>
      <!-- Unnamed () -->
      <div id="u91" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Horizontal Line) -->
    <div id="u92" class="ax_default line">

      <!-- Unnamed () -->
      <div id="u93" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Horizontal Line) -->
    <div id="u94" class="ax_default line">

      <!-- Unnamed () -->
      <div id="u95" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Horizontal Line) -->
    <div id="u96" class="ax_default line">

      <!-- Unnamed () -->
      <div id="u97" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u98" class="ax_default box_2">
    <div id="u98_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u99" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u100" class="ax_default label">
    <div id="u100_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u101" class="text" style="visibility: visible;">
      <p><span>7日内采集数据增量</span></p>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u102" class="ax_default">

    <!-- Unnamed (Rectangle) -->
    <div id="u103" class="ax_default box_1">
      <img id="u103_img" class="img " src="<%=path%>/demo/images/daping/u103.png"/>
      <!-- Unnamed () -->
      <div id="u104" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Image) -->
    <div id="u105" class="ax_default image">
      <img id="u105_img" class="img " src="<%=path%>/demo/images/daping/u105.png"/>
      <!-- Unnamed () -->
      <div id="u106" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Group) -->
    <div id="u107" class="ax_default">

      <!-- Unnamed (Rectangle) -->
      <div id="u108" class="ax_default box_2">
        <div id="u108_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u109" class="text" style="display: none; visibility: hidden">
          <p><span></span></p>
        </div>
      </div>

      <!-- Unnamed (Rectangle) -->
      <div id="u110" class="ax_default label">
        <div id="u110_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u111" class="text" style="visibility: visible;">
          <p><span>人群聚集热力图</span></p>
        </div>
      </div>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u112" class="ax_default box_1">
    <img id="u112_img" class="img " src="<%=path%>/demo/images/daping/u112.png"/>
    <!-- Unnamed () -->
    <div id="u113" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u114" class="ax_default box_2">
    <div id="u114_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u115" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u116" class="ax_default label">
    <div id="u116_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u117" class="text" style="visibility: visible;">
      <p><span>今日新增MAC数量</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u118" class="ax_default heading_3">
    <div id="u118_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u119" class="text" style="visibility: visible;">
      <p><span>456，789</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u120" class="ax_default box_1">
    <img id="u120_img" class="img " src="<%=path%>/demo/images/daping/u28.png"/>
    <div id="container2" style="height: 100%"></div>
    <script type="text/javascript">
      var dom = document.getElementById("container2");
      var myChart = echarts.init(dom);
      var app = {};
      option = null;
      app.title = '坐标轴刻度与标签对齐';

      option = {
        backgroundColor:'#000066',
        color: ['#FFC78E'],
        tooltip : {
          trigger: 'axis',
          axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '1%',
          bottom: '3%',
          containLabel: true
        },
        xAxis : [
          {
            type : 'category',
            data : ['03-13', '03-14', '03-15', '03-16', '03-17', '03-18', '03-19'],
            axisTick: {
              alignWithLabel: true
            },
            axisLabel: {
              show: true,
              textStyle: {
                color: '#fff'
              }
            }
          }
        ],
        yAxis : [
          {
            type : 'value',
            axisLabel: {
              show: true,
              textStyle: {
                color: '#fff'
              }
            }
          }
        ],
        series : [
          {
            name:'总数',
            type:'bar',
            itemStyle: {
              normal: {
                label: {
                  show: true,
                  textStyle: {
                    color: '#800080'
                  }
                }
              }
            },
            barWidth: '50%',
            data:[100, 520, 200, 334, 390, 330, 220]
          }
        ]
      };
      ;
      if (option && typeof option === "object") {
        myChart.setOption(option, true);
      }
    </script>
    <!-- Unnamed () -->
    <div id="u121" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u122" class="ax_default box_2">
    <div id="u122_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u123" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u124" class="ax_default label">
    <div id="u124_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u125" class="text" style="visibility: visible;">
      <p><span>7日内新增MAC数量</span></p>
    </div>
  </div>

  <!-- Unnamed (Image) -->


  <!-- Unnamed (Image) -->


  <!-- Unnamed (Rectangle) -->
  <div id="u130" class="ax_default box_2">
    <div id="u130_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u131" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u132" class="ax_default label">
    <div id="u132_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u133" class="text" style="visibility: visible;">
      <p><span>今日新增车牌号数量</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u134" class="ax_default heading_3">
    <div id="u134_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u135" class="text" style="visibility: visible;">
      <p><span>56，789</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u136" class="ax_default box_1">
    <img id="u136_img" class="img " src="<%=path%>/demo/images/daping/u28.png"/>
    <div id="container3" style="height: 100%"></div>
    <script type="text/javascript">
      var dom = document.getElementById("container3");
      var myChart = echarts.init(dom);
      var app = {};
      option = null;
      app.title = '坐标轴刻度与标签对齐';

      option = {
        backgroundColor:'#000066',
        color: ['#3398DB'],
        tooltip : {
          trigger: 'axis'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis : [
          {
            type : 'category',
            data : ['03-13', '03-14', '03-15', '03-16', '03-17', '03-18', '03-19'],
            axisTick: {
              alignWithLabel: true
            },
            axisLabel: {
              show: true,
              textStyle: {
                color: '#fff'
              }
            }
          }
        ],
        yAxis : [
          {
            type : 'value',
            axisLabel: {
              show: true,
              textStyle: {
                color: '#fff'
              }
            }
          }
        ],
        series : [
          {
            name:'总数',
            type:'bar',
            itemStyle: {
              normal: {
                label: {
                  show: true,
                  textStyle: {
                    color: '#800080'
                  }
                }
              }
            },
            barWidth: '50%',
            data:[300, 220, 200, 334, 190, 230, 420]
          }
        ]
      };
      ;
      if (option && typeof option === "object") {
        myChart.setOption(option, true);
      }
    </script>
    <!-- Unnamed () -->
    <div id="u137" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u138" class="ax_default box_2">
    <div id="u138_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u139" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u140" class="ax_default label">
    <div id="u140_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u141" class="text" style="visibility: visible;">
      <p><span>7日内新增车牌号数量</span></p>
    </div>
  </div>

  <!-- Unnamed (Image) -->


  <!-- Unnamed (Rectangle) -->
  <div id="u144" class="ax_default heading_3">
    <div id="u144_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u145" class="text" style="visibility: visible;">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Horizontal Line) -->
  <div id="u146" class="ax_default line">

    <!-- Unnamed () -->
    <div id="u147" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Horizontal Line) -->
  <div id="u148" class="ax_default line">

    <!-- Unnamed () -->
    <div id="u149" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u150" class="ax_default box_2">
    <img id="u150_img" class="img " src="<%=path%>/demo/images/daping/u18.png"/>
    <div id="container8" style="height: 100%"></div>
    <script type="text/javascript">
      var dom = document.getElementById("container8");
      var myChart = echarts.init(dom);
      var app = {};
      option = null;
      app.title = '堆叠条形图';

      option = {
        tooltip : {
          trigger: 'axis',
          axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
          }
        },

        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis:  {
          type: 'value',
          axisLabel: {
            show: true,
            textStyle: {
              color: '#fff'
            }
          }
        },
        yAxis: {
          type: 'category',
          data: ['高家堡检查站','安定检查站','小马坊检查站','凤河营检查站','南庄村检查站','京台高速检查站','榆垡检查站'],
          axisLabel: {
            show: true,
            textStyle: {
              color: '#fff'
            }
          }
        },
        series: [
          {
            name: '进京车辆',
            type: 'bar',
            stack: '总量',
            label: {
              normal: {
                show: true,
                position: 'insideRight'
              }
            },
            data: [320, 302, 301, 334, 390, 330, 320]
          },
          {
            name: '出京车辆',
            type: 'bar',
            stack: '总量',
            label: {
              normal: {
                show: true,
                position: 'insideRight'
              }
            },
            data: [120, 132, 101, 134, 90, 230, 210]
          }
        ]
      };;
      if (option && typeof option === "object") {
        myChart.setOption(option, true);
      }
    </script>
    <!-- Unnamed () -->
    <div id="u151" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u152" class="ax_default box_2">
    <div id="u152_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u153" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u154" class="ax_default label">
    <div id="u154_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u155" class="text" style="visibility: visible;">
      <p><span>检查站过车统计</span></p>
    </div>
  </div>

  <!-- Unnamed (Image) -->
  <div id="u156" class="ax_default image">
    <div id="container7" style="height: 100%"></div>
    <script type="text/javascript">
      var dom = document.getElementById("container7");
      var myChart = echarts.init(dom);
      var app = {};
      option = null;
      option = {
        tooltip : {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          data:['WIFI探针','WIFI审计','卡口过车','视频数据','人像数据'],
          textStyle: {
            color: '#fff'
          }
        },
        toolbox: {
          feature: {

          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis : [
          {
            type : 'category',
            boundaryGap : false,
            data : ['周一','周二','周三','周四','周五','周六','周日'],
            axisLabel: {
              show: true,
              textStyle: {
                color: '#fff'
              }
            }
          }
        ],
        yAxis : [
          {
            type : 'value',
            axisLabel: {
              show: true,
              textStyle: {
                color: '#fff'
              }
            }
          }
        ],
        series : [
          {
            name:'WIFI探针',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[1200, 1320, 1010, 1340, 2900, 2300, 2210]
          },
          {
            name:'WIFI审计',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[2200, 1820, 1910, 2340, 2900, 3300, 3100]
          },
          {
            name:'卡口过车',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[1500, 2320, 2010, 1540, 1900, 3300, 4100]
          },
          {
            name:'视频数据',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[3200, 3320, 3010, 3340, 3900, 3300, 3200]
          },
          {
            name:'人像数据',
            type:'line',
            stack: '总量',
            label: {
              normal: {
                show: true,
                position: 'top'
              }
            },
            areaStyle: {normal: {}},
            data:[1820, 1932, 1901, 1934, 1490, 1330, 1320]
          }
        ]
      };
      ;
      if (option && typeof option === "object") {
        myChart.setOption(option, true);
      }
    </script>
    <!-- Unnamed () -->
    <div id="u157" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Image) -->


  <!-- Unnamed (Group) -->
  <div id="u160" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u161" class="ax_default line">
      <img id="u161_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u162" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u163" class="ax_default line">
      <img id="u163_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u164" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u165" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u166" class="ax_default line">
      <img id="u166_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u167" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u168" class="ax_default line">
      <img id="u168_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u169" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u170" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u171" class="ax_default line">
      <img id="u171_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u172" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u173" class="ax_default line">
      <img id="u173_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u174" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u175" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u176" class="ax_default line">
      <img id="u176_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u177" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u178" class="ax_default line">
      <img id="u178_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u179" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u180" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u181" class="ax_default line">
      <img id="u181_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u182" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u183" class="ax_default line">
      <img id="u183_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u184" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u185" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u186" class="ax_default line">
      <img id="u186_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u187" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u188" class="ax_default line">
      <img id="u188_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u189" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u190" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u191" class="ax_default line">
      <img id="u191_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u192" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u193" class="ax_default line">
      <img id="u193_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u194" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u195" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u196" class="ax_default line">
      <img id="u196_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u197" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u198" class="ax_default line">
      <img id="u198_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u199" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u200" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u201" class="ax_default line">
      <img id="u201_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u202" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u203" class="ax_default line">
      <img id="u203_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u204" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u205" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u206" class="ax_default line">
      <img id="u206_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u207" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u208" class="ax_default line">
      <img id="u208_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u209" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u210" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u211" class="ax_default line">
      <img id="u211_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u212" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u213" class="ax_default line">
      <img id="u213_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u214" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u215" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u216" class="ax_default line">
      <img id="u216_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u217" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u218" class="ax_default line">
      <img id="u218_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u219" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u220" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u221" class="ax_default line">
      <img id="u221_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u222" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u223" class="ax_default line">
      <img id="u223_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u224" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u225" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u226" class="ax_default line">
      <img id="u226_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u227" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u228" class="ax_default line">
      <img id="u228_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u229" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u230" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u231" class="ax_default line">
      <img id="u231_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u232" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u233" class="ax_default line">
      <img id="u233_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u234" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u235" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u236" class="ax_default line">
      <img id="u236_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u237" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u238" class="ax_default line">
      <img id="u238_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u239" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u240" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u241" class="ax_default line">
      <img id="u241_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u242" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u243" class="ax_default line">
      <img id="u243_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u244" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u245" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u246" class="ax_default line">
      <img id="u246_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u247" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u248" class="ax_default line">
      <img id="u248_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u249" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u250" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u251" class="ax_default line">
      <img id="u251_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u252" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u253" class="ax_default line">
      <img id="u253_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u254" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Image) -->


  <!-- Unnamed (Rectangle) -->
  <div id="u257" class="ax_default box_1">
    <img id="u257_img" class="img " src="<%=path%>/demo/images/daping/u257.png"/>
    <!-- Unnamed () -->
    <div id="u258" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u259" class="ax_default box_2">
    <div id="u259_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u260" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u261" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u262" class="ax_default line">
      <img id="u262_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u263" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u264" class="ax_default line">
      <img id="u264_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u265" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Image) -->
  <div id="u266" class="ax_default image">
    <img id="u266_img" class="img " src="<%=path%>/demo/images/daping/u266.png"/>
    <!-- Unnamed () -->
    <div id="u267" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u268" class="ax_default box_1">
    <img id="u268_img" class="img " src="<%=path%>/demo/images/daping/u268.png"/>
    <!-- Unnamed () -->
    <div id="u269" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u270" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u271" class="ax_default line">
      <img id="u271_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u272" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u273" class="ax_default line">
      <img id="u273_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u274" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Group) -->
  <div id="u275" class="ax_default">

    <!-- Unnamed (Horizontal Line) -->
    <div id="u276" class="ax_default line">
      <img id="u276_img" class="img " src="<%=path%>/demo/images/daping/u161.png"/>
      <!-- Unnamed () -->
      <div id="u277" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Vertical Line) -->
    <div id="u278" class="ax_default line">
      <img id="u278_img" class="img " src="<%=path%>/demo/images/daping/u163.png"/>
      <!-- Unnamed () -->
      <div id="u279" class="text" style="display: none; visibility: hidden">
        <p><span></span></p>
      </div>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u280" class="ax_default box_2">
    <div id="u280_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u281" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u282" class="ax_default label">
    <div id="u282_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u283" class="text" style="visibility: visible;">
      <p><span>政府小区</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u284" class="ax_default box_2">
    <div id="u284_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u285" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u286" class="ax_default label">
    <div id="u286_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u287" class="text" style="visibility: visible;">
      <p><span>购物中心</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u288" class="ax_default box_2">
    <div id="u288_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u289" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u290" class="ax_default label">
    <div id="u290_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u291" class="text" style="visibility: visible;">
      <p><span>交通枢纽</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u292" class="ax_default box_2">
    <div id="u292_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u293" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u294" class="ax_default label">
    <div id="u294_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u295" class="text" style="visibility: visible;">
      <p><span>重点场所</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u296" class="ax_default label">
    <div id="u296_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u297" class="text" style="visibility: visible;">
      <p><span>北臧村镇政府</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u298" class="ax_default label">
    <div id="u298_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u299" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u300" class="ax_default label">
    <div id="u300_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u301" class="text" style="visibility: visible;">
      <p><span>大兴区法院</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u302" class="ax_default label">
    <div id="u302_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u303" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u304" class="ax_default label">
    <div id="u304_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u305" class="text" style="visibility: visible;">
      <p><span>政府门口</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u306" class="ax_default label">
    <div id="u306_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u307" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u308" class="ax_default label">
    <div id="u308_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u309" class="text" style="visibility: visible;">
      <p><span>万源北路7号院</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u310" class="ax_default label">
    <div id="u310_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u311" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u312" class="ax_default label">
    <div id="u312_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u313" class="text" style="visibility: visible;">
      <p><span>旧宫新苑北区</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u314" class="ax_default label">
    <div id="u314_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u315" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u316" class="ax_default label">
    <div id="u316_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u317" class="text" style="visibility: visible;">
      <p><span>旧头路灵秀山庄</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u318" class="ax_default label">
    <div id="u318_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u319" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u320" class="ax_default label">
    <div id="u320_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u321" class="text" style="visibility: visible;">
      <p><span>润枫锦尚</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u322" class="ax_default label">
    <div id="u322_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u323" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u324" class="ax_default label">
    <div id="u324_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u325" class="text" style="visibility: visible;">
      <p><span>旧桥路紫郡府小区</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u326" class="ax_default label">
    <div id="u326_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u327" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u328" class="ax_default label">
    <div id="u328_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u329" class="text" style="visibility: visible;">
      <p><span>荟聚</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u330" class="ax_default label">
    <div id="u330_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u331" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u332" class="ax_default label">
    <div id="u332_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u333" class="text" style="visibility: visible;">
      <p><span>宜家</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u334" class="ax_default label">
    <div id="u334_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u335" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u336" class="ax_default label">
    <div id="u336_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u337" class="text" style="visibility: visible;">
      <p><span>物美超市广场</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u338" class="ax_default label">
    <div id="u338_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u339" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u340" class="ax_default label">
    <div id="u340_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u341" class="text" style="visibility: visible;">
      <p><span>居然之家</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u342" class="ax_default label">
    <div id="u342_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u343" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u344" class="ax_default label">
    <div id="u344_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u345" class="text" style="visibility: visible;">
      <p><span>永辉超市</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u346" class="ax_default label">
    <div id="u346_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u347" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u348" class="ax_default label">
    <div id="u348_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u349" class="text" style="visibility: visible;">
      <p><span>兴创大厦</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u350" class="ax_default label">
    <div id="u350_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u351" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u352" class="ax_default label">
    <div id="u352_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u353" class="text" style="visibility: visible;">
      <p><span>旧头路酷乐门歌厅</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u354" class="ax_default label">
    <div id="u354_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u355" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u356" class="ax_default label">
    <div id="u356_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u357" class="text" style="visibility: visible;">
      <p><span>航天万源广场</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u358" class="ax_default label">
    <div id="u358_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u359" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u360" class="ax_default label">
    <div id="u360_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u361" class="text" style="visibility: visible;">
      <p><span>地铁黄村西大街站</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u362" class="ax_default label">
    <div id="u362_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u363" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u364" class="ax_default label">
    <div id="u364_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u365" class="text" style="visibility: visible;">
      <p><span>旧宫地铁站</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u366" class="ax_default label">
    <div id="u366_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u367" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u368" class="ax_default label">
    <div id="u368_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u369" class="text" style="visibility: visible;">
      <p><span>团河路北段485总站</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u370" class="ax_default label">
    <div id="u370_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u371" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u372" class="ax_default label">
    <div id="u372_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u373" class="text" style="visibility: visible;">
      <p><span>刘二村东车站</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u374" class="ax_default label">
    <div id="u374_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u375" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u376" class="ax_default label">
    <div id="u376_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u377" class="text" style="visibility: visible;">
      <p><span>立高防水材料厂</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u378" class="ax_default label">
    <div id="u378_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u379" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u380" class="ax_default label">
    <div id="u380_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u381" class="text" style="visibility: visible;">
      <p><span>736公交总站西门</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u382" class="ax_default label">
    <div id="u382_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u383" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u384" class="ax_default label">
    <div id="u384_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u385" class="text" style="visibility: visible;">
      <p><span>礼贤844路公交总站</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u386" class="ax_default label">
    <div id="u386_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u387" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u388" class="ax_default label">
    <div id="u388_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u389" class="text" style="visibility: visible;">
      <p><span>423公交总站</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u390" class="ax_default label">
    <div id="u390_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u391" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u392" class="ax_default label">
    <div id="u392_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u393" class="text" style="visibility: visible;">
      <p><span>新清真寺</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u394" class="ax_default label">
    <div id="u394_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u395" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u396" class="ax_default label">
    <div id="u396_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u397" class="text" style="visibility: visible;">
      <p><span>田家营清真寺</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u398" class="ax_default label">
    <div id="u398_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u399" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u400" class="ax_default label">
    <div id="u400_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u401" class="text" style="visibility: visible;">
      <p><span>铜佛寺</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u402" class="ax_default label">
    <div id="u402_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u403" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u404" class="ax_default label">
    <div id="u404_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u405" class="text" style="visibility: visible;">
      <p><span>慨尔康科技门口</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u406" class="ax_default label">
    <div id="u406_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u407" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u408" class="ax_default label">
    <div id="u408_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u409" class="text" style="visibility: visible;">
      <p><span>红星医院</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u410" class="ax_default label">
    <div id="u410_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u411" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u412" class="ax_default label">
    <div id="u412_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u413" class="text" style="visibility: visible;">
      <p><span>大兴区中医院</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u414" class="ax_default label">
    <div id="u414_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u415" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u416" class="ax_default label">
    <div id="u416_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u417" class="text" style="visibility: visible;">
      <p><span>采育公墓</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u418" class="ax_default label">
    <div id="u418_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u419" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u420" class="ax_default label">
    <div id="u420_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u421" class="text" style="visibility: visible;">
      <p><span>沁水营村北养殖小区</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u422" class="ax_default label">
    <div id="u422_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u423" class="text" style="visibility: visible;">
      <p><span>4,000</span></p>
    </div>
  </div>

  <!-- Unnamed (Rectangle) -->
  <div id="u424" class="ax_default label">
    <div id="u424_div" class=""></div>
    <!-- Unnamed () -->
    <div id="u425" class="text" style="visibility: visible;">
      <p><span>布控预警</span></p>
    </div>
  </div>

  <!-- Unnamed (Image) -->
  <div id="u426" class="ax_default image">
    <img id="u426_img" class="img " src="<%=path%>/demo/images/daping/u426.jpg"/>
    <!-- Unnamed () -->
    <div id="u427" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Drop) -->
  <div id="u428" class="ax_default marker">
    <img id="u428_img" class="img " src="<%=path%>/demo/images/daping/u428.png"/>
    <!-- Unnamed () -->
    <div id="u429" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Drop) -->
  <div id="u430" class="ax_default marker">
    <img id="u430_img" class="img " src="<%=path%>/demo/images/daping/u428.png"/>
    <!-- Unnamed () -->
    <div id="u431" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Drop) -->
  <div id="u432" class="ax_default marker">
    <img id="u432_img" class="img " src="<%=path%>/demo/images/daping/u428.png"/>
    <!-- Unnamed () -->
    <div id="u433" class="text" style="display: none; visibility: hidden">
      <p><span></span></p>
    </div>
  </div>
</div>
</body>
</html>

