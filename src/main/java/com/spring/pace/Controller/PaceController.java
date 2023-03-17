package com.spring.pace.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.pace.Service.BoardService;
import com.spring.pace.Service.CommentService;
import com.spring.pace.Service.User_infoService;
import com.spring.pace.VO.PaceBoardVO;
import com.spring.pace.VO.PaceUserVO;

@Controller
@RequestMapping(value="/pacebook")
public class PaceController{
	
	private static final Logger logger = LoggerFactory.getLogger(PaceController.class);

	@Autowired
	User_infoService uService;
	
	@Autowired
	CommentService cService;
	
	@Autowired
	BoardService bService;
	
	
	@RequestMapping(value="")
	public String settingPage() {
		
		System.out.println("기본페이지로 로그인 페이지 실행");
		
		return "login";
	}
	
	@RequestMapping(value="/join_succes.do")
	public String joinSucces(
			HttpServletRequest request
			) {
		
		List<PaceUserVO> puvoList = uService.rNum();
		request.setAttribute("puvoList", puvoList);
		List<PaceBoardVO> pbvoList1 = uService.rFriend(puvoList.get(0).getUser_no());
		request.setAttribute("pbvoList1", pbvoList1);
		List<PaceBoardVO> pbvoList2 = uService.rFriend(puvoList.get(1).getUser_no());
		request.setAttribute("pbvoList2", pbvoList2);
		
		return "main";
	}
	
