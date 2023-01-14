package com.blogApp.payload;

public class RoleDto {

	private Integer Id;
	private String name;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public RoleDto(Integer id, String name) {
		super();
		Id = id;
		this.name = name;
	}
	public RoleDto() {
		super();
	}
	
}
