package com.at.flickerbrowser.di;

import android.arch.persistence.room.Room;

import com.at.flickerbrowser.App;
import com.at.flickerbrowser.db.AppDb;
import com.at.flickerbrowser.db.FlickrResponseDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    public AppDb provideFlickrDb(App app) {
        return Room.databaseBuilder(app, AppDb.class, "myflickr.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    public FlickrResponseDao provideFlickrResponseDao(AppDb db) {
        return db.flickrResponseDao();
    }
}
