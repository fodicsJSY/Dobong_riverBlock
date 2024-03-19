// 유속 bar chart

option = {
        xAxis: {
            type: 'category',
            data: ['지역명1', '지역명2', '지역명3', '지역명4', '지역명5', '지역명6', '지역명7', '지역명8', '지역명9', '지역명10']
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                data: [120, 200, 150, 80, 70, 110, 130, 180, 130, 30],
                type: 'bar',
                itemStyle: {
                    color: '#00A9FF' 
                }
            }
        ]
    };