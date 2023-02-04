"<div class='__user-info'> " +
"<a class='profile'> <div class='profile-pic'> " +
"<img src='https://catchtable.co.kr/web-static/static_webapp_v2/img/noimg/profile_default_v2.png' class='img'" +
"style='background: url(&quot;data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7&quot;) center center no-repeat transparent;" +
"> </div> <h4 class='name username'> 닉네임<span class='txt nick"
+review+"'></span></h4> <div class='__post-meta'> <span class='__like comLike"+review+"'> </span> </div> </a> </div> " +
"<div class='__content'><p class='content"+review+"'>내용</p></div><div class='__post-meta'> " +
"<span class='__date date"+review+"'style='font-size: 13px;flex-direction: row-reverse;'>날짜</span> " +
"<a th:class='__more del"+review+"''>MORE</a></div>"


"<div class = '__comment-form' > <div class = '__box' > " +
"<input type = 'text' placeholder = '리뷰에 따뜻한 댓글을 달아주세요.' name = 'content' id = 'registReplyInput'" +
"style = 'font-family: auto; min-width: 150px;margin-right: 28px;margin-left: 16px;' > " +
"<button type = "button"> 등록 </button></div></div>"

"<div> <div class='modal ' +comIdx+ ' modal-overlay'> <div class="modal-window"> " +
"<div th:class='close-area close"+comIdx+"' onclick='closeCom("+comIdx+")'>X</div> <div class='content'> <div class='drawer-box'> " +
"<div class='drawer-box-header mb--20' style='padding: 0 20px 27px 0'> <h2 class='drawer-box-title ml-10' style='margin-bottom: 10px;'> 해당 댓글을 신고하시겠습니까?</h2></div></div> " +
"<div class='drawer-box-footer'> <div class='btn-group'> <button class='btn btn-lg btn-red btn"+comIdx+ "' type='button'style='width: 100%'>신고하기 </button></div> " +
"</div></div></div></div></div>"