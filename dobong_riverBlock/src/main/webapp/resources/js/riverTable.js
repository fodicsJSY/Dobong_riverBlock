// let tableDataList; 

//페이지 로드시
document.addEventListener("DOMContentLoaded", function() {
    reiverMakeTable();
});


function reiverMakeTable(data){
    // tableDataList = data.tableDataList;

    // console.log("tableDataList", tableDataList );
    // console.log("테이블 생성");

    var riverTableContainer = document.getElementById("riverTableContainer");
    riverTableContainer.innerHTML = ""; // Clear previous data

    var gateTable = document.createElement("table");
    gateTable.className = "gateTable";
    riverTableContainer.appendChild(gateTable);

    var gateThead = document.createElement("thead");
    gateThead.className = "gateThead";
    gateTable.appendChild(gateThead);

    var htr = document.createElement("tr");
    gateThead.appendChild(htr);

    createCell(htr, "th", "gatetd gateName", "위치");
    createCell(htr, "th", "gatetd", "수위");
    createCell(htr, "th", "gatetd", "유속");
    createCell(htr, "th", "gatetd", "유량");
    createCell(htr, "th", "gatetd signal", "통신상태");

    
        var gateTbody = document.createElement("tbody");
        gateTbody.className = "gateTbody";
        gateTable.appendChild(gateTbody);
    
        var tr = document.createElement("tr");
        gateTbody.appendChild(tr);
    /********** 이 아래로는 코드 수정 필요  ****************/



    // // 데이터 삽입
    // tableDataList.forEach(function (item) {    
    //     var tr = document.createElement("tr");
    //     gateTbody.appendChild(tr);
    //     createCell(tr, "td", "gatetd", item.gateName);

    //     var div1 = document.createElement("div");
    //     div1.className = "gateIconBox";
    //     tr.appendChild(div1);

    //     let gateImg =  document.createElement("img");
    //     gateImg.className = "gateIcon";

        
    //     // console.log("item.gateStatus : ", item.gateStatus );
        

    //     if(item.gateStatus == 'close'){
    //         gateImg.src = "/resources/img/iconBTN_GateClose.png";
    //     }
    //     if(item.gateStatus == 'open'){
    //         gateImg.src = "/resources/img/iconBTN_GateOpen.png";
    //     }
    //     div1.appendChild(gateImg);
        
    //     if(item.gateStatus == ''){
    //         div1.innerHTML = "-";
    //     }

        
    //     let signalImg =  document.createElement("img");
    //     signalImg.className = "signalIcon";

    //     // console.log("item.commStatus : ", item.commStatus );
    //     if(item.commStatus == 'on'){
    //         signalImg.src = "/resources/img/connect-signalOK.png";
    //     }
    //     if(item.commStatus == 'off'){
    //         signalImg.src = "/resources/img/connect-signalNO.png";
    //     }

    //     // createCell(tr, "td", "gatetd", data);
    //     createCell(tr, "td", "gatetd gate", div1);
    //     createCell(tr, "td", "gatetd", signalImg.outerHTML);
    // });
}


// 셀 생성 함수
function createCell(row, elementType, className, content) {
    var cell = document.createElement(elementType);
    cell.className = className;
    
    // 이미지 엘리먼트인 경우에는 바로 추가
    if (typeof content === 'object' && content instanceof HTMLElement) {
        cell.appendChild(content);
    } else {
        cell.innerHTML = content;
    }

    row.appendChild(cell);
}
