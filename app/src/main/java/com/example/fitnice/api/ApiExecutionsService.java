package com.example.fitnice.api;

import androidx.lifecycle.LiveData;

import com.example.fitnice.api.model.Executions;
import com.example.fitnice.api.model.ExecutionsSend;
import com.example.fitnice.api.model.PagedList;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiExecutionsService {

    @GET("users/current/executions?size=25")
    LiveData<ApiResponse<PagedList<Executions>>> getExecutions( @Query("orderBy") String order, @Query("direction") String direction, @Query("page") int page);

    @POST("executions/{routineId}")
    LiveData<ApiResponse<Executions>> postExecutions(@Path("routineId") int routineId,@Body ExecutionsSend executions);


}
