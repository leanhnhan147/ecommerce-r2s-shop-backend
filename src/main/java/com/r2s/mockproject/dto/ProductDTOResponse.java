package com.r2s.mockproject.dto;

import com.r2s.mockproject.entity.Category;
import com.r2s.mockproject.entity.Product;
import com.r2s.mockproject.entity.VariantProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ProductDTOResponse {
    private Long productId;
    private String productName;
//    private Long categoryId;
//    private String categorName;

    private List<Map<String, Object>> variantProducts;

    public ProductDTOResponse(Product product){
        this.productId = product.getId();
        this.productName = product.getName();
//        if(!ObjectUtils.isEmpty(product.getCategory())){
//            Category category = product.getCategory();
//            this.categoryId = category.getId();
//            this.categorName = category.getName();
//        }
        this.variantProducts = new ArrayList<>();
        for(VariantProduct variantProduct : product.getVariantProducts()){
            this.variantProducts.add(Map.of(
                    "variantProductName", variantProduct.getName(),
                    "variantProductId", variantProduct.getId()));
        }
    }
}
