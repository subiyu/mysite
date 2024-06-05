package com.poscodx.mysite.service;

import java.util.List;
import java.util.Map;

import com.poscodx.mysite.vo.BoardVo;

public class BoardService {
	
		public BoardVo addContents(BoardVo vo) {
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
	
		}
		
		public Map<String, Object> getContentsList(int currentPage, String keyword) {
			List<BoardVo> list = null;
			Map<String, Object> map = null;
			
			map.put("list", list);
			map.put("keyword", "");
			//map.put("pager", pager); 이건 페이저 객체
			map.put("totalCount", 0);
			map.put("listSize", 0);
			map.put("currentPage", 0);
			map.put("beginPage", 0);
			map.put("endPage", 0);
			map.put("prevPage", 0);
			map.put("nextPage", 0);
			
			return map;
		}
}
