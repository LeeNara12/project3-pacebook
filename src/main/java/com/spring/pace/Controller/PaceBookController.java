package com.spring.pace.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.pace.Service.BoardService;
import com.spring.pace.Service.CommentService;
import com.spring.pace.Service.User_infoService;
import com.spring.pace.VO.PaceBoardVO;
import com.spring.pace.VO.PaceUserVO;

@Controller
@RequestMapping(value="/pacebook")
public class PaceBookController{
	
	private static final Logger logger = LoggerFactory.getLogger(PaceBookController.class);

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
	
	@RequestMapping("/board")
	public String board(
			@ModelAttribute PaceBoardVO pbvo,
			HttpServletRequest request
			) {
		HttpSession se = request.getSession();//세션 생성 및 가져오기
		int user_no = (int) se.getAttribute("user_no");//세션에 유저넘버 값을 넣어줌 
		bService.createBoard(user_no, pbvo);// dao의 createBoard메소드에 유저넘버랑 내용을 넘김//DB에 게시글 내용 저장
		return "main";
	}
	
	@RequestMapping("/del_board")
	public String delBoard(
			@RequestParam("board_no") int board_no
			) {
		bService.delBoard(board_no);
		return "main";
	}
	
	@RequestMapping("/bcomment")
	public String bComment(
			@RequestParam("no") int board_no,
			@RequestParam("content") String comment_content,
			HttpServletRequest request
			) {
        HttpSession se = request.getSession();
        int user_no = (int)se.getAttribute("user_no");
        cService.createComment(user_no, board_no, comment_content);
        return "/main";
	}
	
	@RequestMapping("/ccomment")
	public String cComment(
			HttpServletRequest request,
			@RequestParam("content") String content,
			@RequestParam("no") int comment_no
			) {
		HttpSession se = request.getSession();
	    int user_no = (int)se.getAttribute("user_no");
	    cService.createCmComment(user_no, comment_no, content);
	    return "/main";
	}
	
	@RequestMapping("/del_comment")
	public String delComment(
			@RequestParam("comment_no") int comment_no
			) {
		cService.delComment(comment_no);
		return "main";
	}
	
	@RequestMapping("/search")
	public String search(
			HttpServletRequest request,
			@RequestParam("search_content") String search_content
			) {
		List<PaceBoardVO> boardList = bService.search(search_content);
		request.setAttribute("boardList", boardList);
		return "main";
	}
	
	@RequestMapping(value="/get_board", method= {RequestMethod.POST})
	public String getBoard(
			HttpServletRequest request
			) {
		List<PaceBoardVO> boardList = bService.getBoard(1);
		request.setAttribute("boardList", boardList);
		return "main";
	}
	
	@RequestMapping("/profile")
	public String profile(
			HttpServletRequest request,
			@RequestParam("user_no") int user_no
			) {
		HttpSession se = request.getSession();
		PaceUserVO vo = uService.getUserInfo(user_no);
		request.setAttribute("vo",vo);
		List<PaceBoardVO> boardList = bService.myBoard(user_no);
		request.setAttribute("boardList", boardList);
		List<PaceUserVO> followList = uService.getFollowList(user_no);
		request.setAttribute("followList", followList);
		List<PaceUserVO> followerList = uService.getFollowerList(user_no);
		request.setAttribute("followerList", followerList);
		int user_no1 = (int)se.getAttribute("user_no");
		PaceUserVO vo1 = uService.getUserInfo(user_no1);
		se.setAttribute("vo1", vo1);
		return "profile";
	}
	
	@RequestMapping("/profilefollow")
	public String profilefollow(
			HttpServletRequest request,
			@RequestParam("user_no") int buser_no
			) {
		HttpSession se = request.getSession();
		int user_no = (int)se.getAttribute("user_no");
		uService.follow(user_no,buser_no);
		return "/profile";
	}
	
	@RequestMapping("/main")
	public String main(
			HttpServletRequest request
			) {
		HttpSession se = request.getSession();
		int user_no = (int)se.getAttribute("user_no");	
		System.out.println("user_no : "+user_no);
		PaceUserVO puvo = uService.getUserInfo(user_no);
		se.setAttribute("puvo", puvo);
		List<PaceUserVO> followList = uService.getFollowList(user_no);
		request.setAttribute("followList", followList);
		List<PaceBoardVO> boardList = bService.getBoard(1);
		request.setAttribute("boardList", boardList);
		List<PaceUserVO> nfuList = uService.notFollowUsers(user_no, 1);
		request.setAttribute("nfuList", nfuList);
		return "main";
	}
	
	@RequestMapping("/follow")
	public String follow(
			HttpServletRequest request,
			@RequestParam("user_no") int buser_no
			) {
		HttpSession se = request.getSession();
		int user_no = (int)se.getAttribute("user_no");
		uService.follow(user_no, buser_no);
		List<PaceUserVO> followList = new ArrayList<PaceUserVO>();
		followList.add(uService.getUserInfo(buser_no));
		request.setAttribute("followList", followList);
		return "main";
	}
	
	@RequestMapping("/moreboard")
	public String moreboard(
			HttpServletRequest request,
			@RequestParam("pagenum") int pageNum
			) {
		List<PaceBoardVO> boardList = bService.getBoard(pageNum);
		request.setAttribute("boardList", boardList);
		return "main";
	}
	
	@RequestMapping("/notfollow")
	public String notfollow(
			HttpServletRequest request,
			@RequestParam("pagenum") int pageNum
			) {
		HttpSession se = request.getSession();
		int user_no = (int)se.getAttribute("user_no");
		List<PaceUserVO> nfuList = uService.notFollowUsers(user_no, pageNum);
		request.setAttribute("nfuList", nfuList);
		return "main";
	}
}