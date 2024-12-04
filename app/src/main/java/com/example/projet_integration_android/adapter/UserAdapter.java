package com.example.projet_integration_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projet_integration_android.R;
import com.example.projet_integration_android.dto.UserDto;
import com.example.projet_integration_android.services.ApiService;
import com.example.projet_integration_android.services.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<UserDto> userList;
    private Context context;

    public UserAdapter(List<UserDto> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_list, parent, false); // Use the correct layout name here
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserDto user = userList.get(position);
        holder.bindData(user);

        Button deleteButton = holder.itemView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            deleteUser(user.getId(), position);
        });

        Button updateButton = holder.itemView.findViewById(R.id.update_button);
        updateButton.setOnClickListener(v -> {
            UserDto updatedUser = new UserDto();
            updatedUser.setId(user.getId());
            updatedUser.setName("Updated Name");
            updatedUser.setLastName("Updated LastName");
            updatedUser.setEmail("updatedemail@example.com");
            updateUser(user.getId(), updatedUser, position);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void updateUserList(List<UserDto> newList) {
        this.userList = newList;
        notifyDataSetChanged();
    }

    public void fetchUsersFromApi(String token) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<UserDto>> call = apiService.getAllUsersAccepted("Bearer " + token);

        call.enqueue(new Callback<List<UserDto>>() {
            @Override
            public void onResponse(Call<List<UserDto>> call, Response<List<UserDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateUserList(response.body());
                } else {
                    Toast.makeText(context, "Failed to fetch users. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserDto>> call, Throwable t) {
                Toast.makeText(context, "Error fetching users: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteUser(int userId, int position) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<String> call = apiService.deleteUser(userId);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    userList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "User deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to delete user. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Error deleting user: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser(int userId, UserDto updatedUser, int position) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        updatedUser.setId(userId);

        Call<String> call = apiService.updateUser(userId, updatedUser);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    userList.set(position, updatedUser);
                    notifyItemChanged(position);
                    Toast.makeText(context, "User updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to update user. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Error updating user: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
