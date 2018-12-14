package com.at.flickerbrowser.repo

import android.arch.lifecycle.LiveData
import com.at.flickerbrowser.api.FlickrWebService
import com.at.flickerbrowser.db.FlickrResponseDao
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
        private val flickrWebService: FlickrWebService,
        private val flickrResponseDao: FlickrResponseDao) {

    fun getFlickrFeed(): LiveData<Resource<FlickrResponse>> {
        return object : NetworkBoundResource<FlickrResponse, FlickrResponse>(appExecutors) {

            override fun saveCallResult(item: FlickrResponse) {
                flickrResponseDao.deleteAll()
                flickrResponseDao.save(item)
            }

            override fun shouldFetch(data: FlickrResponse?) = true

            override fun loadFromDb() = flickrResponseDao.loadFlickrResponse()

            override fun createCall() = flickrWebService.fetchFlickrFeed()
        }.asLiveData()
    }
}
