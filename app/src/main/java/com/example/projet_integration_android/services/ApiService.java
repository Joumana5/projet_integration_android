package com.example.projet_integration_android.services;

import com.example.projet_integration_android.UserProfile;
import com.example.projet_integration_android.dto.ApiResponseDto;
import com.example.projet_integration_android.dto.ChangePasswordDto;
import com.example.projet_integration_android.dto.EmployeeRequestDto;
import com.example.projet_integration_android.dto.EmployeeSignupDto;
import com.example.projet_integration_android.dto.LoginDto;
import com.example.projet_integration_android.dto.LoginResponseDto;
import com.example.projet_integration_android.dto.ProfileDetails;
import com.example.projet_integration_android.dto.SendResetEmailDto;
import com.example.projet_integration_android.dto.SendResetEmailResponseDto;
import com.example.projet_integration_android.dto.UpdateProfileDto;
import com.example.projet_integration_android.dto.VerifyEmployeeDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/authentication/verify-email")
    Call<ApiResponseDto<String>> verifyEmail(@Body VerifyEmployeeDto verifyEmployeeDto);

    @POST("/authentication/signup")
    Call<ApiResponseDto<String>> signup(@Body EmployeeSignupDto employeeSignup);

    @POST("/authentication/login")
    Call<LoginResponseDto> login(@Body LoginDto loginDto);

    @POST("/authentication/send-password-reset-email")
    Call<SendResetEmailResponseDto> sendResetEmail(@Body SendResetEmailDto sendResetEmailDto);

    @POST("/authentication/reset-password")
    Call<SendResetEmailResponseDto> resetPassword(@Body ChangePasswordDto changePasswordDto);

    @GET("/profile/details/{userId}")
    Call<ApiResponseDto<ProfileDetails>> getUserProfile(@Path("userId") int id, @Header("Authorization") String token);

    @PUT("/profile/update")
    Call<ApiResponseDto<String>> updateProfile(@Body UpdateProfileDto profileDetails, @Header("Authorization") String token);

    @GET("/account-requests/employees")
    Call<List<EmployeeRequestDto>> getEmployeeRequests(@Header("Authorization") String token);

}
