package com.mihryundel.rbcnews;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.mihryundel.rbcnews.mvp.models.News;
import com.mihryundel.rbcnews.mvp.models.NewsDao;


@Database(entities = {News.class}, version = 2)
public abstract class RbcNewsDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}