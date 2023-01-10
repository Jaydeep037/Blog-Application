package com.blogApp.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApp.entities.Category;
import com.blogApp.exceptions.ResourceNotFoundException;
import com.blogApp.payload.CategoryDto;
import com.blogApp.repository.CategoryRepo;
import com.blogApp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	ModelMapper modelmapper;

	@Override
	public CategoryDto creatCategory(CategoryDto categoryDto) {
		Category category = this.modelmapper.map(categoryDto, Category.class);
		Category saveCatogory = this.categoryRepo.save(category);
		return this.modelmapper.map(saveCatogory, CategoryDto.class);
	}

	@Override
	public CategoryDto updatCategory(Integer categoryId, CategoryDto categoryDto) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getcategoryDescription());
		Category updatedCategory = this.categoryRepo.save(category);
		return this.modelmapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> category = this.categoryRepo.findAll();
		List<CategoryDto> categoryDto = category.stream().map(e -> this.modelmapper.map(e, CategoryDto.class))
				.collect(Collectors.toList());
		return categoryDto;
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		CategoryDto categoryDto = this.modelmapper.map(category, CategoryDto.class);
		return categoryDto;
	}

}
