package com.example.fitnice.api;

import androidx.lifecycle.LiveData;


import com.example.fitnice.api.model.Credentials;
import com.example.fitnice.api.model.Token;
import com.example.fitnice.api.model.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiUserService {
    @POST("users/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("users/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("users/current")
    LiveData<ApiResponse<User>> getCurrentUser();

    @PUT("users/current")
    LiveData<ApiResponse<Void>> updateUser(@Body User user);
}
