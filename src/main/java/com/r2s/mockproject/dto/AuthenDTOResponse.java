package com.r2s.mockproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenDTOResponse {
    private String token;
    private String message;
}
