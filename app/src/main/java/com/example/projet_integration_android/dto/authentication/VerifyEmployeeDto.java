package com.example.projet_integration_android.dto.authentication;

public class VerifyEmployeeDto {
    private String email;
    private String code;

    public VerifyEmployeeDto(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }
}