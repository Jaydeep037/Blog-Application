package com.blogApp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blogApp.payload.CategoryDto;

@Service
public interface CategoryService {

	CategoryDto creatCategory(CategoryDto categoryDto);

	CategoryDto updatCategory(Integer categoryId, CategoryDto categoryDto);

	void deleteCategory(Integer categoryId);

	CategoryDto getCategoryById(Integer categoryId);

	List<CategoryDto> getAllCategory();

}
