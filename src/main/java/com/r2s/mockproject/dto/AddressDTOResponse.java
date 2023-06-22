package com.r2s.mockproject.dto;

import com.r2s.mockproject.entity.Address;
import com.r2s.mockproject.entity.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
@AllArgsConstructor
public class AddressDTOResponse {
    private Long id;
    private String street;
    private String district;
    private String city;
    private String country;

    private Long userId;
    private String userName;
    private String fullName;

    public AddressDTOResponse(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.district = address.getDistrict();
        this.city = address.getCity();
        this.country = address.getCountry();
        if(!ObjectUtils.isEmpty(address.getUser())){
            User user = address.getUser();
            this.userId = user.getId();
            this.userName = user.getUsername();
            this.fullName = user.getFullName();
        }
    }
}
