package com.blogApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogApp.payload.CommentDto;
import com.blogApp.payload.Genericresponse;
import com.blogApp.services.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@PostMapping("/addComment/{postId}")
	public ResponseEntity<CommentDto> addComment(@PathVariable Integer postId,@RequestBody CommentDto commentDto){
		return new ResponseEntity<CommentDto>(this.commentService.addComment(commentDto,postId),HttpStatus.CREATED);
	}
	@DeleteMapping("/deletecomment/{commentId}")
	public ResponseEntity<Genericresponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<Genericresponse>(new Genericresponse("Comment deleted successfully !!",true),HttpStatus.ACCEPTED);
	}
	
}
