package com.spring.pace.Controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.pace.Service.BoardService;
import com.spring.pace.Service.CommentService;
import com.spring.pace.Service.User_infoService;
import com.spring.pace.VO.PaceBoardVO;
import com.spring.pace.VO.PaceUserVO;

@Controller
@RequestMapping(value="/pacebook/user")
public class PaceUserController {
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
	
	@RequestMapping("/join")
	public String join(
			@ModelAttribute PaceUserVO vo,
			HttpServletRequest request
			) {
		boolean result = uService.join(vo); 
		if (result) {// 회원가입이 성공했을 때
			return "join_success";
		}else {// 회원가입에 실패했을 때
			request.setAttribute("joinUp", "false");//request에 회원가입에 실패했다는 값을 넣어줌
			return "join";
		    }
	}
	
	@RequestMapping("/idFind1")
	public String idFind1(
			@ModelAttribute PaceUserVO vo,
			@RequestParam("name") String name,
			HttpServletRequest request
			) {
		boolean check = uService.idCheck(vo);
//		String id = str.replace(str.substring(4),"****");
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
			return "idFind2";
		}else {
			System.out.println("idCheck 실패");
			request.setAttribute("alert", true);
			request.setAttribute("text", "등록된 회원정보가 없습니다.");
			return "idFind1";
		}
	}
	
	@RequestMapping("/pwFind1")
	public String pwFind1(
			@ModelAttribute PaceUserVO vo,
			@RequestParam("id") String id,
			HttpServletRequest request
			) {
		boolean check = uService.pwCheck(vo);
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
			return "pwFind2";
		}else {
			System.out.println("비밀번호는 false");
			request.setAttribute("alert", true);
			request.setAttribute("text", "일치하는 회원정보가 없습니다.");
			return "pwFind1";
		}
	}
}
