package com.r2s.mockproject.rest;

import com.r2s.mockproject.constants.ResponseCode;
import com.r2s.mockproject.dto.CartDTOResponse;
import com.r2s.mockproject.dto.CartLineItemDTOResponse;
import com.r2s.mockproject.dto.ProductDTOResponse;
import com.r2s.mockproject.entity.Cart;
import com.r2s.mockproject.entity.CartLineItem;
import com.r2s.mockproject.entity.Product;
import com.r2s.mockproject.entity.VariantProduct;
import com.r2s.mockproject.service.CartService;
import com.r2s.mockproject.service.VariantProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController extends BaseRestController{

    @Autowired
    private CartService cartService;

    @Autowired
    private VariantProductService variantProductService;

    @GetMapping("")
    public ResponseEntity<?> getAllCartLineItem(){
        try {
            List<Cart> carts = this.cartService.getAllCart();
            List<CartDTOResponse> responses = carts.stream()
                    .map(cart -> new CartDTOResponse(cart))
                    .collect(Collectors.toList());
            return super.success(responses);
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

    @PostMapping("/add-to-cart/{id}")
    public ResponseEntity<?> addVariantProductToCart(@PathVariable long id,
                                                     @RequestBody(required = true) Map<String, Object> newCart){
        try{
            if(ObjectUtils.isEmpty(newCart)
                    || ObjectUtils.isEmpty(newCart.get("variantProductId"))
                    || ObjectUtils.isEmpty(newCart.get("quantity"))){
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            Cart foundCart = this.cartService.findCartById(id);
            if (ObjectUtils.isEmpty(foundCart)) {
                return super.error(ResponseCode.CART_NOT_FOUND.getCode(), ResponseCode.CART_NOT_FOUND.getMessage());
            }

            Long variantProductId = Long.parseLong(newCart.get("variantProductId").toString());
            VariantProduct foundVariantProduct = this.variantProductService.findVariantProductById(variantProductId);
            if (ObjectUtils.isEmpty(foundVariantProduct)) {
                return super.error(ResponseCode.VARIANT_PRODUCT_NOT_FOUND.getCode(), ResponseCode.VARIANT_PRODUCT_NOT_FOUND.getMessage());
            }

            int quantity = Integer.parseInt(newCart.get("quantity").toString());
            Cart insertedToCart = cartService.addVariantProductToCart(id, foundVariantProduct, quantity);
            return super.success(new CartDTOResponse(insertedToCart));
        }catch(Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }
}
