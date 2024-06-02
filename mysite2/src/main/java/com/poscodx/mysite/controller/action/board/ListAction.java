package com.poscodx.mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class ListAction implements Action {
	final int boardsPerPage = 5;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageOrderNo = 1; 								// page 파라미터 값이 없으면 페이지 그룹 내의 1번 페이지를 보여줌
        String pageParam = request.getParameter("page"); 	// page 파라미터가 있는 경우 해당 페이지 번호를 가져옴
        if (pageParam != null && !pageParam.isEmpty()) {
            pageOrderNo = Integer.parseInt(pageParam);
        }
        
        int pageGroup = 0;									// pageGroup 파라미터가 없는 경우 pageGroup 1(1~5page 네비게이션)를 보여줌
        String pageGroupParam = request.getParameter("pageGroup"); 	// pageGroup 파라미터가 있는 경우 해당 페이지 그룹 번호를 가져옴
        if (pageGroupParam != null && !pageGroupParam.isEmpty()) {
        	pageGroup = Integer.parseInt(pageGroupParam);
        }
        if (pageGroup < 0) {
        	response.sendRedirect(request.getContextPath() + "/board");
        	return;
        }
        
        List<BoardVo> fullList = new BoardDao().findAll();
		List<BoardVo> list = new BoardDao().findBoardList(pageOrderNo, boardsPerPage);
		int numBoard = fullList.size();
		
		int pageNo = pageGroup * boardsPerPage + 1;
		double totalPage = Math.ceil(numBoard/boardsPerPage);
		if (pageNo > totalPage) {
        	response.sendRedirect(request.getContextPath() + "/board?page=" + boardsPerPage + "&pageGroup=" + (pageGroup-1));
        	return;
        }
		
		request.setAttribute("list", list);
		request.setAttribute("size", numBoard);
		request.setAttribute("pageNo", pageOrderNo);
		request.setAttribute("pageGroup", pageGroup);
		request.setAttribute("bPP", boardsPerPage);
		request
			.getRequestDispatcher("/board/list.jsp")
			.forward(request, response);
	}
}
