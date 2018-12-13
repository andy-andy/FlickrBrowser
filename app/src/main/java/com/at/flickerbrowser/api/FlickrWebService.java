package com.at.flickerbrowser.api;

import android.arch.lifecycle.LiveData;

import com.at.flickerbrowser.models.FlickrResponse;
import com.at.flickerbrowser.repo.ApiResponse;

import retrofit2.http.POST;

public interface FlickrWebService {

    @POST("services/feeds/photos_public.gne?format=json")
    LiveData<ApiResponse<FlickrResponse>> fetchFlickrFeed();

}
