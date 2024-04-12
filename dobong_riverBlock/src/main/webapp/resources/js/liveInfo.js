let gateTotalCount = document.getElementById("gateTotalCount");
let gateOpenCount = document.getElementById("gateOpenCount");
let gateCloseCount = document.getElementById("gateCloseCount");
let noSignalGateCount = document.getElementById("noSignalGateCount");

let totalSignalCount = document.getElementById("totalSignalCount");
let openSignalCount = document.getElementById("openSignalCount");
let noSignalCount = document.getElementById("noSignalCount");

let gateLiveList_arry;
let cameraCount;
let cameraIpList_arry;
let raderLiveList_arry;


// function liveInformation(cameraCountResp, cameraIpListResp, gateLiveListResp, raderLiveListResp){
function liveInformation(cameraCountResp, cameraIpListResp, gateLiveListResp){
    
    cameraCount = cameraCountResp.result;
    cameraIpList_arry = cameraIpListResp.result;
    gateLiveList_arry = gateLiveListResp.result;
    // raderLiveList_arry = raderLiveListResp.result;
    console.log("cameraCount", cameraCount);
    console.log("cameraIpList_arry", cameraIpList_arry);
    console.log("gateLiveList_arry", gateLiveList_arry);
    // console.log("raderLiveList_arry", raderLiveList_arry);


    console.log("gateLiveList_arry[0]", gateLiveList_arry[0]);
    gateLiveList_arry.forEach((item) => {

        gateTotalCount.innerHTML = `${item[0]} 개소`;
        gateOpenCount.innerHTML = `${item[1]} 개소`;
        gateCloseCount.innerHTML = `${item[2]} 개소`;
        noSignalGateCount.innerHTML = `${item[3]} 개소`;

    });



    totalSignalCount.innerHTML = `${cameraCount} 개소`;


    let okCount = 0;
    let noCount = 0;

    // IP 주소 확인 비동기 처리
    cameraIpList_arry.forEach((item) => {
        let ipAddr = item;
        checkPing(ipAddr, (status) => {
            if (status === 200) {
                okCount++;
            } else {
                noCount++;
            }
            // console.log("okCount", okCount);
            // console.log("noCount", noCount);
            // console.log("okCount + noCount", okCount + noCount);

            // 모든 IP 주소 확인 완료 후 카운트 표시
            if (okCount + noCount == cameraCount) {
                openSignalCount.innerHTML = `${okCount} CH`;
                noSignalCount.innerHTML = `${noCount} CH`;
            }
        });
    });
}

    

function checkPing(ipAddr, callback) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", ipAddr, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            callback(xhr.status);
        }
    };
    xhr.send();
}