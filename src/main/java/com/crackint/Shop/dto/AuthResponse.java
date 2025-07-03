package com.crackint.Shop.dto;

public class AuthResponse {
    private String token;
    private Long userId;

    public AuthResponse(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }
}
