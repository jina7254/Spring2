package com.myspring.pro28.ex05;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LocaleInterceptor extends HandlerInterceptorAdapter{

	@Override	//컨트롤러 실행 전 호출
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		
		HttpSession session = request.getSession();
		String locale = request.getParameter("locale");	//브라우저에서 전달한 locale정보 가져오기
		if(locale == null) {
			locale = "ko";	//최초 요청 시 locale을 한국어로 설정
		}
		session.setAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE", new Locale(locale));
		
		return true;
	}
	
	@Override	//컨트롤러 실행 후 호출
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
		
	}
	
	@Override	//뷰(JSP)를 수행한 후 호출
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
		
	}
	
}
