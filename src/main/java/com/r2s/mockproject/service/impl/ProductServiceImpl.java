package com.r2s.mockproject.service.impl;

import com.r2s.mockproject.entity.Category;
import com.r2s.mockproject.entity.Product;
import com.r2s.mockproject.repository.ProductRepository;
import com.r2s.mockproject.service.CategoryService;
import com.r2s.mockproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Product findProductById(Long id) {
        return this.productRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return this.productRepository.findByName(name);
    }

    @Override
    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    @Override
    public Product addProduct(Map<String, Object> newProduct, Category category) {
        Product product = new Product();
        product.setName(newProduct.get("name").toString());
        product.setDeleted(false);
        product.setCategory(category);
        return this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Map<String, Object> newProduct) {
        return null;
    }
}
