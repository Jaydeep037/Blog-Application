package com.blogApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApp.entities.Category;
import com.blogApp.entities.Post;
import com.blogApp.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	List<Post> findByCategory(Category category);

	List<Post> findAllByUser(User user);
	
//	List<Post>findByPostTitleContaining(String Title);
//	@Query("select p from Post p where p.title like :key")
//	List<Post>searchByTitle(@Param("key")String Title);
}
