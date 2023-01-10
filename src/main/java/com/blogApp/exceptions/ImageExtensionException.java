package com.blogApp.exceptions;

public class ImageExtensionException extends Exception {

	
	String message;
	public ImageExtensionException(String message) {
		super(message);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
}
