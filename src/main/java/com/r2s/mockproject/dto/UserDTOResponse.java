package com.r2s.mockproject.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.r2s.mockproject.entity.User;
import org.springframework.util.ObjectUtils;


import lombok.Data;

@Data
public class UserDTOResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phone;

    public UserDTOResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }
}
