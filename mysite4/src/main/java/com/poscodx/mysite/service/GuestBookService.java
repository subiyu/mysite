package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.mysite.repository.GuestBookLogRepository;
import com.poscodx.mysite.repository.GuestBookRepository;
import com.poscodx.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	@Autowired
	private GuestBookRepository guestBookRepository;
	
	@Autowired
	private GuestBookLogRepository guestBookLogRepository;
	
	public List<GuestBookVo> getContentsList() {
		return guestBookRepository.findAll();
	}
	
	@Transactional
	public void deleteContents(Long no, String password) {
		//guestBookRepository.deleteByNoAndPassword(no, password);
		guestBookLogRepository.update(no);
	
		int count = guestBookRepository.deleteByNoAndPassword(no, password);
		if(count == 1) {
			//TODO: 버그 수정
		}
	}
	
	@Transactional
	public void addContents(GuestBookVo vo) {
		//guestBookRepository.insert(vo);
		int count = guestBookLogRepository.update();
		if(count == 0) {
			guestBookLogRepository.insert();
		}
		guestBookRepository.insert(vo);
	}
}