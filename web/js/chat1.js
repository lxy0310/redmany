var chart2 = new Highcharts.chart('container1', {
    chart: {
        type: 'pie',
        spacingBottom: 0,
        spacingTop: 0,
        spacingLeft: 0,
        spacingRight: 0
    },
    title: {
        text: null
    },
    credits: {
        enabled: false  // 禁用版权信息
    },
    yAxis: {
        title: {
            text: ''
        }
    },
    plotOptions: {
        pie: {
            shadow: false,
            center: ['50%', '50%']
        }
    },
    series: [{
        name: '伴侣得分',
        data: [{
            name: 'a',
            y: 20,
            color: '#32A6A3'
        }, {
            name: 'b',
            y: 40,
            color: '#ED93B5'
        }, {
            name: 'c',
            y: 40,
            color: '#FC983B'
        }],
        size: '70%',
        innerSize: '50%',
        dataLabels: {
            formatter: function () {
                return this.y > 5 ? this.point.name + ':</b> ' +
                    this.y : null;
            },
            color: '#ffffff',
            distance: -35
        }
    }, {
        name: '本人得分',
        data: [{
            name: 'a',
            y: 50,
            color: '#32A6A3'
        }, {
            name: 'b',
            y: 20,
            color: '#ED93B5'
        }, {
            name: 'c',
            y: 30,
            color: '#FC983B'
        }],
        size: '100%',
        innerSize: '70%',
        dataLabels: {
            formatter: function () {
                // display only if larger than 1
                return this.y > 1 ? '<b>' + this.point.name + ':</b> ' +
                    this.y : null;
            },
            color: '#ffffff',
            distance: -25
        },
        id: 'versions'
    }],
    responsive: {
        rules: [{
            condition: {
                maxWidth: 400
            },
            chartOptions: {
                series: [{
                    id: 'versions',
                    dataLabels: {
                        enabled: false
                    }
                }]
            }
        }]
    }
});