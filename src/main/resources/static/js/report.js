//리뷰 신고
function reportReview(revIdx) {
    $('.' + revIdx).css("display", "flex");
    $('.btn' + revIdx).click(function () {
        $.ajax({
            type: 'GET',
            url: "/report/review/" + revIdx,
            success: function (data) {
                console.log(data);
                location.href = "/report/review/" + revIdx;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("ERROR : " + textStatus + " : " + errorThrown);
            }
        })
    })
}

// 댓글 신고 및 삭제
function reportComment(comIdx, isComm, revComIdx) {        // 매개변수로 댓글번호랑 본인댓글인지 여부를 보냄
    // 댓글 여부가 false이면 댓글 신고창으로 띄움
    if (!isComm) {
        $('.' + comIdx).css("display", "flex");
        $('.btn' + comIdx).click(function () {
            $.ajax({
                type: 'GET',
                url: "/report/comment/" + comIdx,
                success: function (data) {
                    console.log(data);
                    location.href = "/report/comment/" + comIdx;
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("ERROR : " + textStatus + " : " + errorThrown);
                }
            })
        })
    } else {
        // 댓글 삭제
        $('.isCom' + comIdx).text("해당 댓글을 삭제하시겠습니까?");
        $('.btn' + comIdx).text("댓글 삭제하기");
        $('.' + comIdx).css("display", "flex");
        $('.btn' + comIdx).click(function () {
            $.ajax({
                type: 'GET',
                url: "/timeline/del/comment/" + comIdx +"/"+revComIdx,
                success: function (data) {
                    console.log(data);
                    if(data != null) {
                        $('.' + comIdx).css("display", "none");
                        $('.comDiv'+comIdx).load();
                        alert('삭제 완료!')
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("ERROR : " + textStatus + " : " + errorThrown);
                }
            })
        })
    }
}

// 리뷰 신고창 닫기
function closeModal(revIdx) {
    $('.' + revIdx).css("display", "none");
}

// 댓글 신고창 닫기
function closeCom(comIdx) {
    $('.' + comIdx).css("display", "none");
}
