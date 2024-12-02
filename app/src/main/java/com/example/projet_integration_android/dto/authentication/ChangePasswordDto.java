package com.example.projet_integration_android.dto.authentication;

public class ChangePasswordDto {
    private String email;
    private String newPassword;
    private String resetCode;

    public ChangePasswordDto(String email, String newPassword, String resetCode) {
        this.email = email;
        this.newPassword = newPassword;
        this.resetCode = resetCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }
}
