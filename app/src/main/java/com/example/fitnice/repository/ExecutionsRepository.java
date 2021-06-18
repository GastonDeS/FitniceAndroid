package com.example.fitnice.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fitnice.App;
import com.example.fitnice.api.ApiClient;
import com.example.fitnice.api.ApiExecutionsService;
import com.example.fitnice.api.ApiResponse;
import com.example.fitnice.api.model.Executions;
import com.example.fitnice.api.model.ExecutionsSend;
import com.example.fitnice.api.model.PagedList;

public class ExecutionsRepository {

    private final ApiExecutionsService apiService;

    public ExecutionsRepository(App app) {
        apiService = ApiClient.create(app, ApiExecutionsService.class);
    }

    public LiveData<Resource<PagedList<Executions>>> getExecutions(@NonNull String order, @NonNull String direction, int page) {
        return new NetworkBoundResource<PagedList<Executions>, PagedList<Executions>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Executions>>> createCall() {
                return apiService.getExecutions(order,direction, page);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Executions>> postExecution( int routineId,ExecutionsSend executions) {
        return new NetworkBoundResource<Executions, Executions>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Executions>> createCall() {
                return apiService.postExecutions(routineId,executions);
            }
        }.asLiveData();
    }
}
