package com.mihryundel.rbcnews;

import android.app.Application;

import com.mihryundel.rbcnews.di.AppComponent;
import com.mihryundel.rbcnews.di.AppModule;
import com.mihryundel.rbcnews.di.DaggerAppComponent;
import com.mihryundel.rbcnews.utils.UpdateEventReceiver;


public class RbcNewsApplication extends Application {

    private static RbcNewsApplication app;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext())).build();

        UpdateEventReceiver.setupAlarm(getApplicationContext());
    }

    public static RbcNewsApplication app(){
        return app;
    }

    public AppComponent appComponent(){
        return appComponent;
    }
}