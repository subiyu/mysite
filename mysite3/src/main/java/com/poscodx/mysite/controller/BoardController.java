package com.poscodx.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
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
		if(page == null || page <= 0) {
			page = 1;
		} 
		if(keyword == null) {
			keyword = "";
		}
		if(page < 0) {
			return "redirect:/board";
		}
		
		//TODO: 수정하기
		int totalCount = boardService.getTotalCount(keyword);
		final int contentsPerPage = 5;
		int totalPage = (int)Math.ceil(totalCount/contentsPerPage);
		
		if(page > totalPage) {
			return "redirect:/board?page=" + totalPage;
		}
		
		Map map = boardService.getContentsList(page, keyword);
		if(map == null) {
			return "redirect:/board";
		}
		
		model.addAllAttributes(map);
		return "board/list";
	}
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.getContents(no);
		model.addAttribute("boardVo", vo);
		return "board/view";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(HttpSession session, @PathVariable("no") Long no) {
		// access control 
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////
		
		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board";
	}
	
	@RequestMapping("/modify/{no}")	
	public String modify(HttpSession session, @PathVariable("no") Long no, Model model) {
		// access control 
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////
		
		BoardVo vo = boardService.getContents(no, authUser.getNo());
		if(vo == null) {
			return "redirect:/board/" + no;
		}
		
		model.addAttribute(vo);
		
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)	
	public String modify(HttpSession session, BoardVo vo) {
		// access control 
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////
				
		boardService.updateContents(vo);
		
		return "redirect:/board/view/"+ vo.getNo();
	}
	
	@RequestMapping(value="/write")	
	public String write(HttpSession session, boolean isNew, Model model) {
		// access control 
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////
		
		model.addAttribute("isNew", isNew);
		
		return "board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)	
	public String write(HttpSession session, BoardVo vo) {
		// access control 
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////
				
		vo.setUserNo(authUser.getNo());
		
		boardService.addContents(vo);
		
		return "redirect:/board/view/" + vo.getNo();
	}
	
	@RequestMapping(value="/reply", method=RequestMethod.POST)	
	public String reply(HttpSession session, BoardVo vo) {
		// access control 
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////
		
		BoardVo parentVo = boardService.getContents(vo.getNo());
		vo.setgNo(parentVo.getgNo());
		vo.setoNo(parentVo.getoNo()+1); 		// 부모글 order_no + 1
		vo.setDepth(parentVo.getDepth()+1);
		vo.setUserNo(authUser.getNo());

		boardService.addContents(vo);
		
		return "redirect:/board/view/" + vo.getNo();
	}
}
