package com.blogApp.services.Impl;

import java.util.List;
import com.blogApp.entities.User;
import com.blogApp.payload.UserDto;
import com.blogApp.payload.UserResponse;

import java.util.stream.Collectors;
import com.blogApp.repository.UserRepo;
import com.blogApp.services.UserService;
import com.blogApp.exceptions.ResourceNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		User updatedUser = this.userRepo.save(user);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		UserDto userDto = this.userToDto(user);
		return userDto;
	}

	@Override
	public UserResponse getAllUsers(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable page = PageRequest.of(pageNumber, pageSize, sort);
		Page<User>userPage = this.userRepo.findAll(page);
		List<User> users = userPage.getContent();
		UserResponse userResponse = new UserResponse();
		userResponse.setLastPage(userPage.isLast());
		userResponse.setTotalElements(userPage.getTotalElements());
		userResponse.setPageSize(userPage.getSize());
		userResponse.setPageNumber(userPage.getNumber());
		userResponse.setTotalPages(userPage.getTotalPages());
//		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		userResponse.setUser(userDtos);
		return userResponse;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		this.userRepo.delete(user);
	}

	private User dtoToUser(UserDto userDto) {

//		User user = new User();
//		user.setId(userDto.getId());
//		user.setEmail(userDto.getEmail());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());

		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}

	private UserDto userToDto(User user) {
//		UserDto userDto =  new UserDto();
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
//		userDto.setId(user.getId());
//		userDto.setPassword(user.getPassword());
//		userDto.setName(user.getName());
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}
}