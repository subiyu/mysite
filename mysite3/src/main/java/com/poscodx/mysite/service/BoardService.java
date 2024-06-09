package com.poscodx.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;

@Service
public class BoardService {
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public void addContents(BoardVo vo) {
		if(vo.getgNo() != null) {
			boardRepository.updateOrderNo(vo.getgNo(), vo.getoNo());			
		}
		boardRepository.insert(vo);
	}
	
	public BoardVo getContents(Long no) {
		BoardVo vo = boardRepository.findByNo(no);
		if(vo != null) {
			//TODO: session 정보 저장해서 하루 동안은 동일 아이디로 접속했을때 조회수가 오르지 않는 기능 추가
			boardRepository.updateHit(vo);
		}
		
		return vo;
	}
	
	public BoardVo getContents(Long boardNo, Long userNo) {
		BoardVo vo = boardRepository.findByNoAndUserNo(boardNo, userNo);
		return vo;
	}
	
	public int getTotalCount(String keyword) {
		return boardRepository.getTotalCount(keyword);
	}
	
	public int updateContents(BoardVo vo) {
		return boardRepository.update(vo.getNo(), vo.getTitle(), vo.getContents());
	}
	
	public void deleteContents(Long boardNo, Long userNo) {
		boardRepository.deleteByNoAndUserNo(boardNo, userNo);
	}
	
	public Map<String, Object> getContentsList(int currentPage, String keyword) {
		final int contentsPerPage = 5; 		//한 페이지 당 5개의 게시물을 보여줌
		final int pagePerGroup = 5; 		//그룹당 5개의 페이지를 보여줌
		
		int beginPage = pagePerGroup*((currentPage-1)/pagePerGroup) + 1;
		int endPage = beginPage + pagePerGroup - 1;
		int offset = (currentPage - 1) * contentsPerPage; // 페이지 오프셋 계산(no가 1부터 시작)
		int prevPage = beginPage - 1;
		int nextPage = endPage + 1;
		List<BoardVo> list = boardRepository.findAllByPageAndKeyword(contentsPerPage, offset, keyword);
		int totalCount = boardRepository.getTotalCount(keyword);
		int totalPage = (int)Math.ceil(totalCount/contentsPerPage);
		
		if (currentPage > totalPage) {
        	return null;
        }
		
		/* 파라미터 전달 */
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("keyword", keyword);
		map.put("totalCount", totalCount);
		map.put("totalPage", totalPage);
		map.put("currentPage", currentPage);
		map.put("beginPage", beginPage);
		map.put("endPage", endPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("offset", offset);
		
		return map;
	}
}
