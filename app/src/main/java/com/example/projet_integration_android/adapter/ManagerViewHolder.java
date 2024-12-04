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
import com.example.projet_integration_android.dto.account_requests.Manager;
import com.example.projet_integration_android.services.ApiService;
import com.example.projet_integration_android.services.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerViewHolder extends RecyclerView.ViewHolder {
    public TextView firstName, lastName, email, role;
    public ImageButton check, cancel;

    public ManagerViewHolder(@NonNull View itemView) {
        super(itemView);

        firstName = itemView.findViewById(R.id.firstName);
        lastName = itemView.findViewById(R.id.lastName);
        email = itemView.findViewById(R.id.email);
        role = itemView.findViewById(R.id.role);
        check = itemView.findViewById(R.id.check);
        cancel = itemView.findViewById(R.id.cancel);
    }

    public void bind(Manager manager, List<Manager> managerList, ManagerAdapter adapter) {
        if (manager == null) return;
        firstName.setText(manager.getFirstName());
        lastName.setText(manager.getLastName());
        email.setText(manager.getEmail());
        role.setText(manager.getGrade());

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        check.setOnClickListener(v -> handleManagerAction(
                manager.getId(),
                true,
                null,
                apiService,
                managerList,
                adapter
        ));

        cancel.setOnClickListener(v -> {
            String rejectionReason = "Account rejected by admin";
            handleManagerAction(
                    manager.getId(),
                    false,
                    rejectionReason,
                    apiService,
                    managerList,
                    adapter
            );
        });
    }

    private void handleManagerAction(
            int accountId,
            boolean isAccepted,
            String rejectionReason,
            ApiService apiService,
            List<Manager> managerList,
            ManagerAdapter adapter
    ) {
        AccountRequestDto requestDto = new AccountRequestDto();
        requestDto.setAccountId(accountId);
        requestDto.setAccountAccepted(isAccepted);
        requestDto.setRejectionReason(rejectionReason);

        Context context = itemView.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token == null || token.isEmpty()) {
            Toast.makeText(context, "Token non disponible. Veuillez vous reconnecter.", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.handleManager(token, requestDto).enqueue(new Callback<ApiResponseDto<String>>() {
            @Override
            public void onResponse(Call<ApiResponseDto<String>> call, Response<ApiResponseDto<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(context, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        managerList.remove(position);
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
