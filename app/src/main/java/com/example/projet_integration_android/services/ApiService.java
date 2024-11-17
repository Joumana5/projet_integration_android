package com.example.projet_integration_android.services;

import com.example.projet_integration_android.dto.ApiResponseDto;
import com.example.projet_integration_android.dto.EmployeeSignupDto;
import com.example.projet_integration_android.dto.LoginDto;
import com.example.projet_integration_android.dto.VerifyEmployeeDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/Employee/verify-email")
    Call<ApiResponseDto<String>> verifyEmail(@Body VerifyEmployeeDto verifyEmployeeDto);

    @POST("/Employee/signup")
    Call<ApiResponseDto<String>> signup(@Body EmployeeSignupDto employeeSignup);

    @POST("/Employee/login")
    Call<ApiResponseDto<String>> login(@Body LoginDto loginDto);


}
