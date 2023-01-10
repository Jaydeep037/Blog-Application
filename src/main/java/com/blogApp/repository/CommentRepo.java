package com.blogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApp.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
