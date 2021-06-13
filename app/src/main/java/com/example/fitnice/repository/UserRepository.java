package com.example.fitnice.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fitnice.App;
import com.example.fitnice.api.ApiClient;
import com.example.fitnice.api.ApiResponse;
import com.example.fitnice.api.ApiUserService;
import com.example.fitnice.api.model.Credentials;
import com.example.fitnice.api.model.Token;
import com.example.fitnice.api.model.User;

public class UserRepository {

    private final ApiUserService apiService;

    public UserRepository(App app) {
        this.apiService = ApiClient.create(app, ApiUserService.class);
    }

    public LiveData<Resource<Token>> login(Credentials credentials) {
        return new NetworkBoundResource<Token, Token>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Token>> createCall() {
                return apiService.login(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<User>> getCurrentUser() {
        return new NetworkBoundResource<User, User>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return apiService.getCurrentUser();
            }
        }.asLiveData();
    }
}