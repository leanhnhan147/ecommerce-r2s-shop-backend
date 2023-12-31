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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/getAllVariantProductByProductId")
    public ResponseEntity<?> getAllProductsByCategory(@RequestParam(defaultValue = "1") Long productId,
                                                      @RequestParam(defaultValue = "0") Integer offset,
                                                      @RequestParam(defaultValue = "5") Integer limit) {
        try {
            Pageable pageable = PageRequest.of(offset, limit);
            List<Order> orders = new ArrayList<>();
            if (!orders.isEmpty()) {
                pageable = PageRequest.of(offset, limit, Sort.by(orders));
            }

            Product foundProduct = this.productService.findProductById(productId);
            if (ObjectUtils.isEmpty(foundProduct)) {
                return super.error(ResponseCode.PRODUCT_NOT_FOUND.getCode(), ResponseCode.PRODUCT_NOT_FOUND.getMessage());
            }

            List<VariantProduct> foundVariantProducts = variantProductService.findVariantProductByProductId(productId);
            int startIndex = (int) pageable.getOffset();
            int endIndex = Math.min((startIndex + pageable.getPageSize()), foundVariantProducts.size());
            List<VariantProduct> variantProducts = foundVariantProducts.subList(startIndex, endIndex);
            List<VariantProductDTOResponse> responses = variantProducts.stream()
                    .map(variantProduct -> new VariantProductDTOResponse(variantProduct))
                    .collect(Collectors.toList());
            return super.success(responses);
        } catch (Exception e) {
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

            Long productId = Long.parseLong(newVariantProduct.get("productId").toString());
            Product foundProduct = this.productService.findProductById(productId);
            if (ObjectUtils.isEmpty(foundProduct)) {
                return super.error(ResponseCode.PRODUCT_NOT_FOUND.getCode(), ResponseCode.PRODUCT_NOT_FOUND.getMessage());
            }

            VariantProduct insertedVariantProduct = variantProductService.addVariantProduct(newVariantProduct, foundProduct);
            return super.success(new VariantProductDTOResponse(insertedVariantProduct));
//            return super.success(insertedVariantProduct);
        }catch(Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVariantProduct(@PathVariable long id,
                                                  @RequestBody(required = false) Map<String, Object> newVariantProduct){
        try{
            if(ObjectUtils.isEmpty(newVariantProduct)
                    || ObjectUtils.isEmpty(newVariantProduct.get("name"))
                    || ObjectUtils.isEmpty(newVariantProduct.get("screenSize"))
                    || ObjectUtils.isEmpty(newVariantProduct.get("storageCapacity"))
                    || ObjectUtils.isEmpty(newVariantProduct.get("color"))
                    || ObjectUtils.isEmpty(newVariantProduct.get("weight"))
                    || ObjectUtils.isEmpty(newVariantProduct.get("price"))){
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            VariantProduct foundVariantProduct = this.variantProductService.findVariantProductById(id);
            if (ObjectUtils.isEmpty(foundVariantProduct)) {
                return super.error(ResponseCode.VARIANT_PRODUCT_NOT_FOUND.getCode(), ResponseCode.VARIANT_PRODUCT_NOT_FOUND.getMessage());
            }

            VariantProduct updatedVariantProduct = variantProductService.updateVariantProduct(id, newVariantProduct);
            return super.success(new VariantProductDTOResponse(updatedVariantProduct));
//            return super.success(updatedVariantProduct);
        }catch(Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }
}
