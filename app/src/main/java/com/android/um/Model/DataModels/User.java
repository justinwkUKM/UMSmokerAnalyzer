package com.android.um.Model.DataModels;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class User {

    private String username;
    private String email;
    private String password;
    private int age;
    private String gender;
    private ArrayList<Question> questions;


    public User() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void setFirebaseUser(FirebaseUser user)
    {
        this.setUsername(user.getDisplayName());
    }
}
