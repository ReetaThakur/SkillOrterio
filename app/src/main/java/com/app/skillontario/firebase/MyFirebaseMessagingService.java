package com.app.skillontario.firebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.app.skillontario.activities.SplashActivity;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.home.HomeFragment;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

import me.leolin.shortcutbadger.ShortcutBadger;

import static com.app.skillontario.constants.AppConstants.FIREBASE_TOKEN;
import static com.app.skillontario.constants.AppConstants.NOTIFICATION_COUNT;
import static com.app.skillontario.constants.SharedPrefsConstants.IS_NOTIFICATION;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String ADMIN_CHANNEL_ID = "00001";
    public static NotificationManager notificationManager;
    private String dataN, typeClass, pushUrl;

    @Override
    public void onNewToken(@NotNull String refreshedToken) {
        Log.e("NEW_TOKEN", refreshedToken);
        storeRegIdInPref(refreshedToken);
    }

    private void storeRegIdInPref(String token) {
        Log.e("FIREBASE TOKEN ", token);
        MySharedPreference.getInstance().setStringData(FIREBASE_TOKEN, token);
    }

    @SuppressLint("LongLogTag")
    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        Log.e("Sunny", " onMessageReceived call ");
        showCustomNotification(remoteMessage);
        MySharedPreference.getInstance().setBooleanData(IS_NOTIFICATION, true);

        /*Badge count handling*/
        int count = 0;
        if (MySharedPreference.getInstance().getStringData(NOTIFICATION_COUNT) == null ||
                MySharedPreference.getInstance().getStringData(NOTIFICATION_COUNT).isEmpty())
            count = 0;
        else
            count = Integer.parseInt(MySharedPreference.getInstance().getStringData(NOTIFICATION_COUNT));

        try {
            HomeFragment.tvNotificationCount.setText("" + count);
            HomeFragment.tvNotificationCount.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }

        // MySharedPreference.getInstance().setStringData(NOTIFICATION_COUNT, String.valueOf(++count));

        ShortcutBadger.removeCount(this);
        ShortcutBadger.applyCount(this, count);

    }

    private void showCustomNotification(RemoteMessage remoteMessage) {
        String s;
        if (remoteMessage.getData().size() > 0) {

            try {
                Map<String, String> params = remoteMessage.getData();
                JSONObject object = new JSONObject(params);

                MySharedPreference.getInstance().saveNotificationObject(SharedPrefsConstants.NOTIFICATION_DATA, object);


                try {
                    if (object.has("type"))
                        typeClass = object.getString("type");
                } catch (Exception e) {
                }

                if (typeClass != null) {
                    if (typeClass.equalsIgnoreCase("push")) {
                        if (object.has("url"))
                            pushUrl = object.getString("url");
                    }
                }

                if (typeClass == null) {

                } else {

                    try {
                        if (object.has("content"))
                            dataN = object.getString("content");
                    } catch (Exception e) {
                    }


                   /* try {
                        JSONObject object1 = new JSONObject(dataN);
                        object1.has("_id");
                        // s = object1.getString("_id");
                    } catch (Exception e) {
                    }*/
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        Intent notificationIntent = new Intent(this, SplashActivity.class);
        notificationIntent.putExtra("typeClass", typeClass);
        notificationIntent.putExtra("dataN", dataN);
        notificationIntent.putExtra("pushUrl", pushUrl);
        notificationIntent.putExtra("noti", true);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int notificationId = new Random().nextInt(60000);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels();
        }

        String title = "";
        if (remoteMessage.getNotification().getTitle() != null)
            title = remoteMessage.getNotification().getTitle();


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                                R.mipmap.ic_launcher))
                        .setSmallIcon(R.drawable.ic_icon_new_logo)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setContentTitle(title)
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                        .setCategory(Notification.CATEGORY_REMINDER)
                        .setContentIntent(pendingIntent);


        notificationManager.notify(notificationId, notificationBuilder.build());
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels() {
        CharSequence adminChannelName = "Skills Notification";
        String adminChannelDescription = "notifications_admin_channel_description";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        adminChannel.setShowBadge(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

    public static void initialiseManager(Context context) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

   /* public void sendMyBroadCast(Context context, String completeAction) {
        try {
            Intent broadcastIntent = new Intent(AppConstants.ACTION.MAIN_ACTION);
            // int duration = getDuration();
            Bundle bdl = new Bundle();
            bdl.putString(AppConstants.CONTENT_TYPE, completeAction);
            broadcastIntent.putExtras(bdl);
            context.sendBroadcast(broadcastIntent);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/
}