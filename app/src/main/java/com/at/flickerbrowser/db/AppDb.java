package com.at.flickerbrowser.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.at.flickerbrowser.models.FlickrResponse;
import com.at.flickerbrowser.models.Item;

@Database(entities = {FlickrResponse.class, Item.class}, version = 2, exportSchema = false)
public abstract class AppDb extends RoomDatabase {

    public abstract FlickrResponseDao flickrResponseDao();

    public abstract FlickrItemDao flickrItemDao();

}

