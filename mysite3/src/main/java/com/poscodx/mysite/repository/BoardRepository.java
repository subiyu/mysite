package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.GuestBookVo;
import com.poscodx.mysite.vo.UserVo;

@Repository
public class BoardRepository {
	private SqlSession sqlSession;
	
	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");			
			//String url = "jdbc:mariadb://192.168.0.193:3306/webdb?charset=utf8";
			//String url = "jdbc:mariadb://192.168.35.55:3306/webdb?charset=utf8";
			String url = "jdbc:mariadb://172.20.10.11:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
		
		return conn;
	}
	
	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}

	public List<BoardVo> findAllByPageAndKeyword(int contentsPerPage, int offset, String keyword) {
		//TODO: 시퀄문 $, # 차이 알아보기
		Map<String, Object> map = new HashMap<>();
		map.put("cpp", contentsPerPage);
		map.put("offset", offset);
		map.put("keyword", keyword);
		
		return sqlSession.selectList("board.findAllByPageAndKeyword", map);
	}

	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);
	}

	public BoardVo findByNoAndUserNo(Long boardNo, Long userNo) {
		return sqlSession.selectOne("board.findByNoAndUserNo", Map.of("boardNo", boardNo, "userNo", userNo));
	}
	
	public int getTotalCount(String keyword) {
		return sqlSession.selectOne("board.totalCount", keyword);
	}
	
	public int deleteByNoAndUserNo(Long boardNo, Long userNo) {
		return sqlSession.delete("board.delete", Map.of("boardNo", boardNo, "userNo", userNo));
	}

	public int update(Long no, String title, String contents) {
		return sqlSession.update("board.update", Map.of("no", no, "title", title, "contents", contents));
	}
	
	public int updateOrderNo(Long gNo, Long oNo) {
		return sqlSession.update("board.updateOrderNo", Map.of("gNo", gNo, "oNo", oNo));
	}
	
	public int updateHit(BoardVo vo) {
		return sqlSession.update("board.updateHit", vo);
	}

}
