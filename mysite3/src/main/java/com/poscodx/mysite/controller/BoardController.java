package com.poscodx.mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@RequestMapping("")
	public String index(Integer page, String keyword, Model model) {
		if(page == null) {
			page = 1;
		} 
		if(keyword == null) {
			keyword = "";
		}
		if(page < 0) {
			return "redirect:/board";
		}
		
		Map map = boardService.getContentsList(page, keyword);
		if(map == null) {
			return "redirect:/board?page=" + page;
		}
		
		model.addAllAttributes(map);
		return "board/list";
	}
	
	/* @RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no) {
		
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(HttpSession session, PathVariable("no") Long no) {
		//session: no를 끄집어내야하니까.
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////
	} */
}
