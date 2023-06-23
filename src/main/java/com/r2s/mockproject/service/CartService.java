package com.r2s.mockproject.service;

import com.r2s.mockproject.entity.Cart;
import com.r2s.mockproject.entity.CartLineItem;
import com.r2s.mockproject.entity.VariantProduct;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CartService {

    Cart findCartById(Long id);

    List<Cart> getAllCart();

    Cart addVariantProductToCart(Long cartId, VariantProduct variantProduct, int quantity);
}
