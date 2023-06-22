package com.r2s.mockproject.service.impl;

import com.r2s.mockproject.entity.Category;
import com.r2s.mockproject.repository.CategoryRepository;
import com.r2s.mockproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category generateCategory(Map<String, Object> newCategory) {
        Category category = new Category();
        category.setName(newCategory.get("name").toString());

        return category;
    }

    @Override
    public List<Category> getAllCategory() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Map<String, Object> newCategory) {
        Category category = generateCategory(newCategory);
        category.setDeleted(false);

        return this.categoryRepository.save(category);
    }
}
