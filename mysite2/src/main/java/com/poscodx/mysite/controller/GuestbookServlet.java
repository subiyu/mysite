package com.poscodx.mysite.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.controller.action.guestbook.DeleteAction;
import com.poscodx.mysite.controller.action.guestbook.DeleteFormAction;
import com.poscodx.mysite.controller.action.guestbook.InsertAction;
import com.poscodx.mysite.controller.action.guestbook.ListAction;
import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.mysite.vo.GuestBookVo;

public class GuestbookServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Action> mapAction = Map.of(
			"deleteform", new DeleteFormAction(),
			"insert", new InsertAction(),
			"delete", new DeleteAction(),
			"list", new ListAction()
			);

	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}

//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");
//		
//		String action = request.getParameter("a");
//		if("deleteform".equals(action)) {
//			request.setCharacterEncoding("utf-8");
//			
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
//			rd.forward(request, response);
//		} else if("insert".equals(action)) {
//			request.setCharacterEncoding("utf-8");
//
//			String name = request.getParameter("name");
//			String password = request.getParameter("password");
//			String contents = request.getParameter("contents");
//			
//			GuestBookVo vo = new GuestBookVo();
//			vo.setName(name);
//			vo.setPassword(password);
//			vo.setContents(contents);
//			
//			new GuestBookDao().insert(vo);
//			response.sendRedirect(request.getContextPath() + "/guestbook");
//		} else if("delete".equals(action)) {
//			request.setCharacterEncoding("utf-8");
//
//			String no = request.getParameter("no");
//			String password = request.getParameter("password");
//			
//			new GuestBookDao().deleteByNo(Long.parseLong(no), password);
//			
//			response.sendRedirect(request.getContextPath() + "/guestbook");
//		} else {
//			/* default 4action (list) */
//			List<GuestBookVo> list = new GuestBookDao().findAll();
//			request.setAttribute("list", list);
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp");
//			rd.forward(request, response);
//		}
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
//	}

}
