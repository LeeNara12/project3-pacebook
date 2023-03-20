<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>아이디찾기1</title>
    <link rel="stylesheet" href="/css/idFind1.css">
</head>
<script>
	
	//page이동되자마자 alert창 띄우기
	console.log('${alert }');
	if(${alert }){
		alert('${text }');
	}


	window.onload = function(){

        init();
        bind();

    }
    function init(){//초기화
        
        //생년월일 input이벤트 초기화
        let date = document.querySelector("#date");
        let warning = document.querySelector(".margin_left3");


        //휴대전화 input이벤트 초기화
        let phone1 = document.querySelector("#phone1");
        let phone2 = document.querySelector("#phone2");
        let phone3 = document.querySelector("#phone3");
        let phone = phone1 + '-' +phone2 + '-' + phone3; 
        
        
        

    }
    function bind(){//이벤트

        //생년월일 input이벤트 + 유효성 검사
        date.addEventListener("input",function(){
            let val = date.value.replace(/\D/g, "");
            let leng = val.length;

            let result = '';

            if(leng < 5){
                result = val;
            }else if(leng <7){
                result += val.substring(0,4);
                result += "-";
                result += val.substring(4);
            }else{
                result += val.substring(0,4);
                result += "-";
                result += val.substring(4,6);
                result += "-";
                result += val.substring(6);

                console.log(!checkValuidDate(result));
                console.log(result);

                if(!checkValuidDate(result)){
                    warning.classList.add("on");
                }else{
                    warning.classList.remove("on");
                }
            }
            

            date.value = result;

        })
        //생년월일 유효성 검사 메소드
        let checkValuidDate = function(value){
        console.log(1)
            let result = true;
            try{
                let date = value.split("-");
                let y = parseInt(date[0], 10),
                    m = parseInt(date[1], 10),
                    d = parseInt(date[2], 10);
                let dateRegex = /^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-.\/])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/;
                
                result = dateRegex.test(d+"-"+m+'-'+y);

            }catch(err){
                result = false;
            }

            return result;
        }
        




        //휴대전화 숫자만 입력되게 하는 이벤트
        phone1.addEventListener("input", function(){
            
            phone1.value = phone1.value.replace(/\D/g, "");

            
        })
       


    }
   
</script>

<body>
    <div id="wrap">

        <div id="top_box">
            <span id="margin_left">아이디찾기</span>
        </div>

        <div id="content">
            <div id="content_wrap">

				<form action='/pacebook/user/idFind1' method='post'>
                <div class="detail_wrap" id="margin_top">
                    <div class="name"> <span class="margin_left2">이름</span> <span class="star">*</span></div>
                    <div class="detail">
                        <input class="input_text" type="text" name='user_name'>
                    </div>
                </div>
                <div class="detail_wrap">
                    <div class="name"> <span class="margin_left2">생년월일</span> 
                        <span class="star">*</span>
                    </div>
                    <div class="detail">
                        <input class="input_text " type="text" name='user_birth' id='date' maxlength='10'>
                        <span class="margin_left3 " id="warning">유효하지 않은 날짜입니다.</span>
                    </div>
                    <span id="margin_left2">예)19990101</span>
                </div>
                <div class="detail_wrap" id="border_bottom">
                    <div class="name"> <span class="margin_left2">휴대전화</span> <span class="star">*</span></div>
                    <div class="detail">
                        <input class="input_block" type="text" name='phone1' id="phone1" maxlength="4">
                        <span id="hyphen">-</span>
                        <input class="input_block" type="text" name='phone2' id="phone2" maxlength="4">
                        <span id="hyphen">-</span>
                        <input class="input_block" type="text" name='phone3' id="phone3" maxlength="4">
                    </div>
                </div>
                <div id="idFind_wrap">
                    <input id="idFind_btn" type="submit" value="아이디찾기" >
                    <span id="space"></span>
                    <a id="idFind_btn" href="/pacebook/user"><input id="idFind_btn1" type="button" value="로그인하러가기"></a>
                </div>
				</form>

            </div>
           
        </div>

    </div>
</body>
</html>
