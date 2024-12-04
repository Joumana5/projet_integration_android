package com.example.projet_integration_android.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_integration_android.R;
import com.example.projet_integration_android.account_requests.EmployeeRequests;
import com.example.projet_integration_android.account_requests.ManagerRequests;
import com.example.projet_integration_android.authentication.Login;
import com.example.projet_integration_android.dto.ApiResponseDto;
import com.example.projet_integration_android.dto.profile.ProfileDetails;
import com.example.projet_integration_android.dto.profile.UpdateProfileDto;
import com.example.projet_integration_android.services.ApiService;
import com.example.projet_integration_android.services.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity {

    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText addressInput;
    EditText ageInput;
    EditText roleInput;
    EditText gradeInput;
    Button updateButton;
    Button requestsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);

        firstNameInput = findViewById(R.id.firstName);
        lastNameInput = findViewById(R.id.lastName);
        emailInput = findViewById(R.id.email);
        addressInput = findViewById(R.id.address);
        ageInput = findViewById(R.id.age);
        roleInput = findViewById(R.id.role);
        gradeInput = findViewById(R.id.grade);
        updateButton = findViewById(R.id.updateButton);
        requestsButton = findViewById(R.id.requestsButton);

        gradeInput.setEnabled(false);
        roleInput.setEnabled(false);
        emailInput.setEnabled(false);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String role = sharedPreferences.getString("role", null);

        if (role.equals("ROLE_EMPLOYEE")) {
            requestsButton.setVisibility(View.GONE);
        }

        loadUserData();

        updateButton.setOnClickListener(v -> updateProfile());

        requestsButton.setOnClickListener(v -> {
            if (role.equals("ROLE_MANAGER")) {
                startActivity(new Intent(UserProfile.this, EmployeeRequests.class));
            } else if (role.equals("ROLE_ADMIN")) {
                startActivity(new Intent(UserProfile.this, ManagerRequests.class));
            }

        });

    }

    public void loadUserData() {

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String token = prefs.getString("token", null);
        int userId = prefs.getInt("accountId", -1);
        String role = prefs.getString("role", null);

        Log.v("role", role);

        if (token == null) {
            startActivity(new Intent(UserProfile.this, Login.class));
        }


        else {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            apiService.getUserProfile(userId, token).enqueue(new Callback<ApiResponseDto<ProfileDetails>>() {
                @Override
                public void onResponse(Call<ApiResponseDto<ProfileDetails>> call, Response<ApiResponseDto<ProfileDetails>> response) {
                    if (response.isSuccessful()) {
                        ApiResponseDto<ProfileDetails> data = response.body();

                        firstNameInput.setText(data.getResponseData().getFirstName());
                        lastNameInput.setText(data.getResponseData().getLastName());
                        emailInput.setText(data.getResponseData().getEmail());
                        ageInput.setText(data.getResponseData().getAge());
                        addressInput.setText(data.getResponseData().getAddress());

                        try {
                            if (role.equals("ROLE_EMPLOYEE")) {
                                roleInput.setText(data.getResponseData().getEmployeeRole());
                                gradeInput.setVisibility(View.GONE);
                            } else if (role.equals("ROLE_MANAGER")) {
                                gradeInput.setText(data.getResponseData().getManagerGrade());
                                roleInput.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            Log.e("profile", e.getMessage());
                        }


                    } else {
                        Log.e("UserProfile", "Error: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ApiResponseDto<ProfileDetails>> call, Throwable t) {

                }
            });
        }
    }

    public void updateProfile() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("accountId", -1);
        String token = prefs.getString("token", null);

        UpdateProfileDto updateProfileDto = new UpdateProfileDto(firstNameInput.getText().toString(),
                lastNameInput.getText().toString(),
                Integer.parseInt(ageInput.getText().toString()),
                addressInput.getText().toString(),
                userId);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        apiService.updateProfile(updateProfileDto, token).enqueue(new Callback<ApiResponseDto<String>>() {
            @Override
            public void onResponse(Call<ApiResponseDto<String>> call, Response<ApiResponseDto<String>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("update-profile", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ApiResponseDto<String>> call, Throwable t) {

            }
        });

    }


}