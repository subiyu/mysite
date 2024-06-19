package com.poscodx.mysite.exception;

public class UserRepsotiryExceoption extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UserRepsotiryExceoption() {
		super("UserRepsotiryExceoption Occurs");
	}

	public UserRepsotiryExceoption(String message) {
		super(message);
	}
}
