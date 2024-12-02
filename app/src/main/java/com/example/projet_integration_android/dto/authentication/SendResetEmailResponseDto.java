package com.example.projet_integration_android.dto.authentication;

public class SendResetEmailResponseDto {
    private String responseMessage;

    private String responseData;

    public SendResetEmailResponseDto(String responseMessage, String responseData) {
        this.responseMessage = responseMessage;
        this.responseData = responseData;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}
