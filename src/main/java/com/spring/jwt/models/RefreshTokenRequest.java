package com.spring.jwt.models;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
