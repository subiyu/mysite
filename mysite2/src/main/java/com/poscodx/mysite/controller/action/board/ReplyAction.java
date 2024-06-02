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

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
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
		Long gNo = Long.parseLong(request.getParameter("gNo"));
		Long oNo = Long.parseLong(request.getParameter("oNo"));
		Integer depth = Integer.parseInt(request.getParameter("depth"));
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		oNo += 1; 	// 부모글 order_no + 1
		depth += 1;
		new BoardDao().updateBeforeInsert(gNo, oNo);
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setgNo(gNo);
		vo.setoNo(oNo);
		vo.setDepth(depth);
		vo.setUserNo(userNo);
		
		
		new BoardDao().insertReply(vo);
		response.sendRedirect(request.getContextPath() + "/board");
	}

}
