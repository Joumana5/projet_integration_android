package com.example.projet_integration_android.dto.profile;

public class ProfileDetails {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String age;
    private String employeeRole;
    private String managerGrade;

    public ProfileDetails(String firstName, String lastName, String email, String age, String employeeRole, String managerGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.employeeRole = employeeRole;
        this.managerGrade = managerGrade;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getManagerGrade() {
        return managerGrade;
    }

    public void setManagerGrade(String managerGrade) {
        this.managerGrade = managerGrade;
    }

    public String getAddress() {
        return address;
    }
}
