package com.blogApp.controllers;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogApp.payload.CategoryDto;
import com.blogApp.payload.Genericresponse;
import com.blogApp.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryControllers {

	@Autowired
	CategoryService categoryService;

	@PostMapping("/addCategory")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<CategoryDto>(this.categoryService.creatCategory(categoryDto), HttpStatus.CREATED);
	}

	@PostMapping("/updateCategory/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategoryDto(@Valid @PathVariable Integer categoryId,
			@RequestBody CategoryDto categoryDto) {
		return ResponseEntity.ok(this.categoryService.updatCategory(categoryId, categoryDto));
	}

	@GetMapping("/getCategory")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		return new ResponseEntity<List<CategoryDto>>(this.categoryService.getAllCategory(), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<Genericresponse> deleteCategory(@PathVariable Integer categoryId) {
		this.deleteCategory(categoryId);
		return new ResponseEntity<Genericresponse>(new Genericresponse("category deleted successfully", true),
				HttpStatus.OK);
	}

	@GetMapping("/getCategoryById/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {
		return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
	}
}
