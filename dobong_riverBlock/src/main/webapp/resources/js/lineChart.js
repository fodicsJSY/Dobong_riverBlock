let daliyCountList;
let dateDataChart; 

function lineChart(data) {
    daliyCountList = data.daliyCountList;

    // console.log("daliyCountList", daliyCountList);
    // console.log("일별 구동 차트");

    // 이전에 있던 차트 객체가 있으면 삭제
    if (dateDataChart || daliyCountList == null) {
        dateDataChart.dispose();
    }



    dateDataChart = echarts.init(document.getElementById('dateData'));



    // 카메라 이름 목록 추출
    const cameras = [...new Set(daliyCountList.map(entry => entry.cameraName))];
    // console.log("cameras : ", cameras);

    const colors = ['#00A9FF', '#FFB840', '#FF5A46', '#00BD9F', '#785FFF', '#F28B8C', '#989486', '#516F7D', '#28E6EB', '#28695F'];
    option = {
        color: colors,
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            orient: 'vertical',
            right: 0, // 오른쪽으로 이동
            top: 'middle', // 세로 중앙 정렬
            textStyle: {
                color: '#FFFFF' 
            },
            data: cameras
        },
        grid: {
            left: '3%',
            right: '10%',
            bottom: '5%',
            containLabel: true
        },
        xAxis: {
            type: 'category', // x축 타입을 category로 변경
            boundaryGap: false,
            data: Array.from({ length: 31 }, (_, i) => i + 1) // 1부터 31까지의 배열 생성
        },
        yAxis: {
            type: 'value'
        },
        series: cameras.map(camera => {
            const cameraData = Array.from({ length: 31 }, (_, i) => {
                const currentDate = i + 1;
                const matchingEntry = daliyCountList.find(entry => {
                    const entryDate = new Date(entry.logDate).getDate();
                    return entryDate === currentDate && entry.cameraName === camera;
                });
                return matchingEntry ? parseInt(matchingEntry.cnt) : 0;
            });
            // console.log("cameraData : ", cameraData);
            return {
                name: camera,
                type: 'line',
                data: cameraData
            };
        })
    };

    dateDataChart.setOption(option);
}




