package com.mihryundel.rbcnews.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public final class UpdateServiceStarterReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        UpdateEventReceiver.setupAlarm(context);
    }
}
