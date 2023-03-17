<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>아이디찾기2</title>
    <link rel="stylesheet" href="/project2/css/idFind2.css">
</head>
<body>
    <div id="wrap">
        <div id="content_wrap">
            <div id="check_wrap">
                <svg id="check" xmlns="http://www.w3.org/2000/svg"   fill="currentColor" class="bi bi-check2" viewBox="0 0 16 16">
                    <path d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z"/>
                </svg>
            </div>
            <p>${name }님의 아이디는</p>
            <p>${id } 입니다.</p>
        </div>
        <div id="btn_wrap">
            <a class="btn2" href='/project2/login.jsp'><input class="btn" type="button" value="로그인"></a>
            <span id="width"></span>
            <a class="btn2" href='/project2/pwFind1.jsp'><input class="btn" type="button" value="비밀번호찾기"></a>
        </div>
    </div>
</body>
</html>