package com.example.projet_integration_android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_integration_android.R;
import com.example.projet_integration_android.dto.account_requests.Manager;

import java.util.ArrayList;
import java.util.List;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerViewHolder> {

    private List<Manager> managerList;

    public ManagerAdapter(List<Manager> managerList) {
        this.managerList = (managerList != null) ? managerList : new ArrayList<>();
    }

    @NonNull
    @Override
    public ManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerViewHolder holder, int position) {
        Manager manager = managerList.get(position);
        holder.firstName.setText(manager.getFirstName());
        holder.lastName.setText(manager.getLastName());
        holder.email.setText(manager.getEmail());
        holder.role.setText(manager.getGrade());
        holder.bind(managerList.get(position), managerList, this);
    }

    @Override
    public int getItemCount() {
        return (managerList != null) ? managerList.size() : 0;
    }
}
