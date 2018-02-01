package com.mihryundel.rbcnews.di;

import com.mihryundel.rbcnews.MySharedPreferences;
import com.mihryundel.rbcnews.mvp.presenters.NewsPresenter;
import com.mihryundel.rbcnews.ui.activities.SettingsActivity;
import com.mihryundel.rbcnews.ui.adapters.NewsRecyclerAdapter;
import com.mihryundel.rbcnews.utils.ParsingNewsAsyncTask;
import com.mihryundel.rbcnews.utils.UpdateIntentService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(NewsPresenter newsPresenter);
    void inject(ParsingNewsAsyncTask parsingNewsAsyncTask);
    void inject(MySharedPreferences mySharedPreferences);
    void inject(SettingsActivity.MainPreferenceFragment mainPreferenceFragment);

    void inject(NewsRecyclerAdapter newsRecyclerAdapter);

    void inject(UpdateIntentService updateIntentService);
}