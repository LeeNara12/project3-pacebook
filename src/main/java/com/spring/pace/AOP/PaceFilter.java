package com.spring.pace.AOP;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class PaceFilter extends HttpFilter implements Filter {
       
    public PaceFilter() {
        super();
    }

	public void destroy() {
	}

	/*
	 * indexOf()
	 * - 특정 문자나 문자열이 앞에서부터 처음 발견되는 인덱스를 반환하며만약 찾지 못했을 경우 "-1"을 반환합니다.
	 * - instanceof는 객체 타입을 확인하는 연산자이다.
	 * - 형변환 가능 여부를 확인하며 true / false로 결과를 반환한다.
	 * - 주로 상속 관계에서 부모객체인지 자식 객체인지 확인하는 데 사용된다.
	 * 
	 * 
	 * */
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
//		response.setContentType("text/html;charset=utf-8");//css나 image가 적용되는 문제 발생

		
		HttpSession session = null;
		if(request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest)request;
			
			
			StringBuffer sb  = req.getRequestURL();
			String url = sb.toString();
			System.out.println("현재위치는 "+url);
			

			if(
						url.indexOf("/css") != -1
						|| url.indexOf("/image") != -1
						|| url.indexOf("/script") != -1
						|| url.indexOf("/favicon.*") != -1
				) 	{

				System.out.println(1);
				chain.doFilter(request, response);//디스패처서블릿으로

			}else {
				
				response.setContentType("text/html;charset=utf-8");
				
				if(
						url.indexOf("/pacebook") != -1
						|| url.indexOf("/pacebook/user/idFind1_page") != -1
						|| url.indexOf("/pacebook/user/pwFind1_page") != -1
					    || url.indexOf("/pacebook/user/join_page") != -1
				){
					System.out.println("그냥 통과");
					chain.doFilter(request, response);
				} else { //위에 선언한 url주소가 아닌 곳에 갈때 세션 확인
					session = req.getSession(); 
					String logon = (String) session.getAttribute("logon"); //세션에 로그인한 기록이 있으면 진행 없으면 로그인페이지로 리턴
					System.out.println("filter => logon : "+ logon);
					
					if("true".equals(logon) ) {
						System.out.println("(필터) 로그인 성공!! + "+logon);
						chain.doFilter(request, response);//디스패처서블릿으로
						
					} else {
						System.out.println("(핉터) 로그인 실패 다시 로그인!! + "+logon);
						((HttpServletResponse)response).sendRedirect("/pacebook");//로그아웃기능 만들어지면 확인하기
					}
					
					
				}
			}
					
			}
		}		
		
}
