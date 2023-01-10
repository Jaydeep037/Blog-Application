package com.blogApp.services;

import org.springframework.stereotype.Service;

import com.blogApp.payload.CommentDto;

@Service
public interface CommentService {

public CommentDto addComment(CommentDto commentDto,Integer postId);

public void deleteComment(Integer commentId);

}
