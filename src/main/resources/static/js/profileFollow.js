// 프로필 팔로잉
let profile = document.querySelector('.profile-follow');
let login = document.querySelector('.login-idx');
let timeLine = document.querySelector('.timeline-idx');
console.log(profile);
console.log(login);
console.log(timeLine);

if(profile.value == true) {
    let profilebtn = "<button type='button' onclick ='follow(" + login.value + "," + timeLine.value + ")' class='btn btn-md btn-outline-orange btn-rounded full-width t"
        + timeLine.value + "'>" +
        "<span class='label "+login.value+"'>팔로잉</span>" +
        "</button>"
    $('.profile').append(profilebtn);
} else {
    let profilebtn = "<button type='button' onclick ='follow(" + login.value + "," + timeLine.value + ")' class='btn btn-md btn-orange btn-rounded full-width t"
        + timeLine.value + "'>" +
        "<span class='label "+timeLine.value+"'>팔로우</span>" +
        "</button>"
    $('.profile').append(profilebtn);
}

function follow(prIdx, timeLineIdx) {
    const spanText = $('.' + timeLineIdx).text();
    console.log(spanText);
    if (spanText == '팔로우') {
        following(true, prIdx, timeLineIdx);
    } else {
        following(false, prIdx, timeLineIdx);
    }
}

function following(check, prIdx, timeLineIdx) {
    console.log(prIdx, timeLineIdx);
    let param = {"follower": prIdx, "following": timeLineIdx};
    if (check) {
        $.ajax({
            type: 'POST',
            data: JSON.stringify(param),
            url: "/timeline/following",
            contentType: "application/json",
            success: function (data) {
                console.log(data);
                if (data == 'OK') {
                    $('.t' + timeLineIdx).addClass('btn-outline-orange');
                    $('.t' + timeLineIdx).removeClass('btn-orange');
                    $('.' + timeLineIdx).text('팔로잉');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("ERROR : " + textStatus + " : " + errorThrown);
            }
        });
    } else {
        $.ajax({
            type: 'POST',
            data: JSON.stringify(param),
            url: "/timeline/unfollowing",
            contentType: "application/json",
            success: function (data) {
                console.log(data);
                if (data == 'OK') {
                    $('.t' + timeLineIdx).addClass('btn-orange');
                    $('.t' + timeLineIdx).removeClass('btn-outline-orange');
                    $('.' + timeLineIdx).text('팔로우');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("ERROR : " + textStatus + " : " + errorThrown);
            }
        });
    }
}

