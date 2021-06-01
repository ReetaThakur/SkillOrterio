package com.app.skillontario.baseClasses;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

public class AppController extends MultiDexApplication {

    public static Context context;
  //  private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
      //  mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
       // PaymentConfiguration.init(getApplicationContext(), STRIP_KEY);

        //        DocuSign.init(
//                this, // the Application Context
//                "91be1136-c3f7-48e6-b39c-11dfbd9c2e1a", // recommend not hard-coding this
//                DSMode.DEBUG // this controls the logging (logcat) behavior
//        );



    }

    public Context getContext() {
        return context;
    }
}
