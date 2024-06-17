package com.poscodx.mysite.dto;

public class JsonResult {
	private String result;		// "success" or "fail"
	private String message;		// if fail, set
	private Object data;		// if success, set
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
