function checkBlock(){
    let resaBisName = document.getElementById("resa_bis_name").innerText;

    $.ajax({
        url:'/reservation/blockCheck'
        ,type:'GET'
        ,success:function (data){
            if(data){
                alert("예약이 불가능한 회원입니다")
                location.href='/'
            }else{
                location.href='/reservation/'+resaBisName
            }
        }
    })
}