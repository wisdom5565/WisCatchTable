function checkBlock(resaBisName){

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