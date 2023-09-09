package com.r2s.mockproject.rest;

import com.r2s.mockproject.constants.ResponseCode;
import com.r2s.mockproject.dto.OrderDTOResponse;
import com.r2s.mockproject.dto.ProductDTOResponse;
import com.r2s.mockproject.entity.*;
import com.r2s.mockproject.service.AddressService;
import com.r2s.mockproject.service.OrderService;
import com.r2s.mockproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController extends BaseRestController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @GetMapping("")
    public ResponseEntity<?> getAllOrder(){
        try {
            List<Order> orders = this.orderService.getAllOrder();
            List<OrderDTOResponse> responses = orders.stream()
                    .map(order -> new OrderDTOResponse(order))
                    .collect(Collectors.toList());
            return super.success(responses);
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody(required = true) Map<String, Object> newOrder){
        try{
            if(ObjectUtils.isEmpty(newOrder)
                    || ObjectUtils.isEmpty(newOrder.get("deliverTime"))
                    || ObjectUtils.isEmpty(newOrder.get("userId"))
                    || ObjectUtils.isEmpty(newOrder.get("addressId"))){
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            Long userId = Long.parseLong(newOrder.get("userId").toString());
            User foundUser = this.userService.findUserById(userId);
            if (ObjectUtils.isEmpty(foundUser)) {
                return super.error(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getMessage());
            }

            Long addressId = Long.parseLong(newOrder.get("addressId").toString());
            Address foundAddress = this.addressService.findAddressById(addressId);
            if (ObjectUtils.isEmpty(foundAddress)) {
                return super.error(ResponseCode.ADDRESS_NOT_FOUND.getCode(), ResponseCode.ADDRESS_NOT_FOUND.getMessage());
            }

            Order createdOder = orderService.createOrder(newOrder, foundUser, foundAddress);
            return super.success(new OrderDTOResponse(createdOder));
//            return super.success(createdOder);
        }catch(Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

}
