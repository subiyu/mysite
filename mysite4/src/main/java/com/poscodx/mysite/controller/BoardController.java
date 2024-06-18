package com.poscodx.mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
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
	public String index(
			@RequestParam(value="p", required=true, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=true, defaultValue="") String keyword, 
			Model model) {
	
		if(page < 0) {
			return "redirect:/board";
		}
		
		int totalCount = boardService.getTotalCount(keyword);
		int totalPage = (int)Math.ceil(totalCount/(double)boardService.contentsPerPage);
		
		System.out.println("totalCount = " +totalCount);
		
		if(page > (int)totalPage) {
			return "redirect:/board?p=" + totalPage;
		}
		
		Map map = boardService.getContentsList(page, keyword);
		if(map == null) {
			return "redirect:/board";
		}
		
		model.addAllAttributes(map);
		model.addAttribute(keyword);
		
		return "board/list";
	}
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.getContents(no);
		model.addAttribute("boardVo", vo);
		return "board/view";
	}
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(@AuthUser UserVo authUser, @PathVariable("no") Long no) {
		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping("/modify/{no}")	
	public String modify(@AuthUser UserVo authUser, @PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.getContents(no, authUser.getNo());
		if(vo == null) {
			return "redirect:/board/" + no;
		}
		
		model.addAttribute(vo);
		
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.POST)	
	public String modify(@AuthUser UserVo authUser, BoardVo vo) {
		boardService.updateContents(vo);
		
		return "redirect:/board/view/"+ vo.getNo();
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "/board/write";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)	
	public String write(@AuthUser UserVo authUser, BoardVo vo) {
		vo.setUserNo(authUser.getNo());
		
		boardService.addContents(vo);
		
		return "redirect:/board/view/" + vo.getNo();
	}
	
	@Auth
	@RequestMapping(value="/reply", method=RequestMethod.POST)	
	public String reply(@AuthUser UserVo authUser, BoardVo vo) {
		BoardVo parentVo = boardService.getContents(vo.getNo());
		vo.setgNo(parentVo.getgNo());
		vo.setoNo(parentVo.getoNo()+1); 		// 부모글 order_no + 1
		vo.setDepth(parentVo.getDepth()+1);
		vo.setUserNo(authUser.getNo());

		boardService.addContents(vo);
		
		return "redirect:/board/view/" + vo.getNo();
	}
}
