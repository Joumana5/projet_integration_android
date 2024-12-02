package com.example.projet_integration_android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_integration_android.R;
import com.example.projet_integration_android.dto.account_requests.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private List<Employee> employeeList;

    public EmployeeAdapter(List<Employee> userList) {
        this.employeeList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_table_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee user = employeeList.get(position);
        holder.firstName.setText(user.getFirstName());
        holder.lastName.setText(user.getLastName());
        holder.email.setText(user.getEmail());
        holder.role.setText(user.getRole());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView firstName, lastName, email, role;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            email = itemView.findViewById(R.id.email);
            role = itemView.findViewById(R.id.role);
        }
    }
}
