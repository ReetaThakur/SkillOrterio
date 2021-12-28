package com.app.skillsontario.firebase;

import android.content.Context;
import android.content.Intent;

import com.app.skillsontario.utils.MySharedPreference;
import com.google.android.gms.stats.GCoreWakefulBroadcastReceiver;

import static com.app.skillsontario.constants.AppConstants.NOTIFICATION_COUNT;
import static com.app.skillsontario.constants.SharedPrefsConstants.IS_NOTIFICATION;


public class FirebaseBroadcastReceiver extends GCoreWakefulBroadcastReceiver {
    private int count;

    @Override
    public void onReceive(Context context, Intent intent) {

        MySharedPreference.getInstance().setBooleanData(IS_NOTIFICATION, true);

        /*Badge count handling*/
        if (MySharedPreference.getInstance().getStringData(NOTIFICATION_COUNT) == null ||
                MySharedPreference.getInstance().getStringData(NOTIFICATION_COUNT).isEmpty())
            count = 0;
        else
            count = Integer.parseInt(MySharedPreference.getInstance().getStringData(NOTIFICATION_COUNT));

        MySharedPreference.getInstance().setStringData(NOTIFICATION_COUNT, String.valueOf(++count));

        // ShortcutBadger.removeCount(AppController.context);
        // ShortcutBadger.applyCount(AppController.context, count);
    }
}