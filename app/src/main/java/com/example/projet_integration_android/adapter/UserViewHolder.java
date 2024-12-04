package com.example.projet_integration_android.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_integration_android.R;
import com.example.projet_integration_android.dto.UserDto;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public TextView firstName;
    public TextView lastName;
    public TextView email;
    public TextView address;
    public TextView age;
    public Button deleteButton;
    public Button updateButton;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        firstName = itemView.findViewById(R.id.firstName);
        lastName = itemView.findViewById(R.id.lastName);
        email = itemView.findViewById(R.id.email);
        address = itemView.findViewById(R.id.address);
        age = itemView.findViewById(R.id.age);
        deleteButton = itemView.findViewById(R.id.delete_button);
        updateButton = itemView.findViewById(R.id.update_button);
    }

    public void bindData(UserDto user) {
        firstName.setText(user.getName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        address.setText(user.getAdress());
        age.setText(String.valueOf(user.getAge()));


    }

}
