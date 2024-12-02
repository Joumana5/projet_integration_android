package com.example.projet_integration_android.dto.profile;

public class UpdateProfileDto {
    private String firstName;
    private String lastName;
    private int age;
    private String adress;
    private int userId;

    public UpdateProfileDto(String firstName, String lastName, int age, String adress, int userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.userId = userId;
        this.adress = adress;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
