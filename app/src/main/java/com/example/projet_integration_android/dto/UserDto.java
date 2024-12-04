package com.example.projet_integration_android.dto;

import java.util.Objects;

public class UserDto {
    private int id;
    private String name;
    private String lastName;
    private Integer age;
    private String adress;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDto() {
    }

    public UserDto(int id, String name, String lastName, Integer age, String adress, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.adress = adress;
        this.email = email;
    }

    public UserDto(String name, String lastName, Integer age, String adress, String email) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.adress = adress;
        this.email = email;
    }

    // Getters et Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getAdress() { return adress; }
    public void setAdress(String adress) { this.adress = adress; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Méthode toString
    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", adress='" + adress + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    // Méthode equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(name, userDto.name) &&
                Objects.equals(lastName, userDto.lastName) &&
                Objects.equals(age, userDto.age) &&
                Objects.equals(adress, userDto.adress) &&
                Objects.equals(email, userDto.email);
    }

    // Méthode hashCode
    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, age, adress, email);
    }
}
