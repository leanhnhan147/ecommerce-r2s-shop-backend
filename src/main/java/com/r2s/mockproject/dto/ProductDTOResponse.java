package com.r2s.mockproject.dto;

import com.r2s.mockproject.entity.Category;
import com.r2s.mockproject.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
@AllArgsConstructor
public class ProductDTOResponse {
    private Long productId;
    private String productName;
//    private Long categoryId;
    private String categorName;

    public ProductDTOResponse(Product product){
        this.productId = product.getId();
        this.productName = product.getName();
        if(!ObjectUtils.isEmpty(product.getCategory())){
            Category category = product.getCategory();
//            this.categoryId = category.getId();
            this.categorName = category.getName();
        }
    }
}
