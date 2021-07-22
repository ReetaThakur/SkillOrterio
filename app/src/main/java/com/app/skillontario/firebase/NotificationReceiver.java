package com.app.skillontario.firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.skillontario.baseClasses.AppController;


public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent("notificationReceived");
        intent1.putExtra("message", "notification");
        LocalBroadcastManager.getInstance(AppController.context).sendBroadcast(intent1);
    }
}
