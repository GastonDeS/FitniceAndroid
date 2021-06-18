package com.example.fitnice.api;

import androidx.lifecycle.LiveData;

import com.example.fitnice.api.model.PagedList;
import com.example.fitnice.api.model.Routine;

import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiMyRoutinesService {

    @GET("users/current/routines/?size=30")
    LiveData<ApiResponse<PagedList<Routine>>> getMyRoutines(@Query("orderBy") String order, @Query("direction") String directionn, @Query("page") int page);
}
