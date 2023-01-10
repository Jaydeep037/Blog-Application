package com.blogApp.payload;



public class ImageResponse {

	private String fileName;
	private String message;
	
	private PostDto postDto;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PostDto getPostDto() {
		return postDto;
	}

	public void setPostDto(PostDto postDto) {
		this.postDto = postDto;
	}

	public ImageResponse(String fileName, String message, PostDto postDto) {
		super();
		this.fileName = fileName;
		this.message = message;
		this.postDto = postDto;
	}
	
	
	
}
