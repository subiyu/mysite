package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.poscodx.mysite.exception.UserRepsotiryExceoption;
import com.poscodx.mysite.vo.UserVo;

@Repository
public class UserRepository {
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");			
			String url = "jdbc:mariadb://192.168.0.193:3306/webdb?charset=utf8";
			//String url = "jdbc:mariadb://192.168.35.55:3306/webdb?charset=utf8";			
			//String url = "jdbc:mariadb://172.20.10.11:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
		
		return conn;
	}

	public int insert(UserVo vo) {
		int result = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO user VALUES(null, ?, ?, password(?), ?, current_date());");
			PreparedStatement pstmt2 = conn.prepareStatement("SELECT last_insert_id() FROM dual");
		) {
			pstmt1.setString(1, vo.getName());
			pstmt1.setString(2, vo.getEmail());
			pstmt1.setString(3, vo.getPassword());
			pstmt1.setString(4, vo.getGender());
			result = pstmt1.executeUpdate();
			
			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch(SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo result = null;		//test code(testOrder())에서 assertNull 검사를 하므로, 매칭되는 값이 없으면 null을 반환해야한다.
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT no, name FROM user WHERE email = ? and password = password(?)");
		) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = new UserVo();		 // 매칭되는 값이 존재하면 객체 생성
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				result.setNo(no);
				result.setName(name);
			}
			rs.close();
		} catch(SQLException e) {
			throw new UserRepsotiryExceoption();
		} 
		
		return result;
	}

	public UserVo findByNo(Long no) {
		UserVo result = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT no, name, email, gender FROM user WHERE no = ?");
		) {
			pstmt.setLong(1, no);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = new UserVo();		 // 매칭되는 값이 존재하면 객체 생성
				no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
			}
			rs.close();
		} catch(SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}

	public int update(UserVo vo) {
		int result = 0;
		ResultSet rs = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("UPDATE user SET name=?, gender=? WHERE no=?");
			PreparedStatement pstmt2 = conn.prepareStatement("UPDATE user SET name=?, password=password(?), gender=? WHERE no=?");
		) {
			if("".equals(vo.getPassword())) {
				pstmt1.setString(1, vo.getName());
				pstmt1.setString(2, vo.getGender());
				pstmt1.setLong(3, vo.getNo());
				result = pstmt1.executeUpdate();
				rs = pstmt1.executeQuery();
			} else {
				pstmt2.setString(1, vo.getName());
				pstmt2.setString(2, vo.getPassword());
				pstmt2.setString(3, vo.getGender());
				pstmt2.setLong(4, vo.getNo());
				result = pstmt2.executeUpdate();
				rs = pstmt2.executeQuery();
			}
			
			rs.close();
		} catch(SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}
	
	
}
