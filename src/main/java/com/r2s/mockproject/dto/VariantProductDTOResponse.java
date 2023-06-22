package com.r2s.mockproject.dto;

import com.r2s.mockproject.entity.Product;
import com.r2s.mockproject.entity.VariantProduct;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
@AllArgsConstructor
public class VariantProductDTOResponse {
    private Long id;
    private String name;
    private String screenSize;
    private String storageCapacity;
    private String color;
    private String weight;
    private double price;

    private  Long productId;
    private  String productName;

    public VariantProductDTOResponse(VariantProduct variantProduct){
        this.id = variantProduct.getId();
        this.name = variantProduct.getName();
        this.screenSize = variantProduct.getScreenSize();
        this.storageCapacity = variantProduct.getStorageCapacity();
        this.color = variantProduct.getColor();;
        this.weight = variantProduct.getWeight();;
        this.price = variantProduct.getPrice();
        if(!ObjectUtils.isEmpty(variantProduct.getProduct())){
            Product product = variantProduct.getProduct();
            this.productId = product.getId();
            this.productName = product.getName();
        }
    }
}
