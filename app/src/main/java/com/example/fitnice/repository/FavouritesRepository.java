package com.example.fitnice.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fitnice.App;
import com.example.fitnice.api.ApiClient;
import com.example.fitnice.api.ApiFavouritesService;
import com.example.fitnice.api.ApiResponse;
import com.example.fitnice.api.ApiRoutineService;
import com.example.fitnice.api.model.PagedList;
import com.example.fitnice.api.model.Routine;

public class FavouritesRepository {

    private final ApiFavouritesService apiService;

    public FavouritesRepository(App application) {
        this.apiService = ApiClient.create(application, ApiFavouritesService.class);
    }

    public LiveData<Resource<PagedList<Routine>>> getFavourites(int page) {
        return new NetworkBoundResource<PagedList<Routine>, PagedList<Routine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return apiService.getFavourites(page);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> postFav(int id) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.postFav(id);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> removeFav(int id) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.removeFav(id);
            }
        }.asLiveData();
    }
}
