package com.at.flickerbrowser.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.at.flickerbrowser.models.FlickrResponse;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FlickrResponseDao {

    @Insert(onConflict = REPLACE)
    void save(FlickrResponse flickrResponse);

    @Query("DELETE FROM FlickrResponse")
    void deleteAll();

    @Query("SELECT * FROM FlickrResponse")
    LiveData<FlickrResponse> loadFlickrResponse();
}
