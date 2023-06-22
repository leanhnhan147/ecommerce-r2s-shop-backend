package com.r2s.mockproject.rest;

import com.r2s.mockproject.constants.ResponseCode;
import com.r2s.mockproject.dto.ProductDTOResponse;
import com.r2s.mockproject.dto.VariantProductDTOResponse;
import com.r2s.mockproject.entity.Category;
import com.r2s.mockproject.entity.Product;
import com.r2s.mockproject.entity.VariantProduct;
import com.r2s.mockproject.service.ProductService;
import com.r2s.mockproject.service.VariantProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/variant-product")
public class VariantProductController extends BaseRestController{

    @Autowired
    VariantProductService variantProductService;

    @Autowired
    ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> getAllVariantProduct(){
        try {
            List<VariantProduct> variantProducts = this.variantProductService.getAllVariantProduct();
            List<VariantProductDTOResponse> responses = variantProducts.stream()
                    .map(variantProduct -> new VariantProductDTOResponse(variantProduct))
                    .collect(Collectors.toList());
            return super.success(responses);
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> addVariantProduct(@RequestBody(required = true) Map<String, Object> newVariantProduct){
        try{
            if(ObjectUtils.isEmpty(newVariantProduct)
                    || ObjectUtils.isEmpty(newVariantProduct.get("name"))
                    || ObjectUtils.isEmpty(newVariantProduct.get("screenSize"))
                    || ObjectUtils.isEmpty(newVariantProduct.get("storageCapacity"))
                    || ObjectUtils.isEmpty(newVariantProduct.get("color"))
                    || ObjectUtils.isEmpty(newVariantProduct.get("weight"))
                    || ObjectUtils.isEmpty(newVariantProduct.get("price"))
                    || ObjectUtils.isEmpty(newVariantProduct.get("productId"))){
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            VariantProduct foundVariantProduct = this.variantProductService
                    .findByName(newVariantProduct.get("name").toString()).orElse(null);
            if(!ObjectUtils.isEmpty(foundVariantProduct)){
                return super.error(ResponseCode.DATA_ALREADY_EXISTS.getCode(),
                        ResponseCode.DATA_ALREADY_EXISTS.getMessage());
            }

            Long productId = Long.parseLong(newVariantProduct.get("productId").toString());
            Product foundProduct = this.productService.findProductById(productId);
            if (ObjectUtils.isEmpty(foundProduct)) {
                return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
            }

            VariantProduct insertedVariantProduct = variantProductService.addVariantProduct(newVariantProduct, foundProduct);
//            return super.success(new VariantProductDTOResponse(insertedVariantProduct));
            return super.success(insertedVariantProduct);
        }catch(Exception e){
            e.printStackTrace();
        }

        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }
}
