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
		int pageNo = 1; 									// page 파라미터 값이 없으면 1페이지를 보여줌
        String pageParam = request.getParameter("page"); 	// page 파라미터가 있는 경우 해당 페이지 번호를 가져옴
        if (pageParam != null && !pageParam.isEmpty()) {
            pageNo = Integer.parseInt(pageParam);
        }

        List<BoardVo> fullList = new BoardDao().findAll();
		List<BoardVo> list = new BoardDao().findBoardList(pageNo, boardsPerPage); //query의 offset의 시작 인덱스는 0이므로 1페이지면 currentPage=0이어야한다.
		int numBoard = fullList.size();
		
		request.setAttribute("list", list);
		request.setAttribute("size", numBoard);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("bPP", boardsPerPage);
		request
			.getRequestDispatcher("/board/list.jsp")
			.forward(request, response);
	}
}
