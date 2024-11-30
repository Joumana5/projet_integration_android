package com.example.projet_integration_android.dto;

public class EmployeeSignupDto {
    private String name;
    private String lastName;
    private int age;
    private String adress;
    private long RIB;
    private String email;
    private String password;

    private String accountType;

    private String role_employer;

    public EmployeeSignupDto(String name, String lastName, int age, String adress, long RIB, String email, String password, String accountType, String role_employer) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.adress = adress;
        this.RIB = RIB;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.role_employer = role_employer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public long getRIB() {
        return RIB;
    }

    public void setRIB(long RIB) {
        this.RIB = RIB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getRole_employer() {
        return role_employer;
    }

    public void setRole_employer(String role_employer) {
        this.role_employer = role_employer;
    }
}