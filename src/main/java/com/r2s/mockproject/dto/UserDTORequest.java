package com.r2s.mockproject.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTORequest {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;

    public UserDTORequest(Map<String, Object> user) {
//        this.id = Long.parseLong(user.get("id").toString());
        this.username = user.get("username").toString();
        this.password = user.get("password").toString();
        this.fullName = user.get("fullName").toString();
        this.email = user.get("email").toString();
        this.phone = user.get("phone").toString();
    }
}
