package com.r2s.mockproject.service.impl;

import com.r2s.mockproject.entity.*;
import com.r2s.mockproject.repository.CartLineItemRepository;
import com.r2s.mockproject.repository.CartRepository;
import com.r2s.mockproject.repository.OrderRepository;
import com.r2s.mockproject.service.CartLineItemService;
import com.r2s.mockproject.service.CartService;
import com.r2s.mockproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartLineItemRepository cartLineItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Order> getAllOrder() {
        return this.orderRepository.findAll();
    }

    @Override
    public Order createOrder(Map<String, Object> newOrder, User user, Address address) throws ParseException{
        Order order = new Order();
        order.setOrderTime(new Date());
        order.setDeleted(false);
        order.setUser(user);
        order.setAddress(address);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        order.setDeliverTime(format.parse(newOrder.get("deliverTime").toString()));

        List<CartLineItem> cartLineItems = user.getCart().getCartLineItems();
        double totalPrice = totalPrice(cartLineItems);
        order.setTotalPrice(totalPrice);

        Cart cart = user.getCart();
        deleteCartLineItemFromCart(cartLineItems, cart);

        return this.orderRepository.save(order);
    }

    private double totalPrice(List<CartLineItem> cartLineItems){
        double totalPrice = 0.0;

        for(CartLineItem cartLineItem : cartLineItems){
            totalPrice += cartLineItem.getQuantity() * cartLineItem.getVariantProduct().getPrice();
        }
        return totalPrice;
    }

    private void  deleteCartLineItemFromCart(List<CartLineItem> cartLineItems, Cart cart ) {
        List<CartLineItem> removedCartLineItems = new ArrayList<>();
        for (CartLineItem cartLineItem : cartLineItems) {
            cartLineItem.setDeleted(true);
            this.cartLineItemRepository.save(cartLineItem);
            removedCartLineItems.add(cartLineItem);
        }
        cartLineItems.removeAll(removedCartLineItems);
        cart.setCartLineItems(cartLineItems);
        this.cartRepository.save(cart);
    }
}
