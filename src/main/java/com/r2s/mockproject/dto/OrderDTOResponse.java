package com.r2s.mockproject.dto;

import com.r2s.mockproject.entity.Address;
import com.r2s.mockproject.entity.Order;
import com.r2s.mockproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Data
@AllArgsConstructor
public class OrderDTOResponse {
    private String fullName;
    private String phone;
    private String address;

    private Long orderId;
    private Date orderTime;
    private Date deliverTime;
    private double totalPrice;


    public OrderDTOResponse(Order order){
        this.orderId = order.getId();
        this.orderTime = order.getOrderTime();
        this.deliverTime = order.getDeliverTime();
        this.totalPrice = order.getTotalPrice();

        if(!ObjectUtils.isEmpty(order.getUser())){
            User user = order.getUser();
            this.fullName = user.getFullName();
            this.phone = user.getPhone();
        }

        if(!ObjectUtils.isEmpty(order.getAddress())){
            Address fullAddress = order.getAddress();
            this.address = fullAddress.getStreet() + ", " + fullAddress.getDistrict() + ", "
                    + fullAddress.getCity() + ", " + fullAddress.getCountry();
        }

    }
}
