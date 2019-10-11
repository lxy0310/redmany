$(function(){
     var data =$("#data").val().replace(/'/g,"\""); //
     console.log(data)

     var jsonObject= JSON.parse(data);
     console.log(jsonObject);
     var type = jsonObject.type;
     console.log(jsonObject.data);

    var chart = Highcharts.chart('container', {
        chart: {
            type: jsonObject.type  // 'pie'
        },
            title: {
            text: jsonObject.titleText
        },
        subtitle: {
            text: jsonObject.subtitleText
        },
        xAxis: {
            categories: jsonObject.xCategories,
            crosshair: true
        },
        yAxis: {
            min: 0,
            categories: jsonObject.xCategories,
            title: {
                text: jsonObject.yTitleText
            }
        },
        plotOptions: {
            column: {
                borderWidth: 0
            }
        },
        series: jsonObject.data
    });




// 图表初始化函数
var chart = Highcharts.chart('container', chart);
});