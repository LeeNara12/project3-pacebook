<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>pacebook</title>
    <link rel="stylesheet" href="/css/board.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
    <script>

        

              
        window.onload = function(){// 페이지가 로딩한 후 실행한 메소드
            init();
            bind();
        }
        function init(){
            let input_file = document.querySelector('#input_file');
        }
        function bind(){
            
            //프로필사진 클릭이벤트
            let profile = document.querySelector('#profile123');
            let blackLayer = document.querySelector('.blackLayer');
            let popup = document.querySelector('#popup_profile_wrap');

            profile.addEventListener('click',function(e){

                e.stopPropagation();

                let width1 = document.body.clientWidth;
                let height1 = document.body.clientHeight; 

                
                blackLayer.style.width = width1;
                blackLayer.style.height = height1;
                blackLayer.style.opacity = "0.3";
                blackLayer.style.display = "block";

                popup.style.display = "block";
                
            })
            
            document.body.addEventListener('click',function(){
                if(popup.style.display = "block"){

                    popup.style.display = "none";

                }

            })

            ///////////////////////////////////////////////

            //파일업로드 버튼을 input버튼으로 바꾸는 메소드
            let file_btn = document.querySelector('#file_upload_btn');
            let input_btn = document.querySelector('input')
            let upload_btn = document.querySelectorAll('#file_plus, #file_upload_btn');
            
            upload_btn.forEach( i => {
                
                i.addEventListener('click', function(){
                    
                    $("#input_file")[0].type = "radio";
                    $("#input_file")[0].type = "file";
                    setTimeout(function(){
                        input_btn.click();
                    }, 0);
                    $("#images").css({ "margin-left": "0px" });
                    let images = document.querySelector("#images")
                    while(images.hasChildNodes()){
                        images.removeChild(images.firstChild);
                    }
                    
                    
                });
    
            });
                



            //이미지 업로드 메소드
            

            input_file.addEventListener('change', function(){

                console.log(input_btn.files)
                for(let file of input_file.files){

                    //이미지 태그 만들기
                    let img = document.createElement("img");

                    //file을 브라우저에 로드하고 가상의 주소 만들기
                    img.src = URL.createObjectURL(file);
                    img.style.width = '100%';
                    img.style.width = '100%';
                    
                    //만들어놓은 이미지 태그에 넣기
                    document.querySelector('#images').appendChild(img);

                }


            })

            
           







        }



                //이미지 하나씩 보는 이벤트
        //479.61px
        // function fnSlide() {
        //     $("#images").animate({ "margin-left": "-479.61px"}, 300)
        // };

        function fnRight() {
            console.log('right 들어감');
            console.log($('#images'));
            $("#images").animate({ "margin-left": "-479.61px"}, 300, function () {
                $("#images").css({ "margin-left": "0px" });
                $("#images img:first-child").insertAfter("#images img:last-child");
               
            });

        };

        function fnLeft() {
            console.log('left 들어감');
            console.log($('#images'));
            $("#images").css({ "margin-left": "-479.61px" });
            $("#images img:last-child").insertBefore("#images img:first-child");
            // 옮기기
            $("#images").animate({ "margin-left": "0"}, 300, function () {
                // $("#images").css({ "margin-left": "479.61px" });
               
               
            });
        }
    </script>
