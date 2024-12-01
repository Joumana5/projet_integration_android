package com.example.projet_integration_android.services;

import com.example.projet_integration_android.dto.ApiResponseDto;
import com.example.projet_integration_android.dto.EmployeeSignupDto;
import com.example.projet_integration_android.dto.LoginDto;
import com.example.projet_integration_android.dto.LoginResponseDto;
import com.example.projet_integration_android.dto.SendResetEmailDto;
import com.example.projet_integration_android.dto.VerifyEmployeeDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/authentication/verify-email")
    Call<ApiResponseDto<String>> verifyEmail(@Body VerifyEmployeeDto verifyEmployeeDto);

    @POST("/authentication/signup")
    Call<ApiResponseDto<String>> signup(@Body EmployeeSignupDto employeeSignup);

    @POST("/authentication/login")
    Call<LoginResponseDto> login(@Body LoginDto loginDto);

    @POST("/authentication/send-password-reset-email")
    Call<LoginResponseDto> sendResetEmail(@Body SendResetEmailDto sendResetEmailDto);

}
