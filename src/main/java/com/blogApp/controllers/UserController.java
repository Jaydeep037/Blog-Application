package com.blogApp.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogApp.payload.Genericresponse;
import com.blogApp.payload.UserDto;
import com.blogApp.payload.UserResponse;
import com.blogApp.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/adduser")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdDtouser = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdDtouser, HttpStatus.CREATED);
	}

	@PutMapping("/updateuser/{uid}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("uid") Integer uid) {
		UserDto updatedDtoUser = this.userService.updateUser(userDto, uid);
		return ResponseEntity.ok(updatedDtoUser);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteuser/{userId}")
	public ResponseEntity<Genericresponse> deleteUser(@PathVariable("userId") Integer userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<Genericresponse>(new Genericresponse("User deleted successfully", true),
				HttpStatus.OK);
	}

	@GetMapping("/allusers")
	public ResponseEntity<UserResponse> getAllUsers(@RequestParam(value = "pageNumber", defaultValue="1",required = false)Integer PageNumber,
			@RequestParam(value = "pageSize",defaultValue = "5" ,required = false)Integer PageSize,
			@RequestParam(value = "sortBy",defaultValue = "postId",required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
			) {
		UserResponse userResponse = this.userService.getAllUsers(PageNumber,PageSize,sortBy,sortDir);
		return ResponseEntity.ok(userResponse);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
		UserDto userDto = this.userService.getUserById(userId);
		return ResponseEntity.ok(userDto);
	}
}
