package com.poscodx.mysite.vo;

public class BoardVo {
	private Long no;
	private String title;
	private String contents;
	private Integer hit;
	private String regDate;
	private Long gNo;
	private Long oNo;
	private Integer depth;
	private Long userNo;
	private String userName;
	
	public Long getNo() {
		return no;
	}
	
	public void setNo(Long no) {
		this.no = no;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public Integer getHit() {
		return hit;
	}
	
	public void setHit(Integer hit) {
		this.hit = hit;
	}
	
	public String getRegDate() {
		return regDate;
	}
	
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	public Long getgNo() {
		return gNo;
	}
	
	public void setgNo(Long gNo) {
		this.gNo = gNo;
	}
	
	public Long getoNo() {
		return oNo;
	}
	
	public void setoNo(Long oNo) {
		this.oNo = oNo;
	}
	
	public Integer getDepth() {
		return depth;
	}
	
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	
	public Long getUserNo() {
		return userNo;
	}
	
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", contents=" + contents + ", hit=" + hit + ", regDate="
				+ regDate + ", gNo=" + gNo + ", oNo=" + oNo + ", depth=" + depth + ", userNo=" + userNo + ", userName="
				+ userName + "]";
	}
}