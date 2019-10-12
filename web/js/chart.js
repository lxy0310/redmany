$(function(){


    $(document).ready(function () {
        var data = $("#data").val().replace(/'/g, "\"");
        var jsonObject = JSON.parse(data);
        console.log(jsonObject);
        var type = jsonObject.type;
        var  datas=jsonObject.data;
        console.log(datas);

        if (type == 'pie') {

            $('#container').highcharts({
                chart: {
                    type: jsonObject.type,
                    /*options3d: {
                        enabled: true,
                        alpha: 45,
                        beta: 0
                    }*/
                },
                title: {
                    text:  jsonObject.titleText
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        //depth: 35,
                        dataLabels: {
                            enabled: true,
                            // distance: -30,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        },
                        showInLegend: true
                    }
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                series:jsonObject.data
                ,
                credits: {
                    enabled: false     //不显示LOGO
                }
                /* [{
                 name: '占比',
                 // distance: -10,
                 data: [
                     ['Firefox', 45.0],
                     ['IE', 26.8],
                     {
                         name: 'Chrome',
                         y: 12.8 //,
                         // sliced: true,
                         // selected: true
                     }
                 ]
             }]*/
            });

        } else {

            $('#container').highcharts({
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
                ,
                credits: {
                    enabled: false     //不显示LOGO
                }
            });
        }

    });


    /* var data =$("#data").val().replace(/'/g,"\""); //
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
var chart = Highcharts.chart('container', chart);*/





});