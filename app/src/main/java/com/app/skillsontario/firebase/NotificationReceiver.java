package com.app.skillsontario.firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.skillsontario.baseClasses.AppController;


public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent("Received");
        intent1.putExtra("message", "data");
        LocalBroadcastManager.getInstance(AppController.context).sendBroadcast(intent1);
    }
}
