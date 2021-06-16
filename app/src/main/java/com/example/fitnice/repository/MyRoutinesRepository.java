package com.example.fitnice.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fitnice.App;
import com.example.fitnice.api.ApiClient;
import com.example.fitnice.api.ApiMyRoutinesService;
import com.example.fitnice.api.ApiResponse;
import com.example.fitnice.api.ApiRoutineService;
import com.example.fitnice.api.model.PagedList;
import com.example.fitnice.api.model.Routine;

public class MyRoutinesRepository {

    private final ApiMyRoutinesService apiService;

    public MyRoutinesRepository(App application) {
        this.apiService = ApiClient.create(application, ApiMyRoutinesService.class);
    }

    public LiveData<Resource<PagedList<Routine>>> getMyRoutinesSorted(@NonNull String order,@NonNull String direction, int page) {
        return new NetworkBoundResource<PagedList<Routine>, PagedList<Routine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return apiService.getMyRoutines(order,direction, page);
            }
        }.asLiveData();
    }
}
