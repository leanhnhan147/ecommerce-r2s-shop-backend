package com.r2s.mockproject.service.impl;

import com.r2s.mockproject.entity.Cart;
import com.r2s.mockproject.entity.CartLineItem;
import com.r2s.mockproject.entity.VariantProduct;
import com.r2s.mockproject.repository.CartLineItemRepository;
import com.r2s.mockproject.service.CartLineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartLineItemServiceImpl implements CartLineItemService {

    @Autowired
    private CartLineItemRepository cartLineItemRepository;

    @Override
    public List<CartLineItem> getAllCartLineItem() {
        return this.cartLineItemRepository.findAll();
    }

}
