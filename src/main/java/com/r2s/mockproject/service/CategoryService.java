package com.r2s.mockproject.service;

import com.r2s.mockproject.entity.Category;
import com.r2s.mockproject.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> findByName(String name);

    List<Category> getAllCategory();

    Category addCategory(Map<String, Object> newCategory);

}