</head>
<body>
    <div class="blackLayer"></div>
    <form action="/pacebook/main" method="post">
    <div id="top">
        <a href='/pacebook/main' class='a'><span id="logo">PACEBOOK</span></a>
        <div id="top_right">
            <button type="button" id="back_btn" class="top_btn">
                <svg class="icon" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-arrow-left-circle" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8zm15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-4.5-.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5z"/>
                </svg>
                <a href='/pacebook/main' class='a'><span class='btn_text'>뒤로가기</span></a>
            </button>
            <input type="file" style="display:none" id="input_file" multiple="multiple">
            <button type="button" id="file_upload_btn" class="top_btn">
                <svg class="icon" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-folder2-open" viewBox="0 0 16 16">
                    <path d="M1 3.5A1.5 1.5 0 0 1 2.5 2h2.764c.958 0 1.76.56 2.311 1.184C7.985 3.648 8.48 4 9 4h4.5A1.5 1.5 0 0 1 15 5.5v.64c.57.265.94.876.856 1.546l-.64 5.124A2.5 2.5 0 0 1 12.733 15H3.266a2.5 2.5 0 0 1-2.481-2.19l-.64-5.124A1.5 1.5 0 0 1 1 6.14V3.5zM2 6h12v-.5a.5.5 0 0 0-.5-.5H9c-.964 0-1.71-.629-2.174-1.154C6.374 3.334 5.82 3 5.264 3H2.5a.5.5 0 0 0-.5.5V6zm-.367 1a.5.5 0 0 0-.496.562l.64 5.124A1.5 1.5 0 0 0 3.266 14h9.468a1.5 1.5 0 0 0 1.489-1.314l.64-5.124A.5.5 0 0 0 14.367 7H1.633z"/>
                </svg>
                <span class="btn_text" >파일 업로드</span>
            </button>
            <button type="submit" id="make_board_btn" class="top_btn">  <!--여기서 전송-->
                <svg class="icon" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-plus-square" viewBox="0 0 16 16">
                    <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
                    <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                  </svg>
                <span class="btn_text">게시글 작성</span>
            </button>
        </div>
    </div>
    <div id="main">
        <div id="main_left">
            <div id="left_box">
                <div id="profile_box">
                    <div id="profile_top">
                        <div id="profile" class="profile_div">
                            <img class="profile" id='profile123' src="/${vo1.user_profile}">
                        </div>
                        <div id="id">
                            <span>${vo1.user_id}</span>
                        </div>
                    </div>
                    <div id="profile_middle">
                        <div id="follow">
                            <span>팔로워</span>
                        </div>
                        <div id="follow_count">
                            <span>${followList_no}</span>
                        </div>
                    </div>
                    <div id="profile_bottom">
                        <div id="following">
                            <span>팔로우</span>
                        </div>
                        <div id="following_count">
                            <span>${followerList_no}</span>
                        </div>
                    </div>
                </div>
                <div id="friend_box">
                    <div id="friend_box_top">
                        <span>팔로우 목록</span>
                    </div>
                    <ul id="friend_list">
                    
                    
                        <li id="friend">
                            <div id="friend_profile" class="profile_div">
                                <img class="profile" src="/image/slide1.jpg">
                            </div>
                            <div id="friend_name">
                                <span>서한수</span>
                            </div>
                        </li>
                     

                        <!-- 친구 목록 추가 -->
                        


                    </ul>
                </div>
            </div>
        </div>
        <div id="main_center">
            <div id="center_box">
                <div id="image_box">
                    <div id="left" onclick="fnLeft()"><i class="bi bi-chevron-left"></i></div>
                    <ul>
                        <li class="margin_left1" id="images">
                            <p id="file_plus" ><i class="bi bi-plus-square"></i> 파일 업로드 </p>
                            <!--여기서 게시물 이미지 여러개 넣기 -->
                        </li>
                    </ul>
                    <div id="right" onclick="fnRight()"><i class="bi bi-chevron-right"></i></div>
                </div>
                <div id="content_box">
                    <textarea id="board_content" placeholder="내용을 입력해 주세요."></textarea>
                </div>
            </div>
        </div>
        <div id="main_right">
            <div id="right_box">
                <div id="right_box_top">
                    <span>내 게시글 목록</span>
                </div>
                
                <div id="my_board_list_wrap">
                    <div id="my_board_list">
                        
                        <!-- <div id='my_board'></div>
                        <div id='my_board'></div>
                        <div id='my_board'></div>
                        <div id='my_board'></div>
                        <div id='my_board'></div> -->

                        <!-- 내 게시글 목록 -->


                    </div>

                </div>
            </div>
        </div>
    </div>
    <div id="popup_profile_wrap">
        <img id="popup_profile" src="/${vo1.user_profile}">
    </div>    
    </form>
</body>
</html>