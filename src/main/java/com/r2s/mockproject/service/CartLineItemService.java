package com.r2s.mockproject.service;

import com.r2s.mockproject.entity.*;

import java.util.List;
import java.util.Map;

public interface CartLineItemService {

    List<CartLineItem> getAllCartLineItem();

//    CartLineItem addCartLineItem(Map<String, Object> newCartLineItem, Cart cart, VariantProduct variantProduct);
}
