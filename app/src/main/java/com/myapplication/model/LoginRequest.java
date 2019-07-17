package com.myapplication.model;

public class LoginRequest {

    String phone,firebasetoken;

    public LoginRequest(String phone, String firebasetoken) {
        this.phone = phone;
        this.firebasetoken = firebasetoken;
    }
}
