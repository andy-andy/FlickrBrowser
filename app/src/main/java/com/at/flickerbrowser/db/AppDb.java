package com.at.flickerbrowser.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.at.flickerbrowser.models.FlickrResponse;

@Database(entities = {FlickrResponse.class}, version = 1, exportSchema = false)
public abstract class AppDb extends RoomDatabase {

    public abstract FlickrResponseDao flickrResponseDao();

}

