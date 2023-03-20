<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호찾기1</title>
    <link rel="stylesheet" href="/css/pwFind1.css">
</head>
<script>
	//page이동되자마자 alert창 띄우기
	console.log('${alert }');
	if(${alert }){
		alert('${text }');
	}

</script>

<body>
    <div id="wrap">
 	<form action='/pacebook/pwFind1' method='post'>

        <div id="top_box">
            <span id="margin_left">비밀번호찾기</span>
        </div>

        <div id="content">
            <div id="content_wrap">

                <div class="detail_wrap" id="margin_top">
                    <div class="name"> <span id="margin_left">이름</span> <span class="star">*</span></div>
                    <div class="detail">
                        <input class="input_text" type="text" name='user_name'>
                    </div>
                </div>
                <div class="detail_wrap" id="border_bottom">
                    <div class="name"> <span id="margin_left">아이디</span>
                        <span class="star">*</span>
                    </div>
                    <div class="detail">
                        <input class="input_text" type="text" name='user_id'>
                    </div>
                    
                </div>
              
                <div id="pwFind_wrap">
                    <input id="pwFind_btn" type="submit" value="비밀번호 찾기">
                    <span id="space"></span>
                    <a id="pwFind_btn" href="/pacebook/user"><input id="pwFind_btn1" type="button" value="로그인하러가기"></a>
                </div>
				
            </div>
        </div>

    </form>
    </div>
</body>
</html>