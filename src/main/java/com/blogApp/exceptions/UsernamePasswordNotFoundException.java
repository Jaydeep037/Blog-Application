package com.blogApp.exceptions;

public class UsernamePasswordNotFoundException extends Exception{

	private String message;

	public UsernamePasswordNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsernamePasswordNotFoundException(String message) {
		super(message);
		this.message = message;
	}
	
	
	
}
