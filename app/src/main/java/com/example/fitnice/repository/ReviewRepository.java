package com.example.fitnice.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fitnice.App;
import com.example.fitnice.api.ApiClient;
import com.example.fitnice.api.ApiResponse;
import com.example.fitnice.api.ApiReviewsService;
import com.example.fitnice.api.model.PagedList;
import com.example.fitnice.api.model.Review;
import com.example.fitnice.api.model.ReviewSend;
import com.example.fitnice.api.model.Routine;

public class ReviewRepository {

    private final ApiReviewsService apiService;

    public ReviewRepository(App application) {
        this.apiService = ApiClient.create(application, ApiReviewsService.class);
    }

    public LiveData<Resource<Review>> postReview(int routineId, ReviewSend body) {
        return new NetworkBoundResource<Review, Review>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Review>> createCall() {
                return apiService.postReview(routineId,body);
            }
        }.asLiveData();
    }
}
