package com.example.projet_integration_android.dto;

public class SendResetEmailDto {
    private String email;

    public SendResetEmailDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
