package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GuestBookRepository;
import com.poscodx.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	@Autowired
	private GuestBookRepository guestBookRepository;
	
	public List<GuestBookVo> getContentsList() {
		return guestBookRepository.findAll();
	}
	
	public void deleteContents(Long no, String password) {
		guestBookRepository.deleteByNo(no, password);
	}
	
	public void addContents(GuestBookVo vo) {
		guestBookRepository.insert(vo);
	}
}