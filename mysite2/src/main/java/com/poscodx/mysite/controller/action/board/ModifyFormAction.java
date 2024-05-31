package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// Access Control
		if(session == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		Long userNo = authUser.getNo();
		Long writerNo = Long.parseLong(request.getParameter("writer"));
		
		if(userNo == writerNo) {
			String no = request.getParameter("no");
			BoardVo board = new BoardDao().findByNo(Long.parseLong(no));
			
			request.setAttribute("boardVo", board);
			request
				.getRequestDispatcher("/board/modify.jsp")
				.forward(request, response);
		} else {
			//TODO: 알림창 띄우기
			response.sendRedirect(request.getContextPath());
			return;
		}
	}

}
