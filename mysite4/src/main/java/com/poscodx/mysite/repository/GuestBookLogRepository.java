package com.poscodx.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class GuestBookLogRepository {
	private SqlSession sqlSession;

	public GuestBookLogRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public int insert() {
		return sqlSession.update("guestbooklog.insert");
	}
	
	public int update() {
		return sqlSession.update("guestbooklog.update-increase");
	}

	public int update(Long no) {
		return sqlSession.update("guestbooklog.update-decrease", no);
	}
	
}
