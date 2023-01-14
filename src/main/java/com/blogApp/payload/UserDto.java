package com.blogApp.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class UserDto {

	private int id;

	@NotEmpty
	@Size(min = 4, message = "Username must be min of 4 characters")
	private String name;
	@Email(message = "Invalid email address")
	private String email;
	@NotEmpty
	@Size(min = 3, max = 15, message = "password must be min of 3 characters and max of 15 characters")
	private String password;
	@NotEmpty
	private String about;
	private Set<RoleDto> roles = new HashSet<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public Set<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}
	public UserDto(int id, @NotEmpty @Size(min = 4, message = "Username must be min of 4 characters") String name,
			@Email(message = "Invalid email address") String email,
			@NotEmpty @Size(min = 3, max = 15, message = "password must be min of 3 characters and max of 15 characters") String password,
			@NotEmpty String about, Set<RoleDto> roles) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
		this.roles = roles;
	}
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
