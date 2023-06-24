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
}
