package com.app.skillontario.activities;


import android.content.Intent;
import android.os.Handler;

import com.app.skillontario.SignIn.WelcomeActivity;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;

import static com.app.skillontario.constants.SharedPrefsConstants.IS_LOGIN;
import static com.app.skillontario.constants.SharedPrefsConstants.IS_TUTORIAL_LEARN;

public class SplashActivity extends BaseActivity {
    private Handler mHandler = new Handler();
    String TAG = "SkillOntario";

    @Override
    protected void initUi() {

        //Log.e("userid", MySharedPreference.getInstance().getStringData(USER_ID));
        mHandler.postDelayed(runnable, 1500);

       /* FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {

                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    String token = task.getResult();
                    MySharedPreference.getInstance().setStringData(FCM_ID, token);

                });*/
    }

    private Runnable runnable = () -> {
        String url;
        boolean check = false;
        try {
            url = getIntent().getStringExtra("url");
            check = true;
        } catch (Exception e) {
            url = "";
            check = false;
        }

        if (MySharedPreference.getInstance().getBooleanData(IS_LOGIN)) {
           /* Intent intent = new Intent(this, HomeLoginActivity.class);
            intent.putExtra(AppConstants.LOGIN_TYPE, "old");
            if (check)
                intent.putExtra("url", url);
            startActivity(intent);*/
            finishAffinity();
        } else {
            if (MySharedPreference.getInstance().getBooleanData(IS_TUTORIAL_LEARN)) {
                Intent intent = new Intent(this, SelectLanguage.class);
                intent.putExtra(AppConstants.LOGIN_TYPE, "new");
                startActivity(intent);
                finishAffinity();
            } else {
                startActivity(new Intent(this, WelcomeActivity.class));
                finishAffinity();
            }
        }

    };

    @Override
    protected int getLayoutById() {
        return R.layout.activity_splash;
    }
}
