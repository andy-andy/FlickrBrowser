package com.at.flickerbrowser.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.at.flickerbrowser.models.Item;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FlickrItemDao {

    @Insert(onConflict = REPLACE)
    void save(List<Item> item);

    @Query("DELETE FROM item")
    void deleteAll();

    @Query("SELECT * FROM item")
    List<Item> loadFlickrItems();
}
