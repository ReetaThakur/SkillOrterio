package com.app.skillontario.activities;


import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.app.skillontario.BottomBarActivity;
import com.app.skillontario.SignIn.SignInActivity;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.models.ResourceModal;
import com.app.skillontario.signup.SignUpActivity;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import static com.app.skillontario.constants.AppConstants.FIREBASE_TOKEN;
import static com.app.skillontario.constants.AppConstants.IS_WALK_THROUGH;
import static com.app.skillontario.constants.SharedPrefsConstants.IS_NOTIFICATION;
import static com.app.skillontario.constants.SharedPrefsConstants.IS_TUTORIAL_LEARN;
import static com.app.skillontario.constants.SharedPrefsConstants.USER_TOKEN;
import static com.app.skillontario.utils.Utils.updatLocalLanguage;


public class SplashActivity extends BaseActivity {
    private Handler mHandler = new Handler();
    String TAG = "SkillOntario";

    @Override
    protected void initUi() {

        //  Branch.sessionBuilder(this).withCallback(branchReferralInitListener).withData(getIntent() != null ? getIntent().getData() : null).init();


        //Log.e("userid", MySharedPreference.getInstance().getStringData(USER_ID));
        mHandler.postDelayed(runnable, 1500);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {

                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    String token = task.getResult();
                    Log.d("Sunny", "  fcm token > " + token);
                    MySharedPreference.getInstance().setStringData(FIREBASE_TOKEN, token);

                });

        try {
            /*//String lang = CommonFunctions.myPreference.getString(this, GlobalConstants.LANGUAGE);

            String lang = Locale.getDefault().getLanguage();
            changeLocale(lang);
*/
            String lang = MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE);
            if (lang != null) {
                if (lang.isEmpty()) {

                    updatLocalLanguage("en", getBaseContext());

                } else {

                    updatLocalLanguage(lang, getBaseContext());
                }
            } else {

                updatLocalLanguage("en", getBaseContext());
            }


        } catch (Exception e) {
        }

    }

   /* public void changeLocale(String lang) {
        try {
            if (lang.equalsIgnoreCase(""))
                return;
            Locale myLocale = new Locale("en");//Set Selected Locale
            Locale.setDefault(myLocale);//set new locale as default
            Configuration config = new Configuration();//get Configuration
            config.locale = myLocale;//set config locale as selected locale
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());//Update the config

            // validateView(lang);
        } catch (Exception er) {
            er.getMessage();
        }
    }
*/

    private Runnable runnable = () -> {

        boolean check = false;
        try {
            check = getIntent().getBooleanExtra("noti", false);
        } catch (Exception e) {
            check = false;
        }

        try {
            if (MySharedPreference.getInstance().getBooleanData("IS_NOTIFICATION") || check) {
                MySharedPreference.getInstance().setBooleanData(IS_NOTIFICATION, false);

                String typeClass, dataN;

                try {
                    typeClass = getIntent().getStringExtra("typeClass");
                    dataN = getIntent().getStringExtra("dataN");

                    JSONObject object1 = new JSONObject(dataN);
                    //  object1.has("_id");


                    //  String s = object1.getString("_id");


                    // Log.d("Sunny", " id  > " + s);

                    if (typeClass.equalsIgnoreCase("news")) {
                        String url1 = object1.getString("newsUrl");
                        Intent intent = new Intent(SplashActivity.this, NewsDetailAc.class);
                        intent.putExtra("url", url1);
                        startActivity(intent);
                        finishAffinity();
                    } else if (typeClass.equalsIgnoreCase("resource")) {
                        ResourceModal model = new ResourceModal();
                        model.setResUrl(object1.getString("resUrl"));
                        model.setId(object1.getString("_id"));
                        model.setResImage(object1.getString("resImage"));
                        model.setResDesc(object1.getString("resDesc"));
                        Intent intent = new Intent(SplashActivity.this, ResourcesDetailsActivity.class);
                        intent.putExtra("resData", model);
                        startActivity(intent);
                        finishAffinity();
                    } else if (typeClass.equalsIgnoreCase("profile")) {
                        Intent intent = new Intent(SplashActivity.this, BottomBarActivity.class);
                        //intent.putExtra("resData", "");
                        startActivity(intent);
                        finishAffinity();

                    } else if (typeClass.equalsIgnoreCase("event")) {
                        Intent intent = new Intent(SplashActivity.this, BottomBarActivity.class);
                        intent.putExtra("if", "2");
                        startActivity(intent);
                        finishAffinity();
                    } else if (typeClass.equalsIgnoreCase("mgsafai")) {
                        Intent intent = new Intent(SplashActivity.this, BottomBarActivity.class);
                        //intent.putExtra("resData", "");
                        startActivity(intent);
                        finishAffinity();
                    } else {
                        startActivity(new Intent(this, SelectLanguage.class));
                        finishAffinity();
                    }

                } catch (Exception e) {
                    typeClass = "";
                    startActivity(new Intent(this, SelectLanguage.class));
                    finishAffinity();

                }

            } else {
                MySharedPreference.getInstance().setBooleanData(IS_NOTIFICATION, false);
                if (!MySharedPreference.getInstance().getStringData(USER_TOKEN).equalsIgnoreCase("")) {
                    startActivity(new Intent(this, BottomBarActivity.class));
                    finishAffinity();

                } else {
                    if (MySharedPreference.getInstance().getBooleanData(IS_WALK_THROUGH)) {
                        startActivity(new Intent(this, SignUpActivity.class));
                        finishAffinity();
                    } else {
                        Intent intent = new Intent(this, SelectLanguage.class);
                        intent.putExtra(AppConstants.LOGIN_TYPE, "new");
                        startActivity(intent);
                        finishAffinity();
                    }
                }


            }
        } catch (Exception e) {
            startActivity(new Intent(this, SelectLanguage.class));
            finishAffinity();
        }


    };

    @Override
    protected int getLayoutById() {
        return R.layout.activity_splash;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
