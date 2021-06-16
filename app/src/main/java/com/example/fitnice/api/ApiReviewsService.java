package com.example.fitnice.api;

import androidx.lifecycle.LiveData;

import com.example.fitnice.api.model.Review;
import com.example.fitnice.api.model.ReviewSend;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiReviewsService {

    @POST("reviews/{routineId}")
    LiveData<ApiResponse<Review>> postReview(@Path("routineId") int routineId, @Body ReviewSend review);
}
