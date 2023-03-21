package com.spring.pace.Controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.pace.Algorithm.Time;
import com.spring.pace.Service.BoardService;
import com.spring.pace.Service.CommentService;
import com.spring.pace.Service.User_infoService;
import com.spring.pace.VO.PaceBoardVO;
import com.spring.pace.VO.PaceUBVO;
import com.spring.pace.VO.PaceUCVO;
import com.spring.pace.VO.PaceUCmCVO;
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
	public String loginPage() {
		System.out.println("기본페이지로 로그인 페이지 실행");
		return "login";
	}
	
	@RequestMapping("/board_page")
	public String board_page(
			HttpServletRequest request
			) {
		System.out.println("게시판 작성 페이지 실행");
		HttpSession se = request.getSession();
		int user_no = (int) se.getAttribute("user_no");//세션에 유저넘버 값을 넣어줌 
		PaceUserVO vo1 = uService.getUserInfo(user_no);
		int followList_no = bService.followList_no(user_no);
		int followerList_no = bService.followerList_no(user_no);
		List<PaceBoardVO> myBoardList = bService.myBoardList(user_no);
		List<PaceUserVO> myFollowList = bService.myFollowList(user_no);
		
		request.setAttribute("vo1", vo1);
		request.setAttribute("followList_no", followList_no);
		request.setAttribute("followerList_no", followerList_no);
		request.setAttribute("myBoardList", myBoardList);
		request.setAttribute("myFollowList", myFollowList);
		return "board";
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
	
	@RequestMapping(value="/bcomment", method= {RequestMethod.POST})
	public void bComment(
			@RequestParam("no") int board_no,
			@RequestBody String comment_content,
			HttpServletRequest request
			) {
		System.out.println();
        HttpSession se = request.getSession();
        int user_no = (int)se.getAttribute("user_no");
        cService.createComment(user_no, board_no, comment_content);
	}
	
	@RequestMapping("/ccomment")
	public String cComment(
			HttpServletRequest request,
			@RequestBody String content,
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
		List<PaceUBVO> boardList = bService.getBoard(1);
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
		PaceUserVO puvo = uService.getUserInfo(user_no);
		se.setAttribute("puvo", puvo);
		List<PaceUserVO> followList = uService.getFollowList(user_no);
		request.setAttribute("followList", followList);
		List<PaceUBVO> UBList = bService.getBoard(1);
		request.setAttribute("UBList", UBList);
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
	
	@RequestMapping(value="/moreboard", method= {RequestMethod.POST})
	@ResponseBody
	public List<PaceUBVO> moreboard(
			@RequestParam("pagenum") int pageNum
			) {
		List<PaceUBVO> boardList = bService.getBoard(pageNum);
		for(int i=0; i<boardList.size(); i++) {
			Date boardTime = boardList.get(i).getPaceBoardVO().getBoard_time();
			String bt = Time.calculateTime(boardTime);
			boardList.get(i).getPaceBoardVO().setBoard_time_s(bt);
		}
		return boardList;
	}
	
	@RequestMapping(value="/notfollow", method= {RequestMethod.POST})
	@ResponseBody
	public List<PaceUserVO> notfollow(
			HttpServletRequest request,
			@RequestParam("pagenum") int pageNum
			) {
		HttpSession se = request.getSession();
		int user_no = (int)se.getAttribute("user_no");
		List<PaceUserVO> nfuList = uService.notFollowUsers(user_no, pageNum);
		return nfuList;
	}
	
	@RequestMapping(value="/showcomment", method= {RequestMethod.POST})
	@ResponseBody
	public List<PaceUCVO> showComment(
			@RequestParam("board_no") int board_no,
			HttpServletRequest request
			) {
		List<PaceUCVO> UClist = cService.showComment(board_no);
		for(int i=0; i<UClist.size(); i++) {
			Date commentTime = UClist.get(i).getPaceCommentVO().getComment_time();
			String ct = Time.calculateTime(commentTime);
			UClist.get(i).getPaceCommentVO().setComment_time_s(ct);
		}
		return UClist;
	}
	
	@RequestMapping(value="/showcmcomment", method= {RequestMethod.POST})
	@ResponseBody
	public List<PaceUCmCVO> showCmComment(
			@RequestParam("comment_no") int comment_no,
			HttpServletRequest request
			) {
		List<PaceUCmCVO> UCmClist = cService.showCmComment(comment_no);
		for(int i=0; i<UCmClist.size(); i++) {
			Date commentTime = UCmClist.get(i).getPaceCmCommentVO().getCmComment_time();
			String cmct = Time.calculateTime(commentTime);
			UCmClist.get(i).getPaceCmCommentVO().setCmComment_time_s(cmct);
		}
		return UCmClist;
	}
	
	@RequestMapping(value="/setting_page")
	public String settingPage() {
		System.out.println("설정 페이지 실행");
		return "setting";
	}
}