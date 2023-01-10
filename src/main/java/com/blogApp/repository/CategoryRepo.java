package com.blogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApp.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
