package com.example.fitnice.api;

import androidx.lifecycle.LiveData;

import com.example.fitnice.api.model.Cycle;
import com.example.fitnice.api.model.PagedList;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiCycleService {

    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedList<Cycle>>> getCycles(@Path("routineId") Integer routineId);
}
