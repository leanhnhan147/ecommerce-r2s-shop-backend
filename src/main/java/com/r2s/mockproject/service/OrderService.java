package com.r2s.mockproject.service;

import com.r2s.mockproject.entity.Address;
import com.r2s.mockproject.entity.Order;
import com.r2s.mockproject.entity.User;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface OrderService {

    List<Order> getAllOrder();

    Order createOrder(Map<String, Object> newOrder, User user, Address address) throws ParseException;
}
