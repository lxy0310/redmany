$(function () {

    $(document).ready(function () {

        // Build the chart
        $('#container').highcharts({
            chart: {
                type: 'pie'
            },
            title: {
                text: '数值显示在图形区域'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        distance: -30,
                        format:'<b>{point.y}</b>',
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
            series: [{
                name: '占比',
                // distance: -10,
                data: [
                    ['Firefox',   45.0],
                    ['IE',       26.8],
                    {
                        name: 'Chrome',
                        y: 12.8 //,
                        // sliced: true,
                        // selected: true
                    }
                ]
            }]
        });
    });

});