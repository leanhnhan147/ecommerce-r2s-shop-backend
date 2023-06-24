package com.r2s.mockproject.dto;

import com.r2s.mockproject.entity.Cart;
import com.r2s.mockproject.entity.CartLineItem;
import com.r2s.mockproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Data
@AllArgsConstructor
public class CartDTOResponse {

    private Long id;

    private String fullName;
    private List<Map<String, Object>> cartLineItems;

    public CartDTOResponse(Cart cart){
        this.id = cart.getId();
        if(!ObjectUtils.isEmpty(cart.getUser())){
            User user = cart.getUser();
            this.fullName = user.getFullName();
        }

        this.cartLineItems = new ArrayList<>();
        for (CartLineItem cartLineItem : cart.getCartLineItems()){
            this.cartLineItems.add(Map.of(
                    "quantity",cartLineItem.getQuantity(),
                    "cartLineItemName", cartLineItem.getVariantProduct().getName()));
//            this.cartLineItems.add(Map.of("quantity", cartLineItem.getQuantity()));
        }
    }
}
