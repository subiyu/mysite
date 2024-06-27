package com.poscodx.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	private SqlSession sqlSession;

	public SiteRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public SiteVo find() {
		return sqlSession.selectOne("site.find");
	}

	public int update(SiteVo vo) {
		return sqlSession.update("site.update", vo);
	}
}
