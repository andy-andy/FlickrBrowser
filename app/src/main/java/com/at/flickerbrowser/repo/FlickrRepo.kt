package com.at.flickerbrowser.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.at.flickerbrowser.api.FlickrWebService
import com.at.flickerbrowser.models.FlickrResponse
import com.at.flickerbrowser.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Repository that handles Flickr feed data
 */
@Singleton
class FlickrRepo @Inject constructor(
        private val appExecutors: AppExecutors,
        private val flickrWebService: FlickrWebService) {

    fun getFlickrFeed(): LiveData<Resource<FlickrResponse>> {
        return object : NetworkBoundResource<FlickrResponse, FlickrResponse>(appExecutors) {

            override fun saveCallResult(item: FlickrResponse) {}

            override fun shouldFetch(data: FlickrResponse?) = true

            override fun loadFromDb(): LiveData<FlickrResponse> {
                val configInfoResponseMLD = MutableLiveData<FlickrResponse>()
                return configInfoResponseMLD
            }

            override fun createCall() = flickrWebService.fetchFlickrFeed()
        }.asLiveData()
    }
}
