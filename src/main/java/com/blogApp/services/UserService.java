package com.blogApp.services;


import org.springframework.stereotype.Service;

import com.blogApp.payload.UserDto;
import com.blogApp.payload.UserResponse;

@Service
public interface UserService {

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer UserId);

	UserResponse getAllUsers(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

	void deleteUser(Integer userId);
}
