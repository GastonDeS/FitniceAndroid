package com.example.fitnice.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fitnice.App;
import com.example.fitnice.api.ApiClient;
import com.example.fitnice.api.ApiCycleService;
import com.example.fitnice.api.ApiResponse;
import com.example.fitnice.api.model.Cycle;
import com.example.fitnice.api.model.PagedList;

public class CycleRepository {
    private final ApiCycleService apiService;

    public CycleRepository(App app) {
        this.apiService = ApiClient.create(app, ApiCycleService.class);
    }

    public LiveData<Resource<PagedList<Cycle>>> getCycles(Integer id) {
        return new NetworkBoundResource<PagedList<Cycle>, PagedList<Cycle>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Cycle>>> createCall() {
                return apiService.getCycles(id);
            }
        }.asLiveData();
    }
}
