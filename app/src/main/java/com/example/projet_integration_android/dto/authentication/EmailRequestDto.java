package com.example.projet_integration_android.dto.authentication;

public class EmailRequestDto {
    private String email;

    public EmailRequestDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}