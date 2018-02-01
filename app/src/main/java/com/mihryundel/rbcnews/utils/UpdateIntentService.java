package com.mihryundel.rbcnews.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mihryundel.rbcnews.RbcNewsApplication;
import com.mihryundel.rbcnews.mvp.presenters.NewsPresenter;

import javax.inject.Inject;


public class UpdateIntentService extends IntentService {

    private static final String ACTION_START = "ACTION_START";

    @Inject
    public NewsPresenter mNewsPresenter;

    public UpdateIntentService() {
        super(UpdateIntentService.class.getSimpleName());
        RbcNewsApplication.app().appComponent().inject(this);

    }

    public static Intent createIntentStartUpdateService(Context context) {
        Intent intent = new Intent(context, UpdateIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling an update event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                processStartUpdate();
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processStartUpdate() {
        // fetch fresh data from backend
        mNewsPresenter.updateNewsFromInternet();
    }

}
