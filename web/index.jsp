<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>Title</title>

</head>
<body>
<div id="container" style="width: 600px;height:400px;"></div>
<!-- 引入 highcharts.js -->
<script src="http://cdn.highcharts.com.cn/highcharts/highcharts.js"></script>
<script>
  // 图表配置
  $.ajax({
    url:"common",
    //data:{"method":"addForm","addForm":JSON.stringify(d),"FormName":FormName,"paramId":paramId},
    //async:false,
    success:function(data){
      var chart = Highcharts.chart('container', {
        title: {
          text: '2010 ~ 2016 年太阳能行业就业人员发展情况'
        },
        subtitle: {
          text: '数据来源：thesolarfoundation.com'
        },
        yAxis: {
          title: {
            text: '就业人数'
          }
        },
        legend: {
          layout: 'vertical',
          align: 'right',
          verticalAlign: 'middle'
        },
        plotOptions: {
          series: {
            label: {
              connectorAllowed: false
            },
            pointStart: 2010
          }
        },
        series: [{
          name: '安装，实施人员',
          data: [43934, 52503, 57177, 69658, 97031, 119931, 137133, 154175]
        }, {
          name: '工人',
          data: [24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]
        }, {
          name: '销售',
          data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
        }, {
          name: '项目开发',
          data: [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
        }, {
          name: '其他',
          data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
        }],
        responsive: {
          rules: [{
            condition: {
              maxWidth: 500
            },
            chartOptions: {
              legend: {
                layout: 'horizontal',
                align: 'center',
                verticalAlign: 'bottom'
              }
            }
          }]
        }
      });

    },
    error:function(data){
      layer.msg('服务器异常！',{icon:5});
    }
  });
  // 图表初始化函数
  var chart = Highcharts.chart('container', options);
</script>
</body>
</html>