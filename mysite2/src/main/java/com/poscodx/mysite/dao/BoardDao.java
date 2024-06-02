package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.GuestBookVo;
import com.poscodx.mysite.vo.UserVo;

public class BoardDao {
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
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("SELECT MAX(g_no) FROM board");
			PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO board(title, contents, hit, reg_date, g_no, o_no, depth, user_no) VALUES(?, ?, 0, now(), ?, 1, 0, ?)");
			PreparedStatement pstmt3 = conn.prepareStatement("SELECT last_insert_id() FROM dual");
		) {
			ResultSet rs = pstmt1.executeQuery();
			int maxGno = 0;
			if (rs.next()) {
			    maxGno = rs.getInt(1);
			}
			pstmt2.setString(1, vo.getTitle());
			pstmt2.setString(2, vo.getContents());
			pstmt2.setLong(3, maxGno+1);
			pstmt2.setLong(4, vo.getUserNo());
			
			result = pstmt2.executeUpdate();
			
			rs = pstmt3.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch(SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}
	
	public int insertReply(BoardVo vo) {
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO board(title, contents, hit, reg_date, g_no, o_no, depth, user_no) VALUES(?, ?, 0, now(), ?, ?, ?, ?)");
			PreparedStatement pstmt2 = conn.prepareStatement("SELECT last_insert_id() FROM dual");
		) {
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setString(2, vo.getContents());
			pstmt1.setLong(3, vo.getgNo());
			pstmt1.setLong(4, vo.getoNo());
			pstmt1.setInt(5, vo.getDepth());
			pstmt1.setLong(6, vo.getUserNo());
			result = pstmt1.executeUpdate();
			
			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch(SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}
	
	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT no, title, contents, hit, reg_date, g_no, o_no, depth, user_no "
															+ "FROM board ORDER BY g_no desc, o_no asc");
			ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Integer hit = rs.getInt(4);
				String regDate = rs.getString(5);
				Long gNo = rs.getLong(6);
				Long oNo = rs.getLong(7);
				Integer depth = rs.getInt(8);
				Long userNo = rs.getLong(9);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}

	public List<BoardVo> findBoardList(int currentPage, int boardsPerPage) {
		List<BoardVo> result = new ArrayList<>();
		int offset = (currentPage - 1) * boardsPerPage; // 페이지 오프셋 계산
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT a.no, a.title, a.contents, a.hit, a.reg_date, a.g_no, a.o_no, a.depth, a.user_no, b.name "
															+ "FROM board a, user b "
															+ "WHERE a.user_no = b.no "
															+ "ORDER BY g_no desc, o_no asc "
															+ "LIMIT ? OFFSET ?");
		) {
			pstmt.setInt(1, boardsPerPage);
			pstmt.setInt(2, offset);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Integer hit = rs.getInt(4);
				String regDate = rs.getString(5);
				Long gNo = rs.getLong(6);
				Long oNo = rs.getLong(7);
				Integer depth = rs.getInt(8);
				Long userNo = rs.getLong(9);
				String userName = rs.getString(10);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}
	
	//title, content 대상으로 검색
	public List<BoardVo> findByKeyword(String keyword) {
		List<BoardVo> result = new ArrayList<>();
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT a.no, a.title, a.contents, a.hit, a.reg_date, a.g_no, a.o_no, a.depth, a.user_no, b.name "
															+ "FROM board a, user b "
															+ "WHERE a.user_no = b.no "
															+ "and a.title LIKE ? "
															+ "or a.contents LIKE ? "
															+ "ORDER BY g_no desc, o_no asc");
		) {
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Integer hit = rs.getInt(4);
				String regDate = rs.getString(5);
				Long gNo = rs.getLong(6);
				Long oNo = rs.getLong(7);
				Integer depth = rs.getInt(8);
				Long userNo = rs.getLong(9);
				String userName = rs.getString(10);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}

	public int deleteByNo(long no) {
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM board WHERE no = ?");
		) {
			pstmt.setLong(1, no);
			result = pstmt.executeUpdate();
		} catch(SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}

	public BoardVo findByNo(Long no) {
		BoardVo result = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT no, title, contents, hit, reg_date, g_no, o_no, depth, user_no FROM board WHERE no=?");
		) {
			pstmt.setLong(1, no);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = new BoardVo();		 // 매칭되는 값이 존재하면 객체 생성
				no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Integer hit = rs.getInt(4);
				String regDate = rs.getString(5);
				Long gNo = rs.getLong(6);
				Long oNo = rs.getLong(7);
				Integer depth = rs.getInt(8);
				Long userNo = rs.getLong(9);
				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);
				result.setHit(hit);
				result.setRegDate(regDate);
				result.setgNo(gNo);
				result.setoNo(oNo);
				result.setDepth(depth);
				result.setUserNo(userNo);
			}
			rs.close();
		} catch(SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}

	public int update(Long no, String title, String contents) {
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("UPDATE board SET title=?, contents=? WHERE no=?");
		) {
			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setLong(3, no);
			result = pstmt.executeUpdate();
		} catch(SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}
	
	public int updateBeforeInsert(Long gNo, Long oNo) {
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("UPDATE board SET o_no = o_no + 1 "
															+ "where g_no = ? and o_no >= ?");
		) {
			pstmt.setLong(1, gNo);
			pstmt.setLong(2, oNo);
			result = pstmt.executeUpdate();
		} catch(SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}
	
	public int updateHit(BoardVo vo) {
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("UPDATE board SET hit = hit + 1 WHERE no = ?");
		) {
			pstmt.setLong(1, vo.getNo());
			result = pstmt.executeUpdate();
		} catch(SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}
}
