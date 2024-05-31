package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.UserVo;

public class DeleteAction implements Action {
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
		
		Long boardNo = Long.parseLong(request.getParameter("no"));
		Long writerNo = Long.parseLong(request.getParameter("writer"));
		
		if(userNo == writerNo) {
			new BoardDao().deleteByNo(boardNo);
		}
		//TODO: else 도 구현하기
		
		response.sendRedirect(request.getContextPath() + "/board");
	}

}
