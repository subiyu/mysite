package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String boardNo = request.getParameter("no");
		BoardVo board = new BoardDao().findByNo(Long.parseLong(boardNo));
		new BoardDao().updateHit(board);
		
		request.setAttribute("boardVo", board);		
		request
			.getRequestDispatcher("/board/view.jsp")
			.forward(request, response);
	}

}
