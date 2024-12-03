package com.example.projet_integration_android.dto.account_requests;

public class Employee {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String employeeRole;

    public Employee() {
    }

    public Employee(String employeeRole, String email, String lastName, String firstName, Integer id) {
        this.employeeRole = employeeRole;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
