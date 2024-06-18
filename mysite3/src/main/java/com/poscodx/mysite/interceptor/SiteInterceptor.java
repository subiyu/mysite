package com.poscodx.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {
	private LocaleResolver localeResolver;
	private SiteService siteService;
	
	public SiteInterceptor(LocaleResolver localeResover, SiteService siteService) {
		this.localeResolver = localeResover;
		this.siteService = siteService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SiteVo siteVo = (SiteVo)request.getServletContext().getAttribute("siteVo");
		if(siteVo == null) {
			siteVo = siteService.getSite(); 	//DB에서 site 정보를 가져옴
			request.getServletContext().setAttribute("siteVo", siteVo);
		}
		
		// Locale
		System.out.println("resolver-locale:" + localeResolver.resolveLocale(request).getLanguage());
		request.setAttribute("language", localeResolver.resolveLocale(request).getLanguage());
		
		return true;
	}
}