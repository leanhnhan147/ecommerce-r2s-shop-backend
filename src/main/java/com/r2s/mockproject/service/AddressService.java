package com.r2s.mockproject.service;

import com.r2s.mockproject.entity.Address;
import com.r2s.mockproject.entity.User;

import java.util.List;
import java.util.Map;

public interface AddressService {

    List<Address> getAllAddress();

    Address addAddress(Map<String, Object> newAddress, User user);
}