	@RequestMapping(value={"/login.do"})
	public String login(
			HttpServletRequest request, Model model,
			@RequestParam("id") String id,
			@RequestParam("pw") String pw,
			@ModelAttribute PaceUserVO vo
			) {
		
		System.out.println("login 페이지 진입");
		String page = "";
		
		vo.setUser_id(id);
		vo.setUser_pw(pw);
		model.addAttribute("vo", vo);
		
		if((id.equals("") || id == null) || (pw.equals("") || pw==null)) {
			request.setAttribute("logon", "false");
			System.out.println("아이디나 비밀번호가 입력이 안됨");
			page = "login";
			
		} else {
			boolean logon = uService.login(vo);//로그인 가능한지 boolean 리턴값으로 받아옴
			if(logon) {// 로그인 성공했을 경우
				HttpSession se = request.getSession();//세션 생성
				se.setAttribute("user_id", id);// 세션에 값을 넣어줌
				se.setAttribute("user_no", vo.getUser_no());
				se.setAttribute("logon", "true");// 로그인이 되었다는걸 세션어트리뷰트에 넣어줌
				System.out.println("로그인 페이지에서 메인페이지로 이동");
				page = "main";
				
			} else {// 로그인 실패했을 경우
				request.setAttribute("logon", "false");// 로그인이 실패했다는걸 request에 넣어줌
				System.out.println("로그인값과 비밀번호값이 존재하지 않는 값임");
				page = "login";
				
			}
		}
		
		
		
		return page;
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PaceService service = new PaceService();
		String action = request.getPathInfo();
		String nextPage = null;
		System.out.println("action : "+action+action.equals("join_success"));
		
		
		if(action == null || action.equals("") || action.equals("/")) {
			nextPage = "/main.jsp";

		}else if( action.equals("/join_success")){
		
		} else if(action.equals("/login")) {// 기능 : 메인에서 로그인 버튼을 누를시
			
		
		} else if(action.equals("/join")) {//회원가입 페이지에서 회원가입 버튼 누를시
			// 값을 받는 법
			String id = request.getParameter("id");
			System.out.println("user_id:"+id);
			String pw = request.getParameter("pw");
			System.out.println("user_pw:"+pw);
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String gender = request.getParameter("gender");
			String birth = request.getParameter("birth");
			System.out.println("birth:"+birth);
			PaceUserVO vo = new PaceUserVO();// PaceVO객체 생성
			vo.setUser_id(id);//생성한 객체에 변수로 저장했던 아이디,비밀번호 등 값을 넣어줌
			vo.setUser_pw(pw);
			vo.setUser_name(name);
			vo.setUser_email(email);
			vo.setUser_phone(phone);
			vo.setUser_birth(birth);
			vo.setUser_gender(gender);
			boolean result = service.join(vo); // dao의 join메소드로 vo객체를 넘김

			if (result) {// 회원가입이 성공했을 때
				nextPage = "join_success";
			}else {// 회원가입에 실패했을 때
				request.setAttribute("joinUp", "false");//request에 회원가입에 실패했다는 값을 넣어줌
				System.out.println("회원가입 실패");
				nextPage = "/join.jsp";
		    }
			
			
			
			


		}else if(action.equals("/idFind1")){
			
			System.out.println("idFind1로 진입됨");
			String name = request.getParameter("name");
			String birth = request.getParameter("birth");
			String phone = request.getParameter("phone1")+"-"+request.getParameter("phone2")+"-"+request.getParameter("phone3");
			
			PaceUserVO  vo = new PaceUserVO();
			vo.setUser_name(name);
			vo.setUser_birth(birth);
			vo.setUser_phone(phone);
			
			boolean check = service.idCheck(vo);
			
//			String id = str.replace(str.substring(4),"****");
			
			if(check) {
				
				String str = vo.getUser_id();
				StringBuffer sb = new StringBuffer();
				sb.append(str.substring(0,4));
				while(sb.length() < str.length()){
					sb.append("*");
				}
				
				String id = sb.toString();
				System.out.println("idCheck 성공");
				request.setAttribute("name", name);
				request.setAttribute("alert", false);
				request.setAttribute("id", id);
				nextPage = "/idFind2.jsp";
				
			}else {
				System.out.println("idCheck 실패");
				request.setAttribute("alert", true);
				request.setAttribute("text", "등록된 회원정보가 없습니다.");
				nextPage = "/idFind1.jsp";
				
			}
			
			
			
			
		}else if(action.equals("/pwFind1")){
			System.out.println("pwFind1 진입 됨");
			String name = request.getParameter("name");
			String id = request.getParameter("id");
			
			PaceUserVO vo = new PaceUserVO();
			vo.setUser_name(name);
			vo.setUser_id(id);
			
			boolean check = service.pwCheck(vo);
			
			if(check) {
				System.out.println("비밀번호는 true");
				String str = id;
				StringBuffer sb = new StringBuffer();
				sb.append(str.substring(0, 4));
				while(sb.length() < str.length()) {
					sb.append("*");
				}
				id = sb.toString();
				System.out.println("아이디는 "+id);
				request.setAttribute("id", id);
				request.setAttribute("pw", vo.getUser_pw());
				request.setAttribute("alert", false);
				nextPage = "/pwFind2.jsp";
				
				
			}else {
				System.out.println("비밀번호는 false");
				request.setAttribute("alert", true);
				request.setAttribute("text", "일치하는 회원정보가 없습니다.");
				nextPage = "/pwFind1.jsp";
				
			}
			
			
			
			
			
		}else if(action.equals("/makeboard")){
			
			
			nextPage ="/board.jsp" ;
			

		}else if(action.equals("/board")) { //게시글작성 페이지에서 게시글 작성 버튼을 누를시
			String board_content = request.getParameter("content");//게시글 내용 가져오기
			String board_url = request.getParameter("url");//게시글 내용 가져오기
			
			HttpSession se = request.getSession();//세션 생성 및 가져오기
			int user_no = (int) se.getAttribute("user_no");//세션에 유저넘버 값을 넣어줌 
			
			PaceBoardVO pbVO = new PaceBoardVO();// PaceBoardVO객체 생성
			pbVO.setBoard_url(board_url);
			pbVO.setBoard_content(board_content);//게시글 내용 pbVO에 넣기
			
			service.createBoard(user_no, pbVO);// dao의 createBoard메소드에 유저넘버랑 내용을 넘김//DB에 게시글 내용 저장
			
			nextPage = "/main.jsp";

//		} else if("goToBoard".equals(command)) {//메인페이지에서 게시글 만들기 버튼 누를시
//			nextPage = "board.jsp";
//		} else if("logout".equals(command)) {
//			HttpSession se = request.getSession();
//			se.invalidate();
//			response.sendRedirect("main.jsp");
//			return;
			
			
			
			
			
			
			
		} else if(action.equals("/setting")) {
			
			
			nextPage = "/setting.jsp";
			
			
		} else if(action.equals("/del_board")) {// 게시글 삭제버튼
			
			int board_no = Integer.parseInt(request.getParameter("board_no"));
			service.delBoard(board_no);
			nextPage = "main.jsp";
			
		}  else if(action.equals("/bcomment")) {//댓글 달기
	         int board_no = Integer.parseInt(request.getParameter("no"));
	         String comment_content = request.getParameter("content");
	         HttpSession se = request.getSession();
	         int user_no = (int)se.getAttribute("user_no");
	         service.createComment(user_no, board_no, comment_content);
	         nextPage = "main";
	      } else if(action.equals("/ccomment")) {// 답글 달기
	         HttpSession se = request.getSession();
	         int user_no = (int)se.getAttribute("user_no");
	         String content = request.getParameter("content");
	         int comment_no = Integer.parseInt(request.getParameter("no"));
	         service.createCmComment(user_no, comment_no, content);
	         nextPage = "main";


		} else if(action.equals("/del_comment")) {//댓글 삭제 버튼++++++++++
			
			int comment_no = Integer.parseInt(request.getParameter("comment_no"));
			service.delComment(comment_no);
			nextPage = "/main.jsp";
		} else if(action.equals("/search")) {
			String search_content = request.getParameter("search_content");
			List<PaceBoardVO> boardList = service.search(search_content);
			request.setAttribute("boardList", boardList);
			nextPage = "/main.jsp";
		} else if(action.equals("/get_board")) {
			List<PaceBoardVO> boardList = service.getBoard(1);
			request.setAttribute("boardList", boardList);

		}else if(action.equals("/profile")) {
			System.out.println("profile페이지 들어옴");
			HttpSession se = request.getSession();
			int user_no = Integer.parseInt(request.getParameter("user_no")) ;
			System.out.println(user_no);
			PaceUserVO vo = service.getUserInfo(user_no);
			request.setAttribute("vo",vo);
			List<PaceBoardVO> boardList = service.myBoard(user_no);
			System.out.println("board 출력" + boardList);
			request.setAttribute("boardList", boardList);
			List<PaceUserVO> followList = service.getFollowList(user_no);
			request.setAttribute("followList", followList);
			List<PaceUserVO> followerList = service.getFollowerList(user_no);
			request.setAttribute("followerList", followerList);
			int user_no1 = (int)se.getAttribute("user_no");
			PaceUserVO vo1 = service.getUserInfo(user_no1);
			se.setAttribute("vo1", vo1);
			nextPage = "/profile.jsp";
			
		} else if(action.equals("/profilefollow")){
			HttpSession se = request.getSession();
			int user_no = (int)se.getAttribute("user_no");
			int buser_no = Integer.parseInt(request.getParameter("user_no"));
			service.follow(user_no,buser_no);
			nextPage = "profile";
			
		}else if(action.equals("/main")) {
			HttpSession se = request.getSession();
			int user_no = (int)se.getAttribute("user_no");	
			PaceUserVO puvo = service.getUserInfo(user_no);
			se.setAttribute("puvo", puvo);
			List<PaceUserVO> followList = service.getFollowList(user_no);
			request.setAttribute("followList", followList);
			List<PaceBoardVO> boardList = service.getBoard(1);
			request.setAttribute("boardList", boardList);
			List<PaceUserVO> nfuList = service.notFollowUsers(user_no, 1);
			request.setAttribute("nfuList", nfuList);
			nextPage = "/main.jsp";
			
		} else if(action.equals("/follow")) {
			HttpSession se = request.getSession();
			int user_no = (int)se.getAttribute("user_no");
			int buser_no = Integer.parseInt(request.getParameter("user_no"));
			service.follow(user_no, buser_no);
			List<PaceUserVO> followList = new ArrayList<PaceUserVO>();
			followList.add(service.getUserInfo(buser_no));
			request.setAttribute("followList", followList);
			nextPage = "/main.jsp";
		} else if(action.equals("/moreboard")) {
			int pageNum = Integer.parseInt(request.getParameter("pagenum"));
			List<PaceBoardVO> boardList = service.getBoard(pageNum);
			request.setAttribute("boardList", boardList);
			nextPage = "/main.jsp";
		} else if(action.equals("/notfollow")) {
			HttpSession se = request.getSession();
			int user_no = (int)se.getAttribute("user_no");
			int pageNum = Integer.parseInt(request.getParameter("pagenum"));
			List<PaceUserVO> nfuList = service.notFollowUsers(user_no, pageNum);
			request.setAttribute("nfuList", nfuList);
			nextPage = "/main.jsp";
		}
		
		
		///페이지 이동관련
		System.out.println("nextPage : "+nextPage);
		if(nextPage != null) {
			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
		}
//		else if(action.equals("/profile")) {
//			HttpSession se = request.getSession();
//			String user_no = (String)se.getAttribute("user_no");
//			
//		}
		
	}
	}

