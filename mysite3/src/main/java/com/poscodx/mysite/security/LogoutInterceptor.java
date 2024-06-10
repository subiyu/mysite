package com.poscodx.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LogoutInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(session != null) {
			session.removeAttribute("authUser"); 	// 로그아웃 처리
			session.invalidate();	//사용자의 세션을 종료하고, 서버에 저장된 세션 데이터를 삭제
		}
		
		response.sendRedirect(request.getContextPath());
		return false;
	}
	
}
