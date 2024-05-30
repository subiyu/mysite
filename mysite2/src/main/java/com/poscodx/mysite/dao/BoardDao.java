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
			String url = "jdbc:mariadb://192.168.0.193:3306/webdb?charset=utf8";
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
			PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO board(title, contents, hit, reg_date, g_no, o_no, depth, user_no) VALUES(?, ?, ?, now(), ?, ?, ?, ?)");
			PreparedStatement pstmt2 = conn.prepareStatement("SELECT last_insert_id() FROM dual");
		) {
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setString(2, vo.getContents());
			pstmt1.setInt(3, vo.getHit());
			pstmt1.setString(4, vo.getRegDate());
			pstmt1.setLong(5, vo.getgNo());
			pstmt1.setLong(6, vo.getoNo());
			pstmt1.setInt(7, vo.getDepth());
			pstmt1.setLong(8, vo.getUserNo());
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
		// TODO 페이징 해야하므로 limit 추가바람
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
			PreparedStatement pstmt = conn.prepareStatement("no, title, contents, hit, reg_date, g_no, o_no, depth, user_no FROM board WHERE no=?");
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
}
