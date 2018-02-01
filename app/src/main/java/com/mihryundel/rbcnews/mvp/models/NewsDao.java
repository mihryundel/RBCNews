package com.mihryundel.rbcnews.mvp.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM News")
    List<News> loadAll();

    @Query("SELECT * FROM News WHERE url LIKE :substring")
    List<News> loadAll(String substring);

    @Query("DELETE FROM News")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(News entry);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(News... newsEntries);
}