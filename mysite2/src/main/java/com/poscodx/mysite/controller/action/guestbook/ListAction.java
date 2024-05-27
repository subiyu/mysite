package com.poscodx.mysite.controller.action.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.mysite.vo.GuestBookVo;

public class ListAction implements Action {
	List<GuestBookVo> list = new GuestBookDao().findAll();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		request.setAttribute("list", list);
		request
			.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp")
			.forward(request, response);
	}
}
