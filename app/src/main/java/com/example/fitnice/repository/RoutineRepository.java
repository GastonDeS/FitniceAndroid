package com.example.fitnice.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fitnice.App;
import com.example.fitnice.api.ApiClient;
import com.example.fitnice.api.ApiResponse;
import com.example.fitnice.api.ApiRoutineService;
import com.example.fitnice.api.model.PagedList;
import com.example.fitnice.api.model.Routine;

public class RoutineRepository {

    private final ApiRoutineService apiService;

    public RoutineRepository(App application) {
        this.apiService = ApiClient.create(application, ApiRoutineService.class);
    }

    public LiveData<Resource<PagedList<Routine>>> getRoutines() {
        return new NetworkBoundResource<PagedList<Routine>, PagedList<Routine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return apiService.getRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> getRoutine(int routineId) {
        return new NetworkBoundResource<Routine, Routine>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Routine>> createCall() {
                return apiService.getRoutine(routineId);
            }
        }.asLiveData();
    }
}
