package com.r2s.mockproject.service.impl;

import com.r2s.mockproject.entity.Product;
import com.r2s.mockproject.entity.VariantProduct;
import com.r2s.mockproject.repository.VariantProductRepository;
import com.r2s.mockproject.service.VariantProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VariantProductServiceImpl implements VariantProductService {

    @Autowired
    VariantProductRepository variantProductRepository;

    @Override
    public VariantProduct findVariantProductById(Long id) {
        return this.variantProductRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<VariantProduct> findByName(String name) {
        return this.variantProductRepository.findByName(name);
    }

    @Override
    public List<VariantProduct> findVariantProductByProductId(Long productId) {
        return this.variantProductRepository.findByProduct_Id(productId);
    }

    @Override
    public List<VariantProduct> getAllVariantProduct() {
        return this.variantProductRepository.findAll();
    }

    @Override
    public VariantProduct addVariantProduct(Map<String, Object> newVariantProduct, Product product) {
        VariantProduct variantProduct = new VariantProduct();
        variantProduct.setName(newVariantProduct.get("name").toString());
        variantProduct.setScreenSize(newVariantProduct.get("screenSize").toString());
        variantProduct.setStorageCapacity(newVariantProduct.get("storageCapacity").toString());
        variantProduct.setColor(newVariantProduct.get("color").toString());
        variantProduct.setWeight(newVariantProduct.get("weight").toString());
        variantProduct.setPrice(Double.parseDouble(newVariantProduct.get("price").toString()));
        variantProduct.setDeleted(false);
        variantProduct.setProduct(product);
        return this.variantProductRepository.save(variantProduct);
    }

    @Override
    public VariantProduct updateVariantProduct(Long id, Map<String, Object> newVariantProduct) {
        VariantProduct variantProduct = this.findVariantProductById(id);
        variantProduct.setName(newVariantProduct.get("name").toString());
        variantProduct.setScreenSize(newVariantProduct.get("screenSize").toString());
        variantProduct.setStorageCapacity(newVariantProduct.get("storageCapacity").toString());
        variantProduct.setColor(newVariantProduct.get("color").toString());
        variantProduct.setWeight(newVariantProduct.get("weight").toString());
        variantProduct.setPrice(Double.parseDouble(newVariantProduct.get("price").toString()));
        return this.variantProductRepository.save(variantProduct);
    }
}
