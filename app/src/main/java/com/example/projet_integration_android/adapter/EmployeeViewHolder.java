package com.example.projet_integration_android.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_integration_android.R;
import com.example.projet_integration_android.dto.ApiResponseDto;
import com.example.projet_integration_android.dto.account_requests.AccountRequestDto;
import com.example.projet_integration_android.dto.account_requests.Employee;
import com.example.projet_integration_android.services.ApiService;
import com.example.projet_integration_android.services.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeViewHolder extends RecyclerView.ViewHolder {
    public TextView firstName, lastName, email, role;
    public ImageButton check, cancel;

    public EmployeeViewHolder(@NonNull View itemView) {
        super(itemView);


        firstName = itemView.findViewById(R.id.firstName);
        lastName = itemView.findViewById(R.id.lastName);
        email = itemView.findViewById(R.id.email);
        role = itemView.findViewById(R.id.role);
        check = itemView.findViewById(R.id.check);
        cancel = itemView.findViewById(R.id.cancel);
    }

    public void bind(Employee employee, List<Employee> employeeList, EmployeeAdapter adapter) {
        if (employee == null) return;
        firstName.setText(employee.getFirstName());
        lastName.setText(employee.getLastName());
        email.setText(employee.getEmail());
        role.setText(employee.getRole());

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        check.setOnClickListener(v -> handleEmployeeAction(
                employee.getId(),
                true,
                null,
                apiService,
                employeeList,
                adapter
        ));

        cancel.setOnClickListener(v -> {
            String rejectionReason = "Account rejected by admin";
            handleEmployeeAction(
                    employee.getId(),
                    false,
                    rejectionReason,
                    apiService,
                    employeeList,
                    adapter
            );
        });
    }

    private void handleEmployeeAction(
            int accountId,
            boolean isAccepted,
            String rejectionReason,
            ApiService apiService,
            List<Employee> employeeList,
            EmployeeAdapter adapter
    ) {
        // Préparation de la requête
        AccountRequestDto requestDto = new AccountRequestDto();
        requestDto.setAccountId(accountId);
        requestDto.setAccountAccepted(isAccepted);
        requestDto.setRejectionReason(rejectionReason);

        // Récupération du token depuis SharedPreferences
        Context context = itemView.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        // Vérification de la présence du token
        if (token == null || token.isEmpty()) {
            Toast.makeText(context, "Token non disponible. Veuillez vous reconnecter.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Appel à l'API
        apiService.handleEmployee(token, requestDto).enqueue(new Callback<ApiResponseDto<String>>() {
            @Override
            public void onResponse(Call<ApiResponseDto<String>> call, Response<ApiResponseDto<String>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Toast.makeText(context, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        employeeList.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                } else {
                    String errorMessage = (response.errorBody() != null)
                            ? response.message()
                            : "Erreur inattendue";
                    Toast.makeText(context, "Erreur lors du traitement : " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseDto<String>> call, Throwable t) {

                Toast.makeText(context, "Échec de l'appel réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
