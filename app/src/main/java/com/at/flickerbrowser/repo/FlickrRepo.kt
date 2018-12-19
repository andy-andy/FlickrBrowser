package com.at.flickerbrowser.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.at.flickerbrowser.api.FlickrWebService
import com.at.flickerbrowser.db.FlickrItemDao
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
        private val flickrResponseDao: FlickrResponseDao,
        private val flickrItemDao: FlickrItemDao) {

    fun getFlickrFeed(): LiveData<Resource<FlickrResponse>> {
        return object : NetworkBoundResource<FlickrResponse, FlickrResponse>(appExecutors) {

            override fun saveCallResult(item: FlickrResponse) {
                flickrResponseDao.deleteAll()
                flickrItemDao.deleteAll()
                flickrResponseDao.save(item)
                flickrItemDao.save(item.items)
            }

            override fun shouldFetch(data: FlickrResponse?) = true

            override fun loadFromDb(): LiveData<FlickrResponse> {
                val flickrResponseMLD = MutableLiveData<FlickrResponse>()
                appExecutors.diskIO().execute {
                    val flickrResponse = FlickrResponse()
                    if (flickrResponseDao.loadFlickrResponse() != null
                            && flickrItemDao.loadFlickrItems() != null) {
                        flickrResponse.items = flickrItemDao.loadFlickrItems()
                        flickrResponse.title = flickrResponseDao.loadFlickrResponse().title
                        flickrResponse.link = flickrResponseDao.loadFlickrResponse().link
                        flickrResponse.description = flickrResponseDao.loadFlickrResponse().description
                        flickrResponse.modified = flickrResponseDao.loadFlickrResponse().modified
                        flickrResponse.generator = flickrResponseDao.loadFlickrResponse().generator

                        appExecutors.mainThread().execute {
                            flickrResponseMLD.value = flickrResponse
                        }
                    } else {
                        appExecutors.mainThread().execute {
                            flickrResponseMLD.value = null
                        }
                    }
                }
                return flickrResponseMLD
            }

            override fun createCall() = flickrWebService.fetchFlickrFeed()
        }.asLiveData()
    }
}
