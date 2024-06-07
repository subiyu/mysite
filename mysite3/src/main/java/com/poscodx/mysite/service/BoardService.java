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

	/* public BoardVo addContents(BoardVo vo) {
		//스프링 레포 만들땐 분리를 해라.
		//boardRepository.updateOrderNo(...);
		//boardRepository.insert(vo);
	}
	
	public BoardVo getContents(Long no) {
//			BoardVo = baordVoRepository.findByNo(no);
//			if(vo != null) {
//				boardRepository.updateHit();
//			}
//			
//			return vo;
	}
	
	public BoardVo getContents(Long boardNo, Long userNo) {
	
	}
	
	public void updateContents(BoardVo vo) {
		
	}
	
	public void deleteContents(Long boardNo, Long userNo) {
 
	} */
	
	public Map<String, Object> getContentsList(int currentPage, String keyword) {
		final int contentsPerPage = 5; 		//한 페이지 당 5개의 게시물을 보여줌
		final int pagePerGroup = 5; 		//그룹당 5개의 페이지를 보여줌
		
		int beginPage = pagePerGroup*((currentPage-1)/pagePerGroup) + 1;
		int endPage = beginPage + pagePerGroup - 1;
		int offset = (currentPage - 1) * contentsPerPage; // 페이지 오프셋 계산(no가 1부터 시작)
        int prevPage = beginPage - 1; //왜 필요하지?
        int nextPage = endPage + 1;
		
		List<BoardVo> list = boardRepository.findAllByPageAndKeyword(contentsPerPage, offset, keyword);
		int listSize = list.size();
		double totalPage = Math.ceil(listSize/contentsPerPage);
		if (currentPage > totalPage) {
        	return null;
        }
		
		/* 파라미터 전달 */
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("keyword", keyword);
		//map.put("pager", pager); 이건 페이저 객체
		//map.put("totalCount", 0);
		map.put("listSize", listSize);
		map.put("currentPage", currentPage);
		map.put("beginPage", beginPage);
		map.put("endPage", endPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("offset", offset);
		
		return map;
	}
}
