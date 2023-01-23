// 시간 버튼 반복돌리기
// let time =["오전 10:00","오전 11:00","오후 12:00","오후 1:00","오후 2:00"
//     ,"오후 5:00","오후 6:00" ,"오후 7:00","오후 8:00","오후 9:00","오후 10:00" ];
// const timeTag = document.querySelector('time');

const radio = document.querySelectorAll(".swiper-slide");
// radio.onclick = function(e) {
//     e.classList.add="checked";
//     console.log(e);
// }

$('.swiper-slide').click(function(e){
    e.classList.add="checked";
    console.log(e);
});

// radio.addEventListener('click', function(){
//     console.log(radio)
//     radio.classList.add="checked";
//     console.log(radio.value);
// });
function time(val) {
    console.log(val);
}

