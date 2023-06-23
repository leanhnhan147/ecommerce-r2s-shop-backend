package com.r2s.mockproject.rest;

import com.r2s.mockproject.constants.ResponseCode;
import com.r2s.mockproject.dto.CartLineItemDTOResponse;
import com.r2s.mockproject.dto.ProductDTOResponse;
import com.r2s.mockproject.entity.*;
import com.r2s.mockproject.service.CartLineItemService;
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
@RequestMapping("/cart-line-item")
public class CartLineItemController extends  BaseRestController{

    @Autowired
    private CartLineItemService cartLineItemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private VariantProductService variantProductService;

    @GetMapping("")
    public ResponseEntity<?> getAllCartLineItem(){
        try {
            List<CartLineItem> cartLineItems = this.cartLineItemService.getAllCartLineItem();
            List<CartLineItemDTOResponse> responses = cartLineItems.stream()
                    .map(cartLineItem -> new CartLineItemDTOResponse(cartLineItem))
                    .collect(Collectors.toList());
            return super.success(responses);
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

//    @PostMapping("")
//    public ResponseEntity<?> addCartLineItem(@RequestBody(required = true) Map<String, Object> newCartLineItem){
//        try{
//            if(ObjectUtils.isEmpty(newCartLineItem)
//                    || ObjectUtils.isEmpty(newCartLineItem.get("quantity"))
//                    || ObjectUtils.isEmpty(newCartLineItem.get("cartId"))
//                    || ObjectUtils.isEmpty(newCartLineItem.get("variantProductId"))){
//                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
//            }
//
//            Long cartId = Long.parseLong(newCartLineItem.get("cartId").toString());
//            Cart foundCart = this.cartService.findCartById(cartId);
//            if (ObjectUtils.isEmpty(foundCart)) {
//                return super.error(ResponseCode.CART_NOT_FOUND.getCode(), ResponseCode.CART_NOT_FOUND.getMessage());
//            }
//
//            Long variantProductId = Long.parseLong(newCartLineItem.get("variantProductId").toString());
//            VariantProduct foundVariantProduct = this.variantProductService.findVariantProductById(variantProductId);
//            if (ObjectUtils.isEmpty(foundVariantProduct)) {
//                return super.error(ResponseCode.VARIANT_PRODUCT_NOT_FOUND.getCode(), ResponseCode.VARIANT_PRODUCT_NOT_FOUND.getMessage());
//            }
//
//            CartLineItem insertedCartLineItem = cartLineItemService.addCartLineItem(newCartLineItem, foundCart, foundVariantProduct);
////            return super.success(new CartLineItemDTOResponse(insertedCartLineItem));
//            return super.success(insertedCartLineItem);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
//    }
}
