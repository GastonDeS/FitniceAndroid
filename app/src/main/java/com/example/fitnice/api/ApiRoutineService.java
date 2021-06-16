package com.example.fitnice.api;

import androidx.lifecycle.LiveData;

import com.example.fitnice.api.model.PagedList;
import com.example.fitnice.api.model.Routine;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRoutineService {


    @GET("routines")
    LiveData<ApiResponse<PagedList<Routine>>> getRoutines();

    @GET("routines")
    LiveData<ApiResponse<PagedList<Routine>>> getRoutinesSorted(@Query("orderBy") String order, @Query("direction") String direction, @Query("page") int page);

    @GET("routines/{routineId}")
    LiveData<ApiResponse<Routine>> getRoutine(@Path("routineId") int routineId);

}
