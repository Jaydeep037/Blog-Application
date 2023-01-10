package com.blogApp.payload;

public class CommentDto {

	private Integer commentId;
	private String comment;

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public CommentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentDto(Integer commentId, String comment) {
		super();
		this.commentId = commentId;
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "CommentDto [commentId=" + commentId + ", comment=" + comment + "]";
	}

}
