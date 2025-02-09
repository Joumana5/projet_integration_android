package com.example.projet_integration_android.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.projet_integration_android.R;
import com.example.projet_integration_android.dto.authentication.ChangePasswordDto;
import com.example.projet_integration_android.dto.authentication.SendResetEmailResponseDto;
import com.example.projet_integration_android.services.ApiService;
import com.example.projet_integration_android.services.RetrofitClient;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.projet_integration_android.databinding.ActivityChangePasswordBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityChangePasswordBinding binding;

    private EditText codeInput;
    private EditText passwordInput;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        codeInput = findViewById(R.id.codeInput);
        passwordInput = findViewById(R.id.passwordInput);
        resetButton = findViewById(R.id.changeButton);

        resetButton.setOnClickListener(v -> {
            changePassword();
        });

    }

    public void changePassword() {
        String code = codeInput.getText().toString();
        String password = passwordInput.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        ChangePasswordDto changePasswordDto = new ChangePasswordDto(email, password, code);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        apiService.resetPassword(changePasswordDto).enqueue(new Callback<SendResetEmailResponseDto>() {
            @Override
            public void onResponse(Call<SendResetEmailResponseDto> call, Response<SendResetEmailResponseDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    startActivity(new Intent(ChangePasswordActivity.this, Login.class));
                } else {
                    if (response.code() == 404) {
                        Toast.makeText(ChangePasswordActivity.this, "Did not find any account with given email", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {
                        Toast.makeText(ChangePasswordActivity.this, "Invalid reset code", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<SendResetEmailResponseDto> call, Throwable t) {

            }
        });

    }


}