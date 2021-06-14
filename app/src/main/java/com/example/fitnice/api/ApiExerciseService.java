package com.example.fitnice.api;

import androidx.lifecycle.LiveData;

import com.example.fitnice.api.model.ExerciseContent;
import com.example.fitnice.api.model.PagedList;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiExerciseService {

    @GET("cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedList<ExerciseContent>>> getExercises(@Path("cycleId") Integer cycle);

}
