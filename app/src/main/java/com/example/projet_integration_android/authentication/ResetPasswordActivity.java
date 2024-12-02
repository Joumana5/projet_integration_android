package com.example.projet_integration_android.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_integration_android.R;
import com.example.projet_integration_android.dto.authentication.SendResetEmailDto;
import com.example.projet_integration_android.dto.authentication.SendResetEmailResponseDto;
import com.example.projet_integration_android.services.ApiService;
import com.example.projet_integration_android.services.RetrofitClient;

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
            sendPasswordResetEmail(email);
        });
    }

    public void sendPasswordResetEmail(String email) {
        if (email.isEmpty()) {
            Toast.makeText(ResetPasswordActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
        } else {
            SendResetEmailDto sendResetEmail = new SendResetEmailDto(email);
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            apiService.sendResetEmail(sendResetEmail).enqueue(new Callback<SendResetEmailResponseDto>() {
                @Override
                public void onResponse(Call<SendResetEmailResponseDto> call, Response<SendResetEmailResponseDto> response) {
                    SendResetEmailResponseDto data = response.body();
                    if (response.isSuccessful() && data != null) {
                        Toast.makeText(ResetPasswordActivity.this, data.getResponseMessage(), Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("email", email);
                        editor.apply();

                        Intent intent = new Intent(ResetPasswordActivity.this, ChangePasswordActivity.class);
                        startActivity(intent);

                    } else {
                        if (response.code() == 404) {
                            Toast.makeText(ResetPasswordActivity.this, "Did not find any account with given email", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<SendResetEmailResponseDto> call, Throwable t) {

                }
            });
        }
    }

    



}
