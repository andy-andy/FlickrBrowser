package com.at.flickerbrowser.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.at.flickerbrowser.models.FlickrResponse;
import com.at.flickerbrowser.repo.FlickrRepo;
import com.at.flickerbrowser.repo.Resource;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private final FlickrRepo mFlickrRepo;

    @Inject
    public MainViewModel(FlickrRepo flickrRepo) {
        mFlickrRepo = flickrRepo;
    }

    // TODO: Implement the ViewModel
    public LiveData<Resource<FlickrResponse>> getFlickrFeed() {
        return mFlickrRepo.getFlickrFeed();
    }

}
