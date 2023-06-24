package com.r2s.mockproject.service;

import com.r2s.mockproject.entity.Product;
import com.r2s.mockproject.entity.VariantProduct;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VariantProductService {

    VariantProduct findVariantProductById(Long id);

    Optional<VariantProduct> findByName(String name);

    List<VariantProduct> findVariantProductByProductId(Long productId);

    List<VariantProduct> getAllVariantProduct();

    VariantProduct addVariantProduct(Map<String, Object> newVariantProduct, Product product);

    VariantProduct updateVariantProduct(Long id, Map<String, Object> newVariantProduct);


}
