package com.example.projet_integration_android.dto.account_requests;

public class Employee {
    private String firstName;
    private String lastName;
    private String email;
    private String employeeRole;

    public Employee(String firstName, String lastName, String email, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.employeeRole = role;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getRole() { return employeeRole; }
}
