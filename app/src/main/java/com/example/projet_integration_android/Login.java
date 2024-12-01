package com.example.projet_integration_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_integration_android.dto.ApiResponseDto;
import com.example.projet_integration_android.dto.LoginDto;
import com.example.projet_integration_android.dto.LoginResponseDto;
import com.example.projet_integration_android.services.ApiService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    TextInputEditText e1, e2;
    Button b;
    TextView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        e1 = findViewById(R.id.mail);
        e2 = findViewById(R.id.pwd);
        b = findViewById(R.id.login_button);
        v = findViewById(R.id.sign);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = e1.getText().toString();
                String password = e2.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(Login.this, "The field email is empty", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(Login.this, "The field password is empty", Toast.LENGTH_SHORT).show();
                } else {

                    LoginDto loginData = new LoginDto(email, password);
                    ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

                    apiService.login(loginData).enqueue(new Callback<LoginResponseDto>() {

                        @Override
                        public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {

                            if (response.isSuccessful() &&  response.body() != null) {
                                LoginResponseDto loginResponse = response.body();
                                String token = loginResponse.getToken();
                                int accountId = loginResponse.getAccountId();
                                String responseData = loginResponse.getResponseData();
                                String role = loginResponse.getRole();

                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("token", token);
                                editor.putInt("accountId", accountId);
                                editor.putString("responseData", responseData);
                                editor.putString("role", role);
                                editor.apply();

                                startActivity(new Intent(Login.this, UserProfile.class));

                            } else {

                                String errorResponse = null;
                                try {
                                    errorResponse = response.errorBody().string();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                Gson gson = new Gson();
                                LoginResponseDto errorDto = gson.fromJson(errorResponse, LoginResponseDto.class);

                                if (errorDto.getResponseData().equals("You did not verify your email yet, a new verification email has been sent")) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    editor.putString("email", email);
                                    editor.apply();

                                    Intent intent = new Intent(Login.this, VerificationActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(Login.this, errorDto.getResponseData(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponseDto> call, Throwable t) {

                        }
                    });

                }
            }
        });
    }
}