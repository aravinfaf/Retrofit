package com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status")
    public String status;

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("primary_mobile")
    public String primary_mobile;

    @SerializedName("email")
    public String email;
}
