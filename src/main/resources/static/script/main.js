window.onload = function () {

    let searchCheckBox = document.querySelector("#show_search_checkbox");
    let menuCheckBox = document.querySelector("#show_menu_checkbox");

    document.querySelector("#notice_btn").addEventListener("click", function () {
        document.querySelector("#notice_page").style.visibility = "visible";
    })

    document.querySelector("#notice_close").addEventListener("click", function () {
        document.querySelector("#notice_page").style.visibility = "hidden";
    })

    document.querySelector("#message_btn").addEventListener("click", function () {
        document.querySelector("#message_room").style.visibility = "visible";
    })

    document.querySelector("#message_close_btn").addEventListener("click", function () {
        document.querySelector("#message_room").style.visibility = "hidden";
        document.querySelector("#message_room_top2").style.visibility = "hidden";
        searchCheckBox.checked = false;
        document.querySelector("#message_menu").style.visibility = "hidden";
        menuCheckBox.checked = false;
    })

    searchCheckBox.addEventListener("click", function () {
        if (searchCheckBox.checked) {
            document.querySelector("#message_room_top2").style.visibility = "visible";
        } else {
            document.querySelector("#message_room_top2").style.visibility = "hidden";
        }
    })

    menuCheckBox.addEventListener("click", function () {
        if (menuCheckBox.checked) {
            document.querySelector("#message_menu").style.visibility = "visible";
        } else {
            document.querySelector("#message_menu").style.visibility = "hidden";
        }
    })

    // 메세지창 드래그
    let msg_room = document.querySelector("#message_room");

    document.addEventListener("mousedown", dragStart);
    document.addEventListener("mousemove", drag);
    document.addEventListener("mouseup", dragEnd);

    let mouseX = 0;
    let mouseY = 0;
    let itemX;
    let itemY;
    let m_moveX;
    let m_moveY;
    let dragging = false;
    function dragStart(e){
        console.log(e);
        if(e.target == document.querySelector("#message_room_top1")){
            document.querySelector("body").setAttribute("style", "user-select:none");
            mouseX = e.clientX;
            mouseY = e.clientY;
            itemX = msg_room.offsetLeft;
            itemY = msg_room.offsetTop;
            dragging = true;
            return false;
        }
    }
    function drag(e){
        if(dragging){
            m_moveX = e.clientX - mouseX;
            m_moveY = e.clientY - mouseY;
            msg_room.style.left = itemX + m_moveX +"px";
            msg_room.style.top = itemY + m_moveY + "px";
        }
    }
    function dragEnd(e){
        document.querySelector("body").removeAttribute("style");
        dragging = false;
    }
    msg_room.ondragstart = function(e){
        return false;
    }

    
    // 내용이 길면 더보기 버튼 생기기
    function showMoreBtn() {
        let smx = document.querySelectorAll(".show_more_box");
        smx.forEach(function(box){
            if(box.previousElementSibling.clientWidth >= 255){
                box.style.display = "inline";
            } else {
                if((box.previousElementSibling.className.indexOf("cct") != -1)){
                    if(box.previousElementSibling.clientWidth >= 196){
                        box.style.display = "inline";
                    } else {
                        box.style.display = "none";
                    }
                } else {
                    box.style.display = "none";
                }
            }
        })
    }

    // 게시글 내용, 댓글 더보기 버튼
    function showMoreBtn2() {
        let moreBtn = document.querySelectorAll(".show_more_btn");
        
        moreBtn.forEach(function(btn){
            btn.addEventListener("click", function(e){
                e.target.parentNode.previousElementSibling.classList.remove("board_content_text");
                e.target.parentNode.style.display = "none";
            })
        })
    }
    
    let cmbArrayB = new Array(); // 게시글 댓글 더보기 버튼 담는 배열
    let cmbArrayC = new Array(); // 댓글 답글 더보기 버튼 담는 배열
    //게시글 댓글 모두보기 버튼
    function showComment(){
        let bcs = document.querySelectorAll("#comment_top");   
        let cmb = document.querySelectorAll(".show_comment");
        cmb.forEach(function(btn){
            btn.addEventListener("click", function(e){
    
                let number = e.target.getAttribute("data-bon");
                e.target.parentElement.parentElement.lastElementChild.lastElementChild.setAttribute("data-url", "bcomment");
                if(e.target.innerHTML.indexOf("모두보기") != -1){
                    let xhr = new XMLHttpRequest();
                    let nextURL = "/pacebook/showcomment?board_no="+number;
                    xhr.open("POST", nextURL);
                    xhr.setRequestHeader('Accept', 'application/json');
                    xhr.send();
                    xhr.onload = function(){
                        let data = JSON.parse(xhr.response);
                        data.forEach( uc => {
                            let comment_html = "        <li id='comment'>"
                            + "        <div id='comment_top'>"
                            + "            <div id='board_comment_profile' class='profile_div'>"
                            + "                <img class='profile' src='/"+uc.paceUserVO.user_profile+"'/>"
                            + "            </div>"
                            + "            <div>"
                            + "                <div class='comment_text_box_top'>"
                            + "                    <div class='comment_id'>"
                            + "                        <span>"
                            + "                            "+uc.paceUserVO.user_id
                            + "                        </span>"
                            + "                    </div>"
                            + "                    <div class='comment_time'>"
                            + "                        <span>"+uc.paceCommentVO.comment_time_s+"</span>"
                            + "                    </div>"
                            + "                    <div class='comment_modify'>";
                            if(uc.paceCommentVO.comment_modify != 0){
                                comment_html += "<span>(수정됨)</span>"
                            }
                            comment_html +="       </div>"
                            + "                </div>"
                            + "                <div class='comment_text'>"
                            + "                    <div class='board_content_text'>"
                            + "                        <span>"
                            + "                            "+uc.paceCommentVO.comment_content
                            + "                        </span>"
                            + "                    </div>"
                            + "                    <span class='show_more_box'>"
                            + "                        <span>... </span>"
                            + "                        <span class='show_more_btn'>더보기</span>"
                            + "                    </span>"
                            + "                </div>"
                            + "                <div class='comment_text_box_bottom'>"
                            + "                    <div>"
                            + "                           <span>좋아요 "+uc.paceCommentVO.comment_like+"개</span>"
                            + "                    </div>"
                            + "                    <div id='comment_comment'>";
                            if(uc.paceCommentVO.comment_cmc_cnt != 0){
                                comment_html += "<span id='c_comment_btn' data-con='"+uc.paceCommentVO.comment_no+"'>답글 "+uc.paceCommentVO.comment_cmc_cnt+"개 더보기</span>"
                            } else {
                                comment_html += "<span id='c_comment_btn' data-con='"+uc.paceCommentVO.comment_no+"'>답글 달기</span>"
                            }
                            comment_html += "      </div>"
                            + "                </div>"
                            + "            </div>"
                            + "        </div>"
                            + "        <ul id='c_comment_list'>"
                            + "        </ul>"
                            + "    </li>";
                            e.target.parentElement.nextElementSibling.nextElementSibling.firstElementChild.innerHTML += comment_html;
                            showMoreBtn();
                            showMoreBtn2();
                            showCmComment();
                        })
                    }
                    let cmbObject = {number, bftext: e.target.innerHTML};
                    cmbArrayB = cmbArrayB.filter(e => e.number !== number);
                    cmbArrayB.push(cmbObject);
                    e.target.innerHTML = "댓글 더보기 닫기";
                    e.target.style.color = "rgb(170, 170, 170)";
                    e.target.parentElement.nextElementSibling.nextElementSibling.style.visibility = "visible";
                    e.target.parentElement.nextElementSibling.nextElementSibling.style.position = "relative";
                } else if(e.target.innerHTML == "댓글 더보기 닫기"){
                    let curbtn = cmbArrayB.find(e => e.number === number);
                    e.target.innerHTML = curbtn.bftext;
                    e.target.style.color = "black";
                    e.target.parentElement.nextElementSibling.nextElementSibling.querySelectorAll("#comment").forEach( x => {
                        x.remove();
                    })
                    e.target.parentElement.nextElementSibling.nextElementSibling.style.visibility = "hidden";
                    e.target.parentElement.nextElementSibling.nextElementSibling.style.position = "absolute";
                    bcs.forEach((e) => {
                        e.style.backgroundColor = "white";
                    })
                } else if(e.target.innerHTML == "댓글 달기"){
                    e.target.parentElement.parentElement.querySelector("#board_comment").focus();
                }
                e.target.parentElement.parentElement.lastElementChild.lastElementChild.setAttribute("data-no", number);
            })
        })
    }
    
    //답글 달기 버튼
    function showCmComment(){
        let bcs = document.querySelectorAll("#comment_top");
        let ccbtn = document.querySelectorAll("#c_comment_btn");
        ccbtn.forEach(function(btn){
            btn.addEventListener("click", function(e){
                bcs.forEach((e) => {
                    e.style.backgroundColor = "white";
                })
                e.target.offsetParent.nextElementSibling.lastElementChild.setAttribute("data-url", "ccomment");
                let number = e.target.getAttribute("data-con");
                if(e.target.innerHTML == "답글 달기"){
                    e.target.parentElement.parentElement.parentElement.parentElement.style.backgroundColor = "rgb(230, 230, 230)";
                    e.target.offsetParent.parentElement.lastElementChild.lastElementChild.setAttribute("data-no", number);
                } else {
                    if(e.target.innerHTML.indexOf("더보기") != -1){
                        let xhr = new XMLHttpRequest();
                        let nextURL = "/pacebook/showcmcomment?comment_no="+number;
                        xhr.open("POST", nextURL);
                        xhr.setRequestHeader('Accept', 'application/json');
                        xhr.send();
                        xhr.onload = function(){
                            let data = JSON.parse(xhr.response);
                            data.forEach( ucmc => {
                                let cmComment_html = "<li id='c_comment'>"
                                + "<span id='c_commet_arrow'>└</span>"
                                + "<div id='board_comment_profile' class='profile_div'>"
                                + "    <img class='profile' src='/"+ucmc.paceUserVO.user_profile+"'>"
                                + "</div>"
                                + "<div>"
                                + "    <div class='comment_text_box_top'>"
                                + "        <div class='comment_id'>"
                                + "            <span>"
                                + "                "+ucmc.paceUserVO.user_id
                                + "            </span>"
                                + "        </div>"
                                + "        <div class='comment_time'>"
                                + "            <span>"+ucmc.paceCmCommentVO.cmComment_time_s+"</span>"
                                + "        </div>"
                                + "        <div class='comment_modify'>";
                                if(ucmc.paceCmCommentVO.cmComment_modify != 0){
                                    cmComment_html += "            <span>(수정됨)</span>"
                                }
                                cmComment_html += "        </div>"
                                + "    </div>"
                                + "    <div class='comment_text'>"
                                + "        <div class='board_content_text cct'>"
                                + "            <span>"
                                + "                "+ucmc.paceCmCommentVO.cmComment_content
                                + "            </span>"
                                + "        </div>"
                                + "        <span class='show_more_box'>"
                                + "            <span>... </span>"
                                + "            <span class='show_more_btn'>더보기</span>"
                                + "        </span>"
                                + "    </div>"
                                + "    <div class='comment_text_box_bottom'>"
                                + "        <div>"
                                + "            <span>"
                                + "                좋아요 "+ucmc.paceCmCommentVO.cmComment_like+"개"
                                + "            </span>"
                                + "        </div>"
                                + "        <div id='comment_comment'>"
                                + "            <span>"
                                + "                답글달기"
                                + "            </span>"
                                + "        </div>"
                                + "    </div>"
                                + "</div>"
                                + "</li>";
                                e.target.parentElement.parentElement.parentElement.parentElement.nextElementSibling.innerHTML += cmComment_html;
                            })
                            showMoreBtn();
                            showMoreBtn2();
                        }
                        let cmbObject = {number, bftext: e.target.innerHTML};
                        cmbArrayC = cmbArrayC.filter(e => e.number !== number);
                        cmbArrayC.push(cmbObject);
                        e.target.parentElement.parentElement.parentElement.parentElement.nextElementSibling
                        .style.visibility = "visible";
                        e.target.parentElement.parentElement.parentElement.parentElement.nextElementSibling
                        .style.position = "relative";
                        e.target.innerHTML = "답글 닫기";
                        e.target.parentElement.parentElement.parentElement.parentElement.style.backgroundColor = "rgb(230, 230, 230)";
                        e.target.offsetParent.parentElement.lastElementChild.lastElementChild.setAttribute("data-no", number);
                    } else {
                        e.target.offsetParent.querySelectorAll("#c_comment").forEach( cc => {
                            cc.remove();
                        })
                        e.target.parentElement.parentElement.parentElement.parentElement.nextElementSibling
                        .style.visibility = "hidden";
                        e.target.parentElement.parentElement.parentElement.parentElement.nextElementSibling
                        .style.position = "absolute";
                        let curbtn = cmbArrayC.find(e => e.number === number);
                        e.target.innerHTML = curbtn.bftext;
                        e.target.offsetParent.parentElement.lastElementChild.lastElementChild.setAttribute("data-no", 
                        e.target.offsetParent.parentElement.children[2].firstElementChild.getAttribute("data-bon"));
                        e.target.offsetParent.nextElementSibling.lastElementChild.setAttribute("data-url", "bcomment");
                    }
                }
            })
        })
    }

    // 게시물 오른쪽 탑 메뉴버튼
    function boardMenu() {
        let menuBtn =  document.querySelectorAll("#board_menu");
    
        menuBtn.forEach((btn) => {
            btn.addEventListener("click", function(){
                if(btn.nextElementSibling.style.visibility == "visible"){
                    btn.nextElementSibling.style.visibility = "hidden";
                } else {
                    btn.nextElementSibling.style.visibility = "visible";
                }
            })
        })
    }

    // 댓글 아이콘 버튼
    function boardIcon() {
        let bcb = document.querySelectorAll("#board_comment_btn");
    
        bcb.forEach((btn)=>{
            btn.addEventListener("click", function(){
                btn.parentElement.parentElement.parentElement.querySelector(".show_comment").click();
                btn.parentElement.parentElement.parentElement.querySelector("#board_comment").focus();
            })
        })
    }

    //게시물 팔로우 버튼
    function boardFollow() {
        let boardFollowBtn = document.querySelectorAll(".board_follow_btn");
    
        boardFollowBtn.forEach((btn)=>{
            btn.addEventListener("click", function(){
                console.log("클릭");
                let xhr = new XMLHttpRequest();
                let userNo = btn.getAttribute("data-un");
                let obj = {user_no: userNo};
                xhr.open("post", "/pacebook/follow");
                xhr.setRequestHeader("Content-type", "application/json");
                xhr.setRequestHeader("Accept", "application/json");
                xhr.send(JSON.stringify(obj));
                xhr.onload = () => {
                    let data = JSON.parse(xhr.response);
                    console.log(data);
                    if(btn.querySelector("span").innerHTML == "팔로우"){
                        let pl_html = "<li id='pl' data-un='"+data.user_no+"'>"
                        + "<div class='friend_profile_outline1'>"
                        + "    <a href='/pacebook/profile?user_no="+data.user_no+"' class='friend_profile_a'>"
                        + "           <div class='friend_profile_outline2'>"
                        + "            <div id='friend_profile' class='profile_div'>"
                        + "                <img class='profile' src='/"+data.user_profile+"'>"
                        + "             </div>"
                        + "        </div>"
                        + "    </a>"
                        + "   </div>"
                        + "<div id='friend_profile_name'>"
                        + "    <span>"+data.user_name+"</span>"
                        + "</div>"
                        + "</li>"
                        document.querySelector("#profile_list").innerHTML += pl_html;
                        btn.querySelector("span").innerHTML = "팔로우 취소"
                    } else {
                        let pl = document.querySelectorAll("#pl");
                        pl.forEach(function(e){
                            if(e.getAttribute("data-un") == userNo){
                                e.remove();
                            }
                        })
                        btn.querySelector("span").innerHTML = "팔로우"
                    }
                    oneTime = false;
                }
            })
        })
    }
    
    let followBtns = document.querySelectorAll(".follow_btn");
    // 팔로우버튼
    function friendFollow(){
        
        followBtns.forEach((btn) =>{
            btn.addEventListener("click", () => {
                let xhr = new XMLHttpRequest();
                let userNo = { user_no : btn.getAttribute("data-un")};
                xhr.open("post", "/pacebook/follow");
                xhr.setRequestHeader("Content-type", "application/json");
                xhr.setRequestHeader("Accept", "application/json");
                xhr.send(JSON.stringify(userNo));
                xhr.onload = () => {
                    let data = JSON.parse(xhr.response);
                    let pl_html = "<li id='pl' data-un='"+data.user_no+"'>"
                    + "<div class='friend_profile_outline1'>"
                    + "    <a href='/pacebook/profile?user_no="+data.user_no+"' class='friend_profile_a'>"
                    + "           <div class='friend_profile_outline2'>"
                    + "            <div id='friend_profile' class='profile_div'>"
                    + "                <img class='profile' src='/"+data.user_profile+"'>"
                    + "             </div>"
                    + "        </div>"
                    + "    </a>"
                    + "   </div>"
                    + "<div id='friend_profile_name'>"
                    + "    <span>"+data.user_name+"</span>"
                    + "</div>"
                    + "</li>"
                    document.querySelector("#profile_list").innerHTML += pl_html;
                    btn.parentElement.remove();
                }
            })
        })
    }
    

    // 페이징
    let nextURL;
    let boardList = document.querySelector("#board_list");
    let boardArea = document.querySelector("#board_area");
    let screenHeight = screen.height;
    let oneTime = false;
    let boardCount = document.querySelectorAll("#board").length;
    
    document.querySelector("#board_area").addEventListener("scroll", onscroll);

    function onscroll(){
        let blHeight = boardList.clientHeight;
        let scrollPosition = boardArea.scrollTop;
        if(blHeight-screenHeight <= scrollPosition && !oneTime && boardCount%5 == 0){
            oneTime = true;
            madeBoard();
        }
    }
    function madeBoard(){
        let xhr = new XMLHttpRequest();
        pagenum = Math.ceil(boardCount/5)+1;
        nextURL = "/pacebook/moreboard?pagenum="+pagenum;
        xhr.open("POST", nextURL);
        xhr.setRequestHeader('Accept', 'application/json');
        xhr.send();
        xhr.onload = function(){
            let data = JSON.parse(xhr.response);
            if(data.length != 0){
                data.forEach( e => {
                    let board_html = "<li id='board'>"
                    + "<div id='board_top'>"
                    + "    <div id='board_top_left'>"
                    + "        <div id='board_profile' class='profile_div'>"
                    + "            <img class='profile' src='/"+e.paceUserVO.user_profile+"'>"
                    + "        </div>"
                    + "        <div id='board_id'>"
                    + "            "+e.paceUserVO.user_id
                    + "        </div>"
                    + "    </div>"
                    + "    <button id='board_menu' class='board_btn'>"
                    + "        <svg xmlns='http://www.w3.org/2000/svg' width='29' height='29' fill='currentColor' class='bi bi-list' viewBox='0 0 16 16'>"
                    + "            <path fill-rule='evenodd' d='M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5z'></path>"
                    + "        </svg>"
                    + "    </button>"
                    + "    <div id='board_menu_box'>"
                    + "        <div id='board_menu_arrow'></div>";
                    if(e.paceBoardVO.board_mine != 1){
                        board_html += "<button class='board_menus board_follow_btn' data-un='"+e.paceUserVO.user_no+"'>";
                        if(e.paceBoardVO.board_follow != 0){
                            board_html += "                            <span>팔로우 취소</span>";
                        } else {
                            board_html += "                            <span>팔로우</span>";
                        }
                        board_html += "                </button>";
                    }
                    board_html += "        <button class='board_menus'>"
                    + "            게시글 상세보기"
                    + "        </button>"
                    + "    </div>"
                    + "</div>"
                    + "<div id='board_image'>"
                    + "    <img class='board_image' src='"+e.paceBoardVO.board_url+"'>"
                    + "</div>"
                    + "<div id='board_tool'>"
                    + "    <div id='board_tool_left'>"
                    + "        <button id='board_like_btn' class='board_btn'>"
                    + "            <svg xmlns='http://www.w3.org/2000/svg' width='29' height='29' fill='currentColor' class='bi bi-heart' viewBox='0 0 16 16'>"
                    + "                <path d='m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z'></path>"
                    + "            </svg>"
                    + "        </button>"
                    + "        <button id='board_comment_btn' class='board_btn'>"
                    + "            <svg xmlns='http://www.w3.org/2000/svg' width='29' height='29' fill='currentColor' class='bi bi-chat' viewBox='0 0 16 16'>"
                    + "                <path d='M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z'></path>"
                    + "            </svg>"
                    + "        </button>"
                    + "        <button id='board_share_btn' class='board_btn'>"
                    + "            <svg xmlns='http://www.w3.org/2000/svg' width='29' height='29' fill='currentColor' class='bi bi-send' viewBox='0 0 16 16'>"
                    + "                <path d='M15.854.146a.5.5 0 0 1 .11.54l-5.819 14.547a.75.75 0 0 1-1.329.124l-3.178-4.995L.643 7.184a.75.75 0 0 1 .124-1.33L15.314.037a.5.5 0 0 1 .54.11ZM6.636 10.07l2.761 4.338L14.13 2.576 6.636 10.07Zm6.787-8.201L1.591 6.602l4.339 2.76 7.494-7.493Z'></path>"
                    + "            </svg>"
                    + "        </button>"
                    + "    </div>"
                    + "    <button id='board_save_btn' class='board_btn'>"
                    + "        <svg xmlns='http://www.w3.org/2000/svg' width='29' height='29' fill='currentColor' class='bi bi-bookmark' viewBox='0 0 16 16'>"
                    + "            <path d='M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z'></path>"
                    + "        </svg>"
                    + "    </button>"
                    + "</div>"
                    + "<div id='board_bottom'>"
                    + "    <div id='like_count'>";
                    if(e.paceBoardVO.board_like != 0){
                        board_html += "좋아요 "+e.paceBoardVO.board_like+"개"
                    }
                    board_html += "    </div>"
                    + "    <div class='board_content'>"
                    + "        <div class='board_content_text'>"
                    + "            <span>"
                    + "                "+e.paceBoardVO.board_content
                    + "            </span>"
                    + "        </div>"
                    + "        <span class='show_more_box' style='display: none;'>"
                    + "            <span>... </span>"
                    + "            <span class='show_more_btn'>더보기</span>"
                    + "        </span>"
                    + "    </div>"
                    + "            <div id='comment_count'>";
                    if(e.paceBoardVO.board_comment_cnt == 0){
                        board_html += "<span class='show_comment' data-bon='"+e.paceBoardVO.board_no+"'>댓글 달기</span>";
                    } else {
                        board_html += " <span class='show_comment' data-bon='"+e.paceBoardVO.board_no+"'>댓글 "+e.paceBoardVO.board_comment_cnt+"개 모두보기</span>";
                    }
                    board_html += "            </div>"
                    + "    <div id='board_time'>"
                    + "        <span>"
                    + "            "+e.paceBoardVO.board_time_s
                    + "        </span>"
                    + "    </div>"
                    + "    <div id='board_comment_area'>"
                    + "        <ul id='comment_list'></ul>"
                    + "    </div>"
                    + "    <div id='board_comment_box'>"
                    + "        <textarea id='board_comment' name='content'></textarea>"
                    + "        <button id='comment_btn' data-no='"+e.paceBoardVO.board_no+"' data-url='bcomment'>게시</button>"
                    + "    </div>"
                    + "</div>"
                    + "</li>";
                    document.querySelector("#board_list").innerHTML += board_html;
                })
                oneTime = false;
                boardCount = document.querySelectorAll("#board").length; 
                showMoreBtn();
                showMoreBtn2();
                boardMenu();
                boardIcon();
                boardFollow();
                showComment();
                showCmComment();
                commentText();
            } else {
                oneTime = true;
            }
        }
    }

    // 추천친구목록 페이징
    let friendList = document.querySelector("#friend_list");
    let friendArea = document.querySelector("#friend_area");
    let friendCount = document.querySelectorAll("#friend").length;
    let twoTime = false;

    document.querySelector("#friend_area").addEventListener("scroll", nfulScroll);

    function nfulScroll(){
        let flHeight = friendList.clientHeight;
        let scrollPosition = friendArea.scrollTop;
        if(flHeight-screenHeight <= scrollPosition && !twoTime && friendCount%20 == 0){
            twoTime = true;
            madefriend();
        }
    }
    function madefriend(){
        let xhr = new XMLHttpRequest();
        pagenum = Math.ceil(friendCount/20)+1;
        nextURL = "/pacebook/notfollow?pagenum="+pagenum;
        xhr.open("POST", nextURL);
        xhr.setRequestHeader("Accept", "application/json")
        xhr.send();
        xhr.onload = function(){
            let data = JSON.parse(xhr.response);
            if(data.length != 0){
                data.forEach( e => {
                    let friend_html = document.querySelector("#friend").cloneNode(true);
                    friend_html.querySelector("#right_profile > img").src = "/"+e.user_profile;//프로필
                    friend_html.querySelector("#right_name > span").innerHTML = e.user_id;//아이디
                    friend_html.querySelector("#friend > a").href = "/pacebook/profile?user_no="+e.user_no;//프로필 페이지 주소
                    friend_html.querySelector("#friend > button").setAttribute("data-un", e.user_no);
                    document.querySelector("#friend_list").append(friend_html);
                    followBtns.append(friend_html.querySelector("#friend > button"));
                })
                twoTime = false;
                friendCount = document.querySelectorAll("#friend").length; 
                friendFollow();
            } else {
                twoTime = true;
            }
        }
    }

    //댓글,답글 입력
    function commentText(){
        let cb = document.querySelectorAll("#comment_btn");
        cb.forEach( btn => {
            btn.addEventListener("click", e => {
                let no = e.target.getAttribute("data-no");
                let content = { content: e.target.previousElementSibling.value
                                , no: no}
                let xhr = new XMLHttpRequest();
                let url = "/pacebook/"+e.target.getAttribute("data-url");
                xhr.open("post", url);
                xhr.setRequestHeader("Content-type", "application/json");
                xhr.send(JSON.stringify(content));
                xhr.onload = () => {
                    if(url.indexOf("bcomment") != -1){
                        if(e.target.parentElement.parentElement.querySelector(".show_comment").innerHTML == "댓글 달기"){
                            //댓글이 없는 상태에서 댓글을 달았을 때
                            e.target.parentElement.parentElement.querySelector(".show_comment").innerHTML = "댓글 1개 모두보기";
                            e.target.parentElement.parentElement.querySelector(".show_comment").click();
                            e.target.previousElementSibling.value = "";
                        } else {
                            //comment_list가 열려있지 않은 상태에서 댓글 달았을 때
    
                            if(e.target.parentElement.parentElement.querySelector(".show_comment").innerHTML.indexOf("닫기") != -1){
                                //comment_list가 열려있는 상태에서 댓글을 달았을 때
                                e.target.parentElement.parentElement.querySelector(".show_comment").click();
                            }
    
                            let text = e.target.parentElement.parentElement.querySelector(".show_comment").innerHTML;
                            let cn = Number(text.substring(text.indexOf("댓글")+2, text.indexOf("개")))+1;
                            let newText = e.target.parentElement.parentElement.querySelector(".show_comment").innerHTML = "댓글 "+cn+"개 모두보기";
                            let cmbObject = {no, bftext: newText};
                            cmbArrayB = cmbArrayB.filter(e => e.number == no);
                            cmbArrayB.push(cmbObject);
                            e.target.parentElement.parentElement.querySelector(".show_comment").click();
                            e.target.previousElementSibling.value = "";
                        }
                    } else if(url.indexOf("ccomment") != -1) {
                        debugger
                        let comments = e.target.parentElement.parentElement.querySelectorAll("#comment");
                        comments.forEach( cc => {
                            if(cc.querySelector("#c_comment_btn").getAttribute("data-con") == no){
                                if(cc.querySelector("#c_comment_btn").innerHTML == "답글달기"){
                                    //답글이 없는 상태에서 댓글을 달았을 때
                                    cc.querySelector("#c_comment_btn").innerHTML = "답글 1개 더보기";
                                    cc.querySelector("#c_comment_btn").click();
                                    e.target.previousElementSibling.value = "";
                                } else {
                                    //c_comment_list가 열려있지 않은 상태에서 답글 달았을 때
            
                                    if(cc.querySelector("#c_comment_btn").innerHTML.indexOf("닫기") != -1){
                                        //c_comment_list가 열려있는 상태에서 답글을 달았을 때
                                        cc.querySelector("#c_comment_btn").click();
                                    }
            
                                    let text = cc.querySelector("#c_comment_btn").innerHTML;
                                    let cn = Number(text.substring(text.indexOf("답글")+2, text.indexOf("개")))+1;
                                    let newText = cc.querySelector("#c_comment_btn").innerHTML = "답글 "+cn+"개 더보기";
                                    let cmbObject = {no, bftext: newText};
                                    cmbArrayC = cmbArrayC.filter(e => e.number == no);
                                    cmbArrayC.push(cmbObject);
                                    cc.querySelector("#c_comment_btn").click();
                                    e.target.previousElementSibling.value = "";
                                }
                            }
                        })
                    }
                }
            })
        })
    }


    function pagingAfter(){
        showMoreBtn();
        showMoreBtn2();
        boardMenu();
        boardIcon();
        boardFollow();
        showComment();
        showCmComment();
        commentText();
        friendFollow();
    }
    pagingAfter();
}
    //게시글 이미지 여러개 좌우로 넘기는 이벤트
            //이미지 하나씩 보는 이벤트
     

        function fnRight() {
            console.log('right 들어감');
            console.log($('#flex_image'));
            $("#flex_image").animate({ "margin-left": "-485.98px"}, 300, function () {
                $("#flex_image").css({ "margin-left": "0px" });
                $("#flex_image img:first-child").insertAfter("#flex_image img:last-child");
               
            });

        };

        function fnLeft() {
            console.log('left 들어감');
            console.log($('#flex_image'));
            $("#flex_image").css({ "margin-left": "-485.98px" });
            $("#flex_image img:last-child").insertBefore("#flex_image img:first-child");
            // 옮기기
            $("#flex_image").animate({ "margin-left": "0"}, 300, function () {
                // $("#images").css({ "margin-left": "479.61px" });
               
               
            });
        }