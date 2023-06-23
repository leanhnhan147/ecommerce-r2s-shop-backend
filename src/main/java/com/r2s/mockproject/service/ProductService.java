package com.r2s.mockproject.service;

import com.r2s.mockproject.entity.Category;
import com.r2s.mockproject.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    Product findProductById(Long id);

    Optional<Product> findByName(String name);

    List<Product> findAllProductByCategoryId(Long categoryId);

    List<Product> getAllProduct();

    Product addProduct(Map<String, Object> newProduct, Category category);

    Product updateProduct(Long id, Map<String, Object> newProduct);
}
