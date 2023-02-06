function delRes(){


    if (!confirm("저장리스트에서 삭제하시겠습니까?")) {

    }else {
        const saveIdx = document.getElementById("saveIdx");
        const prIdx = document.getElementById("prIdx");
        let param = {"saveIdx": saveIdx.value};
        $.ajax({
            type: 'DELETE',
            data: JSON.stringify(param),
            url: '/mypage/saveList',
            contentType: "application/json",
            success: function (data) {
                console.log(data);
                if (data = 'ok') {
                    location.reload();
                } else {
                    window.reload();
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("ERROR : " + textStatus + " : " + errorThrown);
            }
        });
    }
}