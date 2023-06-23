package com.r2s.mockproject.dto;

import com.r2s.mockproject.entity.Cart;
import com.r2s.mockproject.entity.CartLineItem;
import com.r2s.mockproject.entity.VariantProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
@AllArgsConstructor
public class CartLineItemDTOResponse {
    private Long id;
    private int quantity;

    private Long cartId;
    private Long variantProductId;
    private String variantProductName;

    public CartLineItemDTOResponse(CartLineItem cartLineItem){
        this.id = cartLineItem.getId();
        this.quantity = cartLineItem.getQuantity();
        if(!ObjectUtils.isEmpty(cartLineItem.getCart())){
            Cart cart = cartLineItem.getCart();
            this.cartId = cart.getId();
        }

        if(!ObjectUtils.isEmpty(cartLineItem.getVariantProduct())){
            VariantProduct variantProduct = cartLineItem.getVariantProduct();
            this.variantProductId = variantProduct.getId();
            this.variantProductName = variantProduct.getName();
        }
    }
}
