package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.mysite.service.GuestBookService;
import com.poscodx.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	private GuestBookService guestBookService;
	
	public GuestBookController(GuestBookService guestBookService) {
		this.guestBookService = guestBookService;
	}

	@RequestMapping("")
	public String list(Model model) {
		List<GuestBookVo> list = guestBookService.getContentsList();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(GuestBookVo vo) {
		guestBookService.addContents(vo);
		return "redirect:/guestbook/";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete() {
		return "guestbook/deleteform";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(GuestBookVo vo, Model model) {
		guestBookService.deleteContents(vo.getNo(), vo.getPassword());
		return "redirect:/guestbook/";
	}
}
