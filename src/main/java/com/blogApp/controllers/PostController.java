package com.blogApp.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.lf5.util.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogApp.config.AppConstants;
import com.blogApp.exceptions.ImageExtensionException;
import com.blogApp.payload.Genericresponse;
import com.blogApp.payload.ImageResponse;
import com.blogApp.payload.PostDto;
import com.blogApp.payload.PostResponse;
import com.blogApp.services.FileService;
import com.blogApp.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	PostService postService;

	@Autowired
	FileService fileService;

	@Value("${project.image}")
	private String path;

	@PostMapping("user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> CreatePost(@PathVariable Integer userId, @PathVariable Integer categoryId,
			@RequestBody PostDto postDto) {
		return new ResponseEntity<PostDto>(this.postService.createpost(userId, categoryId, postDto),
				HttpStatus.CREATED);
	}

	@DeleteMapping("/deletePost/{postId}")
	public ResponseEntity<Genericresponse> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<Genericresponse>(new Genericresponse("Post deleted successfully", true),
				HttpStatus.OK);
	}

	@PutMapping("/updatepost/{postId}")
	public ResponseEntity<PostDto> updatePost(@PathVariable Integer postId, @RequestBody PostDto postDto) {
		PostDto updatedpostDto = this.postService.updatePost(postId, postDto);
		return ResponseEntity.ok(updatedpostDto);
	}

	@GetMapping("/getpostbyId/{postId}")
	public PostDto getPostById(@PathVariable Integer postId) {
		PostDto postDto = this.postService.getPostById(postId);
		return postDto;
	}

	@GetMapping("/getAllPost")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse postReponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(postReponse);
	}

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		List<PostDto> postDto = this.postService.getPostsByCategory(categoryId);
		return ResponseEntity.ok(postDto);
	}

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
		List<PostDto> postDto = this.postService.getPostsByUser(userId);
		return ResponseEntity.ok(postDto);
	}

	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<ImageResponse> uploadImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws ImageExtensionException {
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = null;
		try {

			try {
				fileName = this.fileService.uploadImage(path, image);
				if (fileName.equals(AppConstants.INVALID_IMAGE_FORMAT)) {
					throw new ImageExtensionException(AppConstants.INVALID_IMAGE_FORMAT);
				}
			} catch (ImageExtensionException e) {
				return new ResponseEntity<ImageResponse>(
						new ImageResponse(null, AppConstants.INVALID_IMAGE_FORMAT, null),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<ImageResponse>(new ImageResponse(null, "Image not uploaded", null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		postDto.setImageName(fileName);
		PostDto updatePostDto = this.postService.updatePost(postId, postDto);
		return new ResponseEntity<ImageResponse>(
				new ImageResponse(fileName, "Image uploaded successfully", updatePostDto), HttpStatus.OK);
	}

	@GetMapping(value = "/images/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
	public void downloadImage(@PathVariable String fileName, HttpServletResponse newresponse) throws IOException {
		InputStream resource = this.fileService.getResource(fileName, path);
		newresponse.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resource, newresponse.getOutputStream());
	}

//	@GetMapping("/searchapi/{keyword}")
//	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keyword){
//		List<PostDto>postDto = this.postService.searchPosts(keyword);
//		return ResponseEntity.ok(postDto);
//	}
}
