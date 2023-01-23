// $("#datepicker").datepicker({
//     dateFormat: 'yy-mm-dd',
//     prevText: '이전 달',
//     nextText: '다음 달',
//     monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
//     monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
//     dayNames: ['일', '월', '화', '수', '목', '금', '토'],
//     dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
//     dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
//     showMonthAfterYear: true,
//     yearSuffix: 'Year',
//     language: "ko",
//   });

  // $(function () {
  //   $("#datepicker").datepicker({
  //     onSelect: function (dateText) {
  //       let date = $("#datepicker")
  //         .datepicker({ dateFormat: "yyyy.mm.dd" })
  //         .val();
  //       console.log(date);
  //     },
  //   });
  // });

        //한개만 단순하게 만들때
        $("#datepicker").datepicker({
          language: "ko",
        });
  
        $(function () {
          $("#datepicker").datepicker({
            onSelect: function (dateText) {
              //  $('#datepicker-display').datepicker("setDate", $(this).datepicker("getDate"));
              var date = $("#datepicker")
                .datepicker({ dateFormat: "yyyy.mm.dd" })
                .val();
              console.log(date);
            },
          });
        });