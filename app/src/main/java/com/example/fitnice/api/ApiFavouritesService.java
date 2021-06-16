package com.example.fitnice.api;

import androidx.lifecycle.LiveData;

import com.example.fitnice.api.model.PagedList;
import com.example.fitnice.api.model.Routine;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiFavouritesService {

    @GET("favourites?size=100")
    LiveData<ApiResponse<PagedList<Routine>>> getFavourites(@Query("page") int page);

    @POST("favourites/{routineId}/")
    LiveData<ApiResponse<Void>> postFav(@Path("routineId") int routineId);

    @DELETE("favourites/{routineId}/")
    LiveData<ApiResponse<Void>> removeFav(@Path("routineId") int routineId);
}