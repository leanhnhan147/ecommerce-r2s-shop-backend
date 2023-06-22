package com.r2s.mockproject.service;

import com.r2s.mockproject.entity.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CategoryService {

    Category findCategoryById(Long id);

    Optional<Category> findByName(String name);

    List<Category> getAllCategory();

    Category addCategory(Map<String, Object> newCategory);

    Category updateCategory(Long id, Map<String, Object> newCategory);

}
