package com.example.projet_integration_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projet_integration_android.dto.EmployeeRequestDto;
import com.example.projet_integration_android.services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAccountRequests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_account_requests);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        if (token == null) {
            startActivity(new Intent(this, Login.class));
        } else {
            fetchEmployeeRequests();
        }

    }

    public void fetchEmployeeRequests() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        Log.v("token", token);
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        apiService.getEmployeeRequests(token).enqueue(new Callback<List<EmployeeRequestDto>>() {
            @Override
            public void onResponse(Call<List<EmployeeRequestDto>> call, Response<List<EmployeeRequestDto>> response) {
                if (response.isSuccessful()) {
                    List<EmployeeRequestDto> employeeRequests = response.body();
                    // Handle the employee requests data here
                    Log.v("requests", employeeRequests.toString());
                } else {
                    Log.e("requests", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeRequestDto>> call, Throwable t) {

            }
        });
    }



}