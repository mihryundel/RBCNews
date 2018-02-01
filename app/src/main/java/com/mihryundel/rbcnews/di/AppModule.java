package com.mihryundel.rbcnews.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.mihryundel.rbcnews.RbcNewsDatabase;
import com.mihryundel.rbcnews.mvp.models.NewsDao;
import com.mihryundel.rbcnews.mvp.presenters.NewsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext(){
        return context;
    }

    @Singleton
    @Provides
    public RbcNewsDatabase provideRbcNewsDatabase(Context context){
        return Room.databaseBuilder(context, RbcNewsDatabase.class, "rbcnews-db").allowMainThreadQueries().build();
    }

    @Singleton
    @Provides
    public NewsDao provideNewsDao(RbcNewsDatabase rbcNewsDatabase){
        return rbcNewsDatabase.newsDao();
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return context.getSharedPreferences("PrefName",Context.MODE_PRIVATE);
    }

    @Provides
    NewsPresenter provideNewsPresenter() {
        return new NewsPresenter();
    }
}
