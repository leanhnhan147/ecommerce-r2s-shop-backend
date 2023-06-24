package com.r2s.mockproject.dto;

import com.r2s.mockproject.entity.Category;
import com.r2s.mockproject.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class CategoryDTOResponse {

    private Long categoryId;
    private String categoryName;

    private List<Map<String, Object>> products;

    public CategoryDTOResponse(Category category){
        this.categoryId = category.getId();
        this.categoryName = category.getName();

        this.products = new ArrayList<>();
        for(Product product : category.getProducts()){
            this.products.add(Map.of("productId", product.getId(),
                    "productName", product.getName()));
        }
    }
}
