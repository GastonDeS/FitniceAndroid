package com.example.fitnice.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fitnice.App;
import com.example.fitnice.api.ApiClient;
import com.example.fitnice.api.ApiExerciseService;
import com.example.fitnice.api.ApiResponse;
import com.example.fitnice.api.model.ExerciseContent;
import com.example.fitnice.api.model.PagedList;

public class ExerciseRepository {
    private final ApiExerciseService apiService;

    public ExerciseRepository(App app) {
        this.apiService = ApiClient.create(app, ApiExerciseService.class);
    }

    public LiveData<Resource<PagedList<ExerciseContent>>> getExercises(Integer id) {
        return new NetworkBoundResource<PagedList<ExerciseContent>, PagedList<ExerciseContent>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<ExerciseContent>>> createCall() {
                return apiService.getExercises(id);
            }
        }.asLiveData();
    }
}
