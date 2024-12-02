package com.example.projet_integration_android.dto.authentication;

public class LoginDto {
    private String email;
    private String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
