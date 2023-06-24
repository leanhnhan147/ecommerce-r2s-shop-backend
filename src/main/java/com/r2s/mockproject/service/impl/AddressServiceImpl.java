package com.r2s.mockproject.service.impl;

import com.r2s.mockproject.entity.Address;
import com.r2s.mockproject.entity.Order;
import com.r2s.mockproject.entity.User;
import com.r2s.mockproject.repository.AddressRepository;
import com.r2s.mockproject.repository.OrderRepository;
import com.r2s.mockproject.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Address findAddressById(Long id) {
        return this.addressRepository.findById(id).orElse(null);
    }

    @Override
    public List<Address> getAllAddress() {
        return this.addressRepository.findAll();
    }

    @Override
    public Address addAddress(Map<String, Object> newAddress, User user) {
        Address address = new Address();
        address.setStreet(newAddress.get("street").toString());
        address.setDistrict(newAddress.get("district").toString());
        address.setCity(newAddress.get("city").toString());
        address.setCountry(newAddress.get("country").toString());
        address.setUser(user);
        return this.addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long id, Map<String, Object> newAddress) {
        Address address = this.findAddressById(id);
        address.setStreet(newAddress.get("street").toString());
        address.setDistrict(newAddress.get("district").toString());
        address.setCity(newAddress.get("city").toString());
        address.setCountry(newAddress.get("country").toString());
        return this.addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = this.findAddressById(id);
        List<Order> orders = address.getOrders();
        for(Order order : orders){
            order.setAddress(null);
            this.orderRepository.save(order);
        }
        address.setOrders(orders);
        this.addressRepository.save(address);
        this.addressRepository.deleteById(id);
    }
}
