package com.poscodx.mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@RequestMapping("")
	public String index(Model model) {
		Map map = boardService.getContentsList();
		model.addAllAttributes(map);

		return "board/index";
	}
	
	@RequestMapping("/view/{no}")
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
	}
}
