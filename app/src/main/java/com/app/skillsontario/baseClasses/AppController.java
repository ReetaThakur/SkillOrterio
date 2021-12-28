package com.app.skillsontario.baseClasses;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDexApplication;

import com.app.skillsontario.utils.LocaleManager;
import com.google.firebase.messaging.FirebaseMessaging;

import io.branch.referral.Branch;

public class AppController extends MultiDexApplication implements LifecycleObserver {

    public static Context context;

    //  private FirebaseAnalytics mFirebaseAnalytics;
    public static final String CHANNEL_ID = "ServiceChannel";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.getInstance().setLocale(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.getInstance().setLocale(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        context = AppController.this;

        createNotificationChannel();

        // Branch logging for debugging
        Branch.enableLogging();

        // Branch object initialization
        Branch.getAutoInstance(this);

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_NONE
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public static Context getApplicationInstance() {
        return context;
    }

    public Context getContext() {
        return context;
    }

}
