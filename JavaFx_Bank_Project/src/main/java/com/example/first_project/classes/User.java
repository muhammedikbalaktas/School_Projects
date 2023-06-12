package com.example._150719652_first_project.classes;

public class User {


    Double balance;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String gender;
    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


    public String getGender() {
        return gender;
    }



    public User(String name, String surname, String email, String password, String gender) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }
}
