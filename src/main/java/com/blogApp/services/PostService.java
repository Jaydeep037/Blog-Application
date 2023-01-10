package com.blogApp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blogApp.payload.PostDto;
import com.blogApp.payload.PostResponse;

@Service
public interface PostService {

	PostDto createpost(Integer userId, Integer categoryId, PostDto postDto);

	PostDto updatePost(Integer postId, PostDto postDto);

	void deletePost(Integer postId);

	List<PostDto> getPostsByUser(Integer userId);

	PostDto getPostById(Integer postId);

	List<PostDto> getPostsByCategory(Integer categoryId);

	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
//	List<PostDto> searchPosts(String keyword);
}
