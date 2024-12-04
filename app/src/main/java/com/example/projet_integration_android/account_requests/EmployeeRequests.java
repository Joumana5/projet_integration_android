package com.example.projet_integration_android.account_requests;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_integration_android.R;
import com.example.projet_integration_android.adapter.EmployeeAdapter;
import com.example.projet_integration_android.dto.account_requests.Employee;
import com.example.projet_integration_android.services.ApiService;
import com.example.projet_integration_android.services.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRequests extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    List<Employee> employeeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_requests);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new EmployeeAdapter(employeeList);
        recyclerView.setAdapter(adapter);

        loadEmployeeRequests();
    }

    public void loadEmployeeRequests() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        String token = getSharedPreferences("MyPrefs", MODE_PRIVATE).getString("token", null);
        apiService.getEmployeeRequests(token).enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                RecyclerView.Adapter adapter = new EmployeeAdapter(response.body());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {

            }
        });
    }

}