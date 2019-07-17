package com.myapplication.utils;

import com.myapplication.model.LoginRequest;
import com.myapplication.model.LoginResponse;
import com.myapplication.model.MultipleResource;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    @GET("/api/unknown")
    Call<MultipleResource> doGetListResource();

    @POST("customerapp/customerlogin")
    Call<LoginResponse> loginresponse(@Body LoginRequest loginRequest);

}
