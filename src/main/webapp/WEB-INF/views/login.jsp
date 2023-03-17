<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <link rel="stylesheet" href="/css/login.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>

    window.onload = function(){
        init();
        bind();
    }   
    function init(){

    }
    function bind(){

        //이미지 슬라이드 효과
        function fnSlide() {
            $("#slide").animate({ "margin-left": "-511.27px"}, 3000, function () {
                $("#slide").css({ "margin-left": "0px" });
                $("img:first-child").insertAfter("img:last-child");
            });
        };
        setInterval(fnSlide, 4000);

        //비밀번호 보이기 효과
        $('.eyes').on('click',function(){
            $('.input').toggleClass('active');
            console.log(this);
            if( $('.input').hasClass('active') == true ){
                
                $(this).find('.bi.bi-eye-fill').attr('class',"bi bi-eye-slash-fill");
                $(this).parents('.input').find('#input').attr('type',"text");
             
            }else{
                $(this).find('.bi.bi-eye-slash-fill').attr('class',"bi bi-eye-fill");
                $(this).parents('.input').find('#input').attr('type','password');
            }
        /*
            JQeury
            -find함수: $()선택자의 하위요소를 찾는다
            -attr함수: attr(속성이름, 결과)=> 속성을 제어
            -parents함수: 셀렉터selector로 잡히는 모든 상위 요소를 반환한다.
            -this: 클릭한 그것
        */
        });
    



    }
    



    </script>

</head>
<body>
<form action="/pacebook/user/login.do" method="post">

    <div id="wrap">
        <div id="logo_wrap">
            <h1 id="logo">PACEBOOK</h1>
        </div>
        <div id="content_wrap">
            <div id="image_wrap">
                <div id="slide">

                    <img src="/image/slide1.jpg">
                    <img src="/image/slide2.jpg">
                    <img src="/image/slide3.jpg">
                    <img src="/image/slide4.jpg">
                 
                </div>
            </div>
            <div id="login_wrap">
                <div id="title_wrap">
                    <h1 id="title">PACEBOOK</h1>
                    <h1 id="title">LOGIN</h1>
                </div>
                
                <div id="input_wrap">
                    <input id="input" name='id' type="text" value='test1' placeholder=" 아이디입력">
                    <div class="input">
                        <input id="input" name='pw' type="password" value='a123456789!' placeholder=" 비밀번호입력">
                        <div class="eyes">
                            <i id="icon" class="bi bi-eye-fill"></i>
                        </div>
                    </div>
                    <div id="checkbox">
                        <input type="checkbox" name='login_keep' value="true"> 
                        <span id="login_con"> 로그인 상태 유지</span>
                    </div>
                </div>
                <div id="loginbtn_wrap">
                    <input id="loginbtn" type="submit" value="로그인">
                </div>
               
                
                <div id="detail_wrap">
                    <a id="detail" href="idFind1.jsp">아이디 찾기</a> |
                    <a id="detail" href="pwFind1.jsp">비밀번호 찾기</a> |
                    <a id="detail" href="join.jsp">회원가입</a> 
                </div>
            </div>
        </div>
    </div>
    
</form>
</body>
</html>