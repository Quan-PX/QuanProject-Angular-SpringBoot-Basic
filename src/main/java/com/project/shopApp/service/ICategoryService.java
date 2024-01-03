package com.project.shopApp.service;

import com.project.shopApp.domain.Category;
import com.project.shopApp.web.rest.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {

    List<Category> getAllCategories();

    Category createCategory(CategoryDTO categoryDTO);

    Category updateCategory(Long categoryId, CategoryDTO categoryDTO);

    Category getCategoryById(Long id);

    void deleteCategory(Long id);
}
