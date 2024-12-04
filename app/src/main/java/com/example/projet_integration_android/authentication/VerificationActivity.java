package com.example.projet_integration_android.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_integration_android.R;
import com.example.projet_integration_android.dto.ApiResponseDto;
import com.example.projet_integration_android.dto.authentication.VerifyEmployeeDto;
import com.example.projet_integration_android.services.ApiService;
import com.example.projet_integration_android.services.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity {
    private EditText codeInput;
    private Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        codeInput = findViewById(R.id.codeInput);
        verifyButton = findViewById(R.id.verifyButton);

        verifyButton.setOnClickListener(v -> {
            String code = codeInput.getText().toString();
            verifyEmail(code);
        });
    }

    private void verifyEmail(String code) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "none");

        Log.v("signup", "Email to be verified : " + email);

        if (email.equals("none")) {
            Intent intent = new Intent(VerificationActivity.this, Signup.class);
            startActivity(intent);

        } else {
            VerifyEmployeeDto verificationData = new VerifyEmployeeDto(email, code);

            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            apiService.verifyEmail(verificationData).enqueue(new Callback<ApiResponseDto<String>>() {
                @Override
                public void onResponse(Call<ApiResponseDto<String>> call, Response<ApiResponseDto<String>> response) {
                    try {
                        Log.v("signup", "code is " + code);
                        ApiResponseDto<String> apiResponse = response.body();
                        if (response.isSuccessful() && apiResponse != null) {
                            Toast.makeText(VerificationActivity.this, "Email verification success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(VerificationActivity.this, Login.class);
                            startActivity(intent);

                        } else {
                            Log.e("signup","HTTP Error: " + response.code() + " " + response.message());
                            String toastMessage;
                            if (response.code() == 401) toastMessage = "Verification code is incorrect";
                            else if (response.code() == 400) toastMessage = "Email is already verified";
                            else toastMessage = "Error verifying your email";
                            Toast.makeText(VerificationActivity.this, toastMessage, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Log.e("verification", e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ApiResponseDto<String>> call, Throwable t) {
                    if (t instanceof IOException) {
                        Log.e("SignupActivity", "Network error: " + t.getMessage());
                    } else {
                        Log.e("SignupActivity", "Unexpected error: " + t.getMessage(), t);
                    }
                }
            });
            
            
            
        }

    }




}
