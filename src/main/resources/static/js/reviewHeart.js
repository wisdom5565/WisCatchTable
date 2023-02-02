let isLike = [];
let prIdx = [];
let revIdx = [];
let heartNum = [];
let data = [];
$(document).ready(function first(){
    $.ajax({
        type: 'GET',
        url: "/timeline/review/heart",
        success: function (data) {
            console.log(data);
            $.each(data, function(index, item) { // 데이터 =item
                isLike.push(item.isLike);
                prIdx.push(item.prIdx);
                revIdx.push(item.revIdx);
                heartNum.push(item.heartNum);
            });

            for (let i = 0; i < data.length; i++) {
                if (isLike[i] == true) {
                    console.log("⭕");
                    let like = "<span class='__like __on " + revIdx[i] + "'" +
                        "onclick='heart(" + prIdx[i] + "," + revIdx[i] + "," + isLike[i] + "," + heartNum[i] + ")'>"
                        + heartNum[i] + "</span>"
                    $('.heart' + revIdx[i]).append(like);
                } else {
                    console.log("❌");
                    let unlike = "<span class='__like " + revIdx[i] + "'" +
                        "onclick='heart(" + prIdx[i] + "," + revIdx[i] + "," + isLike[i] + "," + heartNum[i] + ")'>"
                        + heartNum[i] + "</span>"
                    $('.heart' + revIdx[i]).append(unlike);
                }
            }
            return data;
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("ERROR : " + textStatus + " : " + errorThrown);
        }
    });
    console.log(isLike)
    console.log(prIdx)
    console.log(revIdx.length)
    console.log(heartNum)

});

function heart(prIdx, revIdx, isLike, heartNum) {
    if (isLike) {
        console.log(isLike)
        hearting(prIdx, revIdx, false, heartNum);       // 좋아요 취소
    } else {
        console.log(isLike)
        hearting(prIdx, revIdx, true, heartNum);
    }

}

function hearting(prIdx, revIdx, check, heartNum) {
    console.log(heartNum);
    let param = {"prIdx": prIdx, "revIdx": revIdx, 'revLike' : heartNum+1};
    if (check) {
        $.ajax({
            type: 'POST',
            data: JSON.stringify(param),
            url: "/timeline/new/heart",
            contentType: "application/json",
            success: function (data) {
                console.log(data);
                if (data == 'OK') {
                    let liked = "<span class='__like __on " + revIdx + "'" +
                        "onclick='heart(" + prIdx + "," + revIdx + "," + true + "," + heartNum + ")'>"
                        + (heartNum+1) + "</span>"
                    $('.'+revIdx).replaceWith(liked);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("ERROR : " + textStatus + " : " + errorThrown);
            }
        });
    } else {
        let del;
        if(heartNum == 0) {
            del = {"prIdx": prIdx, "revIdx": revIdx, 'revLike' : heartNum}
        } else {
            del = {"prIdx": prIdx, "revIdx": revIdx, 'revLike' : heartNum-1}
        }
        $.ajax({
            type: 'POST',
            data: JSON.stringify(del),
            url: "/timeline/del/heart",
            contentType: "application/json",
            success: function (data) {
                console.log(data);
                if (data == 'OK') {
                    if(heartNum == 0) {
                        let unliked = "<span class='__like " + revIdx + "'" +
                            "onclick='heart(" + prIdx + "," + revIdx + "," + false + "," + heartNum+ ")'>"
                            +  0 + "</span>"
                        $('.' + revIdx).replaceWith(unliked);
                    } else {
                        let unliked = "<span class='__like " + revIdx + "'" +
                            "onclick='heart(" + prIdx + "," + revIdx + "," + false + "," + heartNum+ ")'>"
                            +  (heartNum-1) + "</span>"
                        $('.' + revIdx).replaceWith(unliked);
                    }

                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("ERROR : " + textStatus + " : " + errorThrown);
            }
        });
    }


}
