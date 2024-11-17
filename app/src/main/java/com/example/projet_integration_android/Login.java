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
import com.example.projet_integration_android.services.ApiService;
import com.google.android.material.textfield.TextInputEditText;

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

                    apiService.login(loginData).enqueue(new Callback<ApiResponseDto<String>>() {
                        @Override
                        public void onResponse(Call<ApiResponseDto<String>> call, Response<ApiResponseDto<String>> response) {
                            if (response.isSuccessful()) {
                                String responseMessage = response.body().getResponseMessage();
                                Log.v("signup", responseMessage);
                                if (responseMessage.equals("SUCCESS")) Toast.makeText(Login.this, "Login success" , Toast.LENGTH_SHORT).show();
                                else if (responseMessage.equals("EMAIL_NOT_VERIFIED")) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email", email);
                                    editor.apply();

                                    Intent intent = new Intent(Login.this, VerificationActivity.class);
                                    startActivity(intent);
                                } else if (responseMessage.equals("ACCOUNT_REJECTED")) {
                                    Toast.makeText(Login.this, "Account rejected", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login.this, "Account still pending verification", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                if (response.code() == 401)
                                    Toast.makeText(Login.this, "Username or password incorrect", Toast.LENGTH_SHORT).show();
                                else Log.e("signup", "Error : " + response.code());
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
        });
    }
}