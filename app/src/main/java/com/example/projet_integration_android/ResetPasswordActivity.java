package com.example.projet_integration_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_integration_android.dto.ApiResponseDto;
import com.example.projet_integration_android.dto.LoginResponseDto;
import com.example.projet_integration_android.dto.SendResetEmailDto;
import com.example.projet_integration_android.dto.VerifyEmployeeDto;
import com.example.projet_integration_android.services.ApiService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText emailInput;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        
        emailInput = findViewById(R.id.emailInput);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            sendVerificationEmail(email);
        });
    }

    public void sendVerificationEmail(String email) {
        if (email.isEmpty()) {
            Toast.makeText(ResetPasswordActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
        } else {
            SendResetEmailDto sendResetEmail = new SendResetEmailDto(email);
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            apiService.sendResetEmail(sendResetEmail).enqueue(new Callback<LoginResponseDto>() {
                @Override
                public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {
                    LoginResponseDto responseData = response.body();
                    if (response.isSuccessful()) {
                        Toast.makeText(ResetPasswordActivity.this, "Email has been sent", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<LoginResponseDto> call, Throwable t) {

                }
            });
        }
    }

    



}
