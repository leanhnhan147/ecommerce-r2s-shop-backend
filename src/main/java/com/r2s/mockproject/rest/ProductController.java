package com.r2s.mockproject.rest;

import com.r2s.mockproject.constants.ResponseCode;
import com.r2s.mockproject.dto.ProductDTOResponse;
import com.r2s.mockproject.entity.Category;
import com.r2s.mockproject.entity.Product;
import com.r2s.mockproject.service.CategoryService;
import com.r2s.mockproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController extends BaseRestController{

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> getAllProduct(){
        try {
            List<Product> products = this.productService.getAllProduct();
            List<ProductDTOResponse> responses = products.stream()
                    .map(product -> new ProductDTOResponse(product))
                    .collect(Collectors.toList());
            return super.success(responses);
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

    @GetMapping("/getProductById")
    public ResponseEntity<?> getProductById(@RequestParam(name = "id", required = false, defaultValue = "1") Long id) {
        Product foundProduct = this.productService.findProductById(id);
        if (ObjectUtils.isEmpty(foundProduct)) {
            return super.error(ResponseCode.PRODUCT_NOT_FOUND.getCode(), ResponseCode.PRODUCT_NOT_FOUND.getMessage());
        }
        return super.success(new ProductDTOResponse(foundProduct));
    }

    @GetMapping("/getAllProductByCategoryId")
    public ResponseEntity<?> getAllProductsByCategory(@RequestParam(defaultValue = "1") Long categoryId,
                                                      @RequestParam(defaultValue = "0") Integer offset,
                                                      @RequestParam(defaultValue = "5") Integer limit) {
        try {
            Pageable pageable = PageRequest.of(offset, limit);
            List<Order> orders = new ArrayList<>();
            if (!orders.isEmpty()) {
                pageable = PageRequest.of(offset, limit, Sort.by(orders));
            }

            Category foundCategory = this.categoryService.findCategoryById(categoryId);
            if (ObjectUtils.isEmpty(foundCategory)) {
                return super.error(ResponseCode.CATEGORY_NOT_FOUND.getCode(), ResponseCode.CATEGORY_NOT_FOUND.getMessage());
            }

            List<Product> foundProducts = productService.findAllProductByCategoryId(categoryId);
            int startIndex = (int) pageable.getOffset();
            int endIndex = Math.min((startIndex + pageable.getPageSize()), foundProducts.size());
            List<Product> products = foundProducts.subList(startIndex, endIndex);
            List<ProductDTOResponse> responses = products.stream()
                    .map(product -> new ProductDTOResponse(product))
                    .collect(Collectors.toList());
            return super.success(responses);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> addProduct(@RequestBody(required = true) Map<String, Object> newProduct){
        try{
            if(ObjectUtils.isEmpty(newProduct)
                    || ObjectUtils.isEmpty(newProduct.get("name"))
                    || ObjectUtils.isEmpty(newProduct.get("categoryId"))){
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            Product foundProduct = this.productService.findByName(newProduct.get("name").toString()).orElse(null);
            if(!ObjectUtils.isEmpty(foundProduct)){
                return super.error(ResponseCode.DATA_ALREADY_EXISTS.getCode(),
                        ResponseCode.DATA_ALREADY_EXISTS.getMessage());
            }

            Long categoryId = Long.parseLong(newProduct.get("categoryId").toString());
            Category foundCategory = this.categoryService.findCategoryById(categoryId);
            if (ObjectUtils.isEmpty(foundCategory)) {
                return super.error(ResponseCode.CATEGORY_NOT_FOUND.getCode(), ResponseCode.CATEGORY_NOT_FOUND.getMessage());
            }

            Product insertedProduct = productService.addProduct(newProduct, foundCategory);
            return super.success(new ProductDTOResponse(insertedProduct));
//            return super.success(insertedProduct);
        }catch(Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id,
                                           @RequestBody(required = false) Map<String, Object> newProduct){
        try{
            if(ObjectUtils.isEmpty(newProduct)
                    || ObjectUtils.isEmpty(newProduct.get("name"))){
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            Product foundProduct = this.productService.findProductById(id);
            if (ObjectUtils.isEmpty(foundProduct)) {
                return super.error(ResponseCode.PRODUCT_NOT_FOUND.getCode(), ResponseCode.PRODUCT_NOT_FOUND.getMessage());
            }

            Product updatedProduct = productService.updateProduct(id, newProduct);
            return super.success(new ProductDTOResponse(updatedProduct));
//            return super.success(insertedProduct);
        }catch(Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }
}
