package com.blogApp.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.blogApp.entities.Category;
import com.blogApp.entities.Post;
import com.blogApp.entities.User;
import com.blogApp.exceptions.ResourceNotFoundException;
import com.blogApp.payload.PostDto;
import com.blogApp.payload.PostResponse;
import com.blogApp.repository.CategoryRepo;
import com.blogApp.repository.PostRepo;
import com.blogApp.repository.UserRepo;
import com.blogApp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PostRepo postRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	CategoryRepo categoryRepo;

	@Override
	public PostDto createpost(Integer userId, Integer categoryId, PostDto postDto) {

		User user = this.userRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user Id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setUser(user);
		post.setCategory(category);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		this.postRepo.save(post);
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		this.postRepo.delete(post);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
	@Override
	public PostResponse getAllPost(Integer pageNumber , Integer pageSize,String sortBy,String sortDir) {

//		Sort sort =null;
//		if(sortDir.equalsIgnoreCase("desc")) {
//			sort=Sort.by(sortBy).descending();
//		}else {
//			sort=Sort.by(sortBy).ascending();
//		}
		/*********ternary operator******/
		
		Sort sort =sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable page = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post>pagePost = this.postRepo.findAll(page);
		List<Post>post = pagePost.getContent();
		PostResponse postResponse = new PostResponse();
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setPageNumber(pagePost.getNumber());
		List<PostDto> postDto = post.stream().map(e -> this.modelMapper.map(e, PostDto.class))
				.collect(Collectors.toList());
		postResponse.setContent(postDto);
		postResponse.setTotalPage(pagePost.getTotalPages());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;

	}

	@Override
	public PostDto updatePost(Integer postId, PostDto postDto) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		post.setContent(postDto.getContent());
		post.setPostTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		post.setAddedDate(new Date());
		this.postRepo.save(post);
		PostDto updatePostDto = this.modelMapper.map(post, PostDto.class);
		return updatePostDto;
	}

	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categoryId));
		List<Post> post = this.postRepo.findByCategory(category);
		List<PostDto> postDto = post.stream().map(p -> this.modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
		System.out.println(postDto);
		return postDto;
	}

	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user Id", userId));
		List<Post> post = this.postRepo.findAllByUser(user);
		List<PostDto> postDto = post.stream().map(p -> this.modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
		return postDto;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

//	@Override
//	public List<PostDto> searchPosts(String keyword) {
//		List<Post> post = this.postRepo.searchByTitle("%"+keyword+"%");
//		List<PostDto> postDto=post.stream().map(e->this.modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
//		return postDto;
//	}

}
