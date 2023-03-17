<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="/project2/css/join_success.css">
    
    <script>
    window.onload = function(){
        document.querySelector(".myButton").addEventListener("click", function(){
            let main = document.login;
            main.method = "get";
            main.action = "/project2/main.jsp";
            main.submit();
        });
        document.querySelector(".follow").addEventListener("click", function(){
            let login = document.login;
            login.method = "post";
            login.action = "pacebook";
            login.submit();
        });
    }
    </script>
    

    </html>

    <body>
    <form name = "login">
        <!--컨텐츠 내용-->
        <div id="pacebook_contents">
			 <div id="content_wrap">
                <div id="top2">
                    <h1 id="wellcome">Pace Book에 오신 것을 환영합니다</h1>
                    <div id="wellcome1">

                        <p style="font-size: 27px;"><b>사진과 동영상을 보고 싶은 사람을 팔로우 해보세요</b></p>
                    </div>
                </div>
                <div class="bottom">
                    <div class="other_user">
                        <div class="user_section">
                            <div class="bname">
                                <p><b>${puvoList.get(0).getUser_id()}</b></p>
                            </div>
                            <div class="src">
                              <img src="/project2/${puvoList.get(0).getUser_profile() }"
                                alt="">
                            </div>
                        </div>
                        <div class="contact_section">
                            <!-- <img src="https://i.picsum.photos/id/237/200/300.jpg?hmac=TmmQSbShHz9CdQm0NkEjx1Dyh_Y984R9LpNrpvH2D_U"
                                alt=""> -->
                            <c:choose>
                            <c:when test="${empty pbvoList1.get(0).getBoard_url() }">
                            <img src="/project2/image/logo.png"
                                alt="">
                            </c:when>
                            <c:otherwise>
                            <img src="/project2/${pbvoList1.get(0).getBoard_url()}"
                                alt="">
                            </c:otherwise>
                            </c:choose>
                            <c:choose>
                            <c:when test="${empty pbvoList1.get(1).getBoard_url() }">
                            <img src="/project2/image/logo.png"
                                alt="">
                            </c:when>
                            <c:otherwise>
                            <img src="/project2/${pbvoList1.get(1).getBoard_url() }"
                                alt="">
                            </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="bottom_section">
                            <a href="#" class="follow">팔로우</a>
                        </div>
                    </div>

                    <div class="bottom">
                        <div class="other_user">
                            <div class="user_section">
                                <div class="bname1">
                                    <p><b>${puvoList.get(1).getUser_id()}</b></p>
                                </div>
                                <div class="src1">
<!--                                     <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7yWNG_boOH4cgRzFeABhwl5JCQ3qdre4AcA&usqp=CAU" -->
<!--                                         alt=""> -->
										<img src="/project2/${puvoList.get(1).getUser_profile() }">
                                </div>
                            </div>
                            <div class="contact_section">
                                <!-- <img src="https://i.picsum.photos/id/237/200/300.jpg?hmac=TmmQSbShHz9CdQm0NkEjx1Dyh_Y984R9LpNrpvH2D_U"
                                    alt=""> -->
                                    <c:choose>
                            <c:when test="${empty pbvoList2.get(0).getBoard_url() }">
                            <img src="/project2/image/logo.png"
                                alt="">
                            </c:when>
                            <c:otherwise>
                            <img src="/project2/${pbvoList2.get(0).getBoard_url()}"
                                alt="">
                            </c:otherwise>
                            </c:choose>
                            <c:choose>
                            <c:when test="${empty pbvoList2.get(1).getBoard_url() }">
                            <img src="/project2/image/logo.png"
                                alt="">
                            </c:when>
                            <c:otherwise>
                            <img src="/project2/${pbvoList2.get(1).getBoard_url() }"
                                alt="">
                            </c:otherwise>
                            </c:choose>
                            </div>
                            <div class="bottom_section">
                           	 <h1>${pl.user_id}</h1>
                                <a href="#" class="follow">팔로우</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="bottom2">
                    <footer id="join">
                        <a href="#" class="myButton">메인으로 가기</a>
                    </footer>
                </div>
            </form>
</body>