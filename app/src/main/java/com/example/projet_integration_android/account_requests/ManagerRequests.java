package com.example.projet_integration_android.account_requests;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_integration_android.R;
import com.example.projet_integration_android.adapter.ManagerAdapter;
import com.example.projet_integration_android.dto.account_requests.Manager;
import com.example.projet_integration_android.services.ApiService;
import com.example.projet_integration_android.services.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerRequests extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ManagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manager_requests);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadManagerRequests();

    }

    public void loadManagerRequests() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String token = getSharedPreferences("MyPrefs", MODE_PRIVATE).getString("token", null);
        apiService.getManagerRequests(token).enqueue(new Callback<List<Manager>>() {
            @Override
            public void onResponse(Call<List<Manager>> call, Response<List<Manager>> response) {
                RecyclerView.Adapter adapter = new ManagerAdapter(response.body());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Manager>> call, Throwable t) {

            }
        });
    }

}