<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="/css/join.css">
   
    <script>
        window.onload = function () {
            let id_regex = /^[A-Za-z]{1}[A-Za-z0-9]{3,19}$/;
            let id = document.querySelector("#id");
            id.addEventListener("focusout", function () {
                document.querySelector("#id_").style.visibility = "visible";
                document.querySelector("#id_").style.position = "relative";
                if (id.value == "") {
                    document.querySelector("#id_").innerHTML = "필수 정보 입니다.";
                } else if (!id_regex.test(id.value)) {
                    document.querySelector("#id_").innerHTML = "4~20자의 영문 소문자, 대문자, 숫자만 사용 가능합니다.";
                } else {
                    document.querySelector("#id_").style.visibility = "hidden";
                    document.querySelector("#id_").style.position = "absolute";
                }
            });
            let pw_regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
            let pw = document.querySelector("#pw");
            pw.addEventListener("focusout", function () {
                document.querySelector("#pw_").style.visibility = "visible";
                document.querySelector("#pw_").style.position = "relative";
                if (pw.value == "") {
                    document.querySelector("#pw_").innerHTML = "필수 정보 입니다.";
                } else if (!pw_regex.test(pw.value)) {
                    document.querySelector("#pw_").innerHTML = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.";
                } else {
                    document.querySelector("#pw_").style.visibility = "hidden";
                    document.querySelector("#pw_").style.position = "absolute";
                }
            });
            document.querySelector("#pw2").addEventListener("focusout", function () {
                document.querySelector("#pw2_").style.visibility = "visible";
                document.querySelector("#pw2_").style.position = "relative";
                if (document.querySelector("#pw2").value == "") {
                    document.querySelector("#pw2_").innerHTML = "필수 정보 입니다.";
                } else if (pw.value != document.querySelector("#pw2").value) {
                    document.querySelector("#pw2_").innerHTML = "비밀번호가 일치하지 않습니다.";
                } else {
                    document.querySelector("#pw2_").style.visibility = "hidden";
                    document.querySelector("#pw2_").style.position = "absolute";
                }
            });
            let email_regex = /^[a-z0-9\.\-_]+@([a-z0-9\-]+\.)+[a-z]{2,6}$/;
            let email = document.querySelector("#email");
            email.addEventListener("focusout", function () {
                document.querySelector("#email_").style.visibility = "visible";
                document.querySelector("#email_").style.position = "relative";
                if (email.value == "") {
                    document.querySelector("#email_").innerHTML = "필수 정보 입니다.";
                } else if (!email_regex.test(email.value)) {
                    document.querySelector("#email_").innerHTML = "이메일 형식이 올바르지 않습니다."
                } else {
                    document.querySelector("#email_").style.visibility = "hidden";
                    document.querySelector("#email_").style.position = "absolute";
                }
            });
            let phone_regex = /^(\d{2,3}).-(\d{3,4}).-(\d{4})$/;
            let phone = document.querySelector("#phone");
            phone.addEventListener("focusout", function () {
                document.querySelector("#phone_").style.visibility = "visible";
                document.querySelector("#phone_").style.position = "relative";
                if (phone.value == "") {
                    document.querySelector("#phone_").innerHTML = "필수 정보 입니다.";
                } else if (!phone_regex.test(phone.value)) {
                    document.querySelector("#phone_").innerHTML = "전화번호 형식이 올바르지 않습니다."
                } else {
                    document.querySelector("#phone_").style.visibility = "hidden";
                    document.querySelector("#phone_").style.position = "absolute";
                }
            });
            document.querySelector("#join_btn").addEventListener("click", function () {
             
                let join = document.join;
                let ready = [];
                let istrue = false;
                if(id_regex.test(id.value)){
                    ready[0] = true;
                } else {
                    ready[0] = false;
                    id.value = "";
                    id.parentElement.lastElementChild.style.visibility = "visible";
                }
                if(pw_regex.test(pw.value)){
                    ready[1] = true;
                } else {
                    ready[1] = false;
                    pw.value = "";
                    pw.parentElement.lastElementChild.style.visibility = "visible";
                }
                if(email_regex.test(email.value)){
                    ready[2] = true;
                } else {
                    ready[2] = false;
                    email.value = "";
                    email.parentElement.lastElementChild.style.visibility = "visible";
                }
                if(phone_regex.test(phone.value)){
                    ready[3] = true;
                } else {
                    ready[3] = false;
                    phone.value = "";
                    phone.parentElement.lastElementChild.style.visibility = "visible";
                }
                for(i of ready){
                    if(i==false){
                        istrue = false;
                        break;
                    } else {
                        istrue = true;
                    }
                }
                if(istrue == true){
                    join.method = "post";
                    join.action = "/project2/pacebook/join";
                    join.submit();
                }
           });

        }
    </script>
</head>

<body>
<div class="pig">
    <div class="img">
        <img src="https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA3MjNfOTQg%2FMDAxNjI3MDUxNTExNTAw.sl3iU2DXUrkSNdvgGGoQNcn0xrP5GYmzLe5bedj07lQg.qwGHgvH5cDw_xOZKF2_UKpT_lQbD3sL6DV2tA-9SfhYg.JPEG.orumcafejeju%2F1626436398864.jpg&type=sc960_832
                        "style width="500px" height="700px" >
    </div>
    <div class="login">
        <div class="pink">
           Sign Up
        </div>
        <form name="join">
            <div class="text_boxes">
                <div class="box">
                    <div><b>아이디</b></div>
                    <input type="text" id="id" name="id" class="book" id="ID" required>
                    <div id="id_" class="hidden_msg"></div>
                </div>
                <br>
                <div class="box">
                    <div><b>비밀번호</b></div>
                    <input type="password" id="pw" name="pw" class="book" title="pw" required>
                    <div id="pw_" class="hidden_msg">비밀번호를 입력해주세요</div>
                </div>
                <br>
                <div class="box">
                    <div><b>비밀번호재확인</b></div>
                    <input type="password" id="pw2" name="pw2" class="book" title="pw" required>
                    <div id="pw2_" class="hidden_msg">비밀번호를 입력해주세요</div>
                </div>
                <br>
                <div class="box">
                    <div><b>이름</b></div>
                    <input type="text" id="name" name="name" class="book" title="name" required>
                    <div id="name_" class="hidden_msg">이름을 입력해주세요</div>
                </div>
                <br>
                <div class="box">
                    <div><b>이메일</b></div>
                    <input type="text" id="email" name="email" class="book" title="email" required>
                    <div id="email_" class="hidden_msg">이메일을 입력해주세요</div>
                </div>
                <br>
                <div class="box">
                    <div><b>전화번호</b></div>
                    <input type="text" id="phone" name="phone" class="book" title="number" oninput="autoHyphen(this)"
                        maxlength="13" required>
                    <div id="phone_" class="hidden_msg">번호를 입력해주세요</div>
                </div>
                <br>
                <div class="box">
                    <div><b>생년월일</b></div>
                    <input type="date" id="birth" name="birth" class="book" title="date">
                    <div id="birth_" class="hidden_msg">생년월일을 입력해주세요</div>
                </div>
                <br>
                <div class="box">
                    <div><b>성별</b></div>
                    <input id="radio1" type="radio" name="gender" value="male" checked><label for="radio1">
                        <img src="data:image/png;base64,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"
                        style="width: 50px;"></label>
                    <input id="radio2" type="radio" name="gender" value="female"><label for="radio2">
                        <img src="data:image/png;base64,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"
                        style="width: 50px;"></label>
                </div>
                <br>
                <div>
                    <button id="join_btn">가입하기</button>
                </div>
            </div>
        </form>
        <div id="login_btn">
            <a href="login.jsp" style="text-decoration-line:none; color: rgb(107, 105, 105);"  >로그인페이지로 이동</a>
        </div>
    </div>
</div>
    <script>
        let autoHyphen = (target) => {
            target.value = target.value.replace(/[^0-9]/g, "").replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, `$1-$2-$3`).replace(/(\-{1,2})$/g, "");
        };
    </script>
    </script>
</body>
</html>