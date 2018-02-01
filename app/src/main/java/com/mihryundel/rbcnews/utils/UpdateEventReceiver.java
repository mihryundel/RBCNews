package com.mihryundel.rbcnews.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class UpdateEventReceiver extends WakefulBroadcastReceiver {

    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static int NOTIFICATIONS_INTERVAL_IN_FIFTEEN_MINUTES = 1;

    public static void setNotificationsIntervalInFifteenMinutes(int notificationsIntervalInFifteenMinutes) {
        NOTIFICATIONS_INTERVAL_IN_FIFTEEN_MINUTES = notificationsIntervalInFifteenMinutes;
    }
    public static void setupAlarm(Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        setNotificationsIntervalInFifteenMinutes(Integer.valueOf(mPrefs.getString("intervalList", "1")));
        if (NOTIFICATIONS_INTERVAL_IN_FIFTEEN_MINUTES != 0) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent alarmIntent = getStartPendingIntent(context);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    getTriggerAt(new Date()),
                    NOTIFICATIONS_INTERVAL_IN_FIFTEEN_MINUTES * AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    alarmIntent);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent serviceIntent = null;
        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {
            Log.i(getClass().getSimpleName(), "onReceive from alarm, starting update service");
            serviceIntent = UpdateIntentService.createIntentStartUpdateService(context);
        }

        if (serviceIntent != null) {
            startWakefulService(context, serviceIntent);
        }
    }

    private static long getTriggerAt(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        return calendar.getTimeInMillis();
    }

    private static PendingIntent getStartPendingIntent(Context context) {
        Intent intent = new Intent(context, UpdateEventReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}