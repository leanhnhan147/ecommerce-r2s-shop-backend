package com.r2s.mockproject.service.impl;

import com.r2s.mockproject.entity.Cart;
import com.r2s.mockproject.entity.CartLineItem;
import com.r2s.mockproject.entity.VariantProduct;
import com.r2s.mockproject.repository.CartLineItemRepository;
import com.r2s.mockproject.repository.CartRepository;
import com.r2s.mockproject.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartLineItemRepository cartLineItemRepository;

    @Override
    public Cart findCartById(Long id) {
        return this.cartRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cart> getAllCart() {
        return this.cartRepository.findAll();
    }

    @Override
    public Cart addVariantProductToCart(Long cartId, VariantProduct variantProduct, int quantity) {
        Cart cart = this.findCartById(cartId);

        List<CartLineItem> cartItems = cart.getCartLineItems();
        CartLineItem cartItem = findCartItem(cartItems, variantProduct.getId());
        if (ObjectUtils.isEmpty(cartItems)) {
            cartItems = new ArrayList<>();
            if (cartItem == null) {
                cartItem = new CartLineItem();
                cartItem.setVariantProduct(variantProduct);
//                cartItem.setTotalPrice(quantity * variantProduct.getPrice());
                cartItem.setQuantity(quantity);
                cartItem.setDeleted(false);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                cartLineItemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItem = new CartLineItem();
                cartItem.setVariantProduct(variantProduct);
//                cartItem.setTotalPrice(quantity * variantProduct.getPrice());
                cartItem.setQuantity(quantity);
                cartItem.setDeleted(false);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                cartLineItemRepository.save(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
//                cartItem.setTotalPrice(cartItem.getTotalPrice() + ( quantity * variantProduct.getPrice()));
                cartLineItemRepository.save(cartItem);
            }
        }
        cart.setCartLineItems(cartItems);

//        int totalItems = totalItems(cart.getCartItem());
//        double totalPrice = totalPrice(cart.getCartItem());
//
//        cart.setTotalPrices(totalPrice);
//        cart.setTotalItems(totalItems);

        return this.cartRepository.save(cart);
    }

    private CartLineItem findCartItem(List<CartLineItem> cartItems, Long variantProductId) {
        if (cartItems == null) {
            return null;
        }

        CartLineItem cartItem = null;
        for (CartLineItem item : cartItems) {
            if (item.getVariantProduct().getId() == variantProductId) {
                cartItem = item;
            }
        }
        return cartItem;
    }
}
