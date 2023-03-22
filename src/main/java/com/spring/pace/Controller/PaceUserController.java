package com.spring.pace.Controller;

import java.util.List;
import java.util.Map;

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
		System.out.println("회원가입성공페이지 진입");
//		Map<String, Object> puvoList = uService.rNum();
//		request.setAttribute("puvoList", puvoList);
//		Map<String, Object> pbvoList1 = uService.rFriend(puvoList.get(0).getUser_no());
//		request.setAttribute("pbvoList1", pbvoList1);
//		Map<String, Object> pbvoList2 = uService.rFriend(puvoList.get(1).getUser_no());
//		request.setAttribute("pbvoList2", pbvoList2);
		
		List list = uService.rNum();
		Map map1 = (Map) list.get(0);
		Map map2 = (Map) list.get(1);
		request.setAttribute("map1", map1);
		request.setAttribute("map2", map2);
		
		System.out.println(" map1 + map2 ==="+map1+"   "+map2);

		return "join_success";
	}
	
	@RequestMapping("/login.do")
	public String login(
			HttpServletRequest request, Model model,
			@ModelAttribute PaceUserVO vo
			) {
		String page = "";
		if(("".equals(vo.getUser_id()) || vo.getUser_id() == null) || ("".equals(vo.getUser_pw()) || vo.getUser_pw()==null)) {
			request.setAttribute("logon", "false");
			System.out.println("아이디나 비밀번호가 입력이 안됨");
			page = "login";
		} else {
			Map<String, Object> map = uService.login(vo);//로그인 가능한지 boolean 리턴값으로 받아옴
			if((boolean) map.get("result")) {// 로그인 성공했을 경우
				HttpSession se = request.getSession();//세션 생성
				vo = (PaceUserVO)map.get("puvo");
				System.out.println(vo.getUser_id()+" , "+vo.getUser_no());
				se.setAttribute("user_id", vo.getUser_id());// 세션에 값을 넣어줌
				se.setAttribute("user_no", vo.getUser_no());
				se.setAttribute("logon", "true");// 로그인이 되었다는걸 세션어트리뷰트에 넣어줌
				System.out.println("로그인 페이지에서 메인페이지로 이동");
				page = "forward:/pacebook/main";
			} else {// 로그인 실패했을 경우
				request.setAttribute("logon", "false");// 로그인이 실패했다는걸 request에 넣어줌
				System.out.println("로그인값과 비밀번호값이 존재하지 않는 값임");
				page = "login";
			}
		}
		return page;
	}
	
	@RequestMapping(value="/join_page")
	public String join_page() {
		System.out.println("아이디 찾기 진입");
		return "join";
	}
	
	
	@RequestMapping("/join")
	public String join(
			@ModelAttribute PaceUserVO vo,
			HttpServletRequest request
			) {
		
		System.out.println("join메소드 진입");
		boolean result = uService.join(vo); 
		if (result) {// 회원가입이 성공했을 때
			return "join_success";
		}else {// 회원가입에 실패했을 때
			request.setAttribute("joinUp", "false");//request에 회원가입에 실패했다는 값을 넣어줌
			return "join";
		    }
	}
	
	@RequestMapping(value="/idFind1_page")
	public String idFind1_page() {
		System.out.println("아이디 찾기1 진입");
		return "idFind1";
	}
	
	
	@RequestMapping("/idFind1")
	public String idFind1(
			@ModelAttribute("vo") PaceUserVO vo,
			@RequestParam("phone1") String phone1,
			@RequestParam("phone2") String phone2,
			@RequestParam("phone3") String phone3,
			
			HttpServletRequest request
			) {
	
		System.out.println("아이디찾기 진입");
		String phone = phone1 + "-" + phone2 + "-" + phone3;
		vo.setUser_phone(phone);
		
		System.out.println("vo.getUser_phone + "+vo.getUser_phone());
		System.out.println("vo.getUser_name + "+vo.getUser_name());
		System.out.println("vo.getUser_birth + "+vo.getUser_birth());
		
		Map<String, Object> map = uService.idCheck(vo);
//		String id = str.replace(str.substring(4),"****");
		boolean result = (boolean)map.get("result");
		PaceUserVO puvo = (PaceUserVO)map.get("vo");
		
		if(result) {
			String str = puvo.getUser_id();
			StringBuffer sb = new StringBuffer();
			sb.append(str.substring(0,4));
			while(sb.length() < str.length()){
				sb.append("*");
			}
			String id = sb.toString();
			System.out.println("idCheck 성공");
			request.setAttribute("name", vo.getUser_name());
			request.setAttribute("alert", false);
			request.setAttribute("id", id);
			System.out.println("user_id + " +puvo.getUser_id());
			return "idFind2";
		}else {
			System.out.println("idCheck 실패");
			request.setAttribute("alert", true);
			request.setAttribute("text", "등록된 회원정보가 없습니다.");
			return "idFind1";
		}
	}
	
	@RequestMapping(value="/pwFind1_page")
	public String pwFind1_page() {
		System.out.println("비밀번호 찾기 페이지 진입");
		return "pwFind1";
	}
	
	@RequestMapping("/pwFind1")
	public String pwFind1(
			@ModelAttribute PaceUserVO vo,
			HttpServletRequest request
			) {
		
		System.out.println("비밀번호 찾기 진입111");
		System.out.println("vo.getUser_id + "+vo.getUser_id());
		System.out.println("vo.getUser_name + "+vo.getUser_name());
		
		
		Map<String, Object> map = uService.pwCheck(vo);
		boolean result = (boolean)map.get("result");
		String temPW = (String)map.get("temPW");
//		PaceUserVO puvo = (PaceUserVO)map.get("vo");
		
		if(result) {
			System.out.println("비밀번호는 true");
			String str = vo.getUser_id();
			StringBuffer sb = new StringBuffer();
			sb.append(str.substring(0, 4));
			while(sb.length() < str.length()) {
				sb.append("*");
			}
			
			String id = sb.toString();
			System.out.println("아이디는 "+id);
			request.setAttribute("id", id);
			
			System.out.println("temPW + "+ temPW );
			request.setAttribute("pw", temPW);
			
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
