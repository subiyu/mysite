package com.poscodx.mysite.controller.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.GuestBookDao;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String no = request.getParameter("no");
		System.out.println("넘버" + no);
		String password = request.getParameter("password");
		
		new GuestBookDao().deleteByNo(Long.parseLong(no), password);
		
		response.sendRedirect(request.getContextPath() + "/guestbook");
	}

}
