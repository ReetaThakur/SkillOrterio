package com.app.skillsontario.baseClasses

import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import java.lang.Exception
import android.content.pm.PackageManager
import com.app.skillsontario.baseClasses.AppController.context


object CrashLogger {
    fun logAndPrintException(e: Throwable) {
        Log.e("Exception Skills", e.printStackTrace().toString())

        val pm: PackageManager = context.getPackageManager()
        val applicationInfo = pm.getApplicationInfo(context.packageName, 0)

        val isDebuggable = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

        if (!isDebuggable) {
            try {
                val mFirebaseAnalytics: FirebaseAnalytics
                mFirebaseAnalytics = FirebaseAnalytics.getInstance(context)
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "CrashLogger Class Error")
                bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, e.message)
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
            } catch (e: Exception) {
            }
        }
    }

    fun trackEventsFirebase(eventMessage: String, screenName: String) {
        try {
            val mFirebaseAnalytics: FirebaseAnalytics =
                FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            bundle.putString("event_message", eventMessage)
            mFirebaseAnalytics.logEvent("Track_Events", bundle)
        } catch (e: Exception) {
        }
    }

    fun logMessage(message: String) {
        Log.e("Skills", message)
    }
}