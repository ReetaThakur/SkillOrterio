package com.app.skillsontario.activities;


import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.skillsontario.BottomBarActivity;
import com.app.skillsontario.R;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.constants.AppConstants;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.models.ResourceModal;
import com.app.skillsontario.models.ScholarShipModal;
import com.app.skillsontario.signup.SignUpActivity;
import com.app.skillsontario.utils.MySharedPreference;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.validators.IntegrationValidator;

import static com.app.skillsontario.constants.AppConstants.FIREBASE_TOKEN;
import static com.app.skillsontario.constants.AppConstants.IS_WALK_THROUGH;
import static com.app.skillsontario.constants.SharedPrefsConstants.IS_NOTIFICATION;
import static com.app.skillsontario.constants.SharedPrefsConstants.USER_TOKEN;
import static com.app.skillsontario.utils.Utils.updatLocalLanguage;


public class SplashActivity extends BaseActivity {
    private Handler mHandler = new Handler();
    String TAG = "SkillOntario";
    ImageView imageView;

    @Override
    protected void initUi() {

        IntegrationValidator.validate(SplashActivity.this);

        Branch.enableLogging();

        // Branch logging for debugging
        Branch.enableDebugMode();

        // Branch object initialization
        Branch.getAutoInstance(this);

        try {
            BranchUniversalObject buo = new BranchUniversalObject()
                    .setCanonicalIdentifier("content/12345")
                    .setTitle("My Content Title")
                    .setContentDescription("My Content Description")
                    .setContentImageUrl("https://lorempixel.com/400/400")
                    .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                    .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                    .setContentMetadata(new ContentMetadata().addCustomMetadata("key1", "value1"));
        } catch (Exception e) {
        }

        imageView = (ImageView) findViewById(R.id.img1);


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

        intitView();

    }


    private Runnable runnable = () -> {

        boolean check = false;
        String typeClass = "", dataN = "", pushUrl = "";
        JSONObject object2 = null;
        try {
            check = getIntent().getBooleanExtra("noti", false);
            dataN = getIntent().getStringExtra("dataN");
            typeClass = getIntent().getStringExtra("typeClass");

        } catch (Exception e) {
            check = false;
        }

        try {
            if (MySharedPreference.getInstance().getBooleanData("IS_NOTIFICATION") || check) {
                MySharedPreference.getInstance().setBooleanData(IS_NOTIFICATION, false);

                JSONObject object = null;
                try {
                    if (typeClass == null) {
                        typeClass = "";
                    }
                } catch (Exception e) {
                    typeClass = "";
                }

            Log.d("Sunny","  pending intent  "+typeClass);
                try {

                    if (typeClass.equalsIgnoreCase("news")) {

                        // dataN = getIntent().getStringExtra("dataN");
                        JSONObject object1 = new JSONObject(dataN);
                        String url1 = object1.getString("newsUrl");
                        Intent intent = new Intent(SplashActivity.this, WebViewActivity.class);
                        intent.putExtra("url", url1);
                        startActivity(intent);
                        finishAffinity();
                    } else if (typeClass.equalsIgnoreCase("resource")) {


                        JSONObject object1 = new JSONObject(dataN);
                        ResourceModal model = new ResourceModal();
                        model.setResUrl(object1.getString("resUrl"));
                        model.setId(object1.getString("_id"));
                        model.setResImage(object1.getString("resImage"));
                        model.setResDesc(object1.getString("resDesc"));
                        model.setResTitle(object1.getString("resTitle"));
                        Intent intent = new Intent(SplashActivity.this, ResourcesDetailsActivity.class);
                        intent.putExtra("model", (Serializable) model);
                        startActivity(intent);
                        finishAffinity();
                    } else if (typeClass.equalsIgnoreCase("profile")) {

                        JSONObject object1 = new JSONObject(dataN);
                        String id = object1.getString("_id");
                        Intent intent = new Intent(SplashActivity.this, JobDetailsActivity.class);
                        intent.putExtra("Popular", id);
                        startActivity(intent);
                        finishAffinity();

                    } else if (typeClass.equalsIgnoreCase("event")) {
                        Intent intent = new Intent(SplashActivity.this, BottomBarActivity.class);
                        intent.putExtra("if", "2");
                        startActivity(intent);
                        finishAffinity();
                    } else if (typeClass.equalsIgnoreCase("mgsafai")) {
                        JSONObject object1 = new JSONObject(dataN);
                        ScholarShipModal model = new ScholarShipModal();
                        model.setTitle(object1.getString("title"));
                        model.setImage(object1.getString("image"));
                        model.setDesc(object1.getString("desc"));
                        model.setWebUrl(object1.getString("webUrl"));

                        Intent intent = new Intent(SplashActivity.this, ScholarDetailAc.class);
                        intent.putExtra("scholar", (Serializable) model);
                        startActivity(intent);
                        finishAffinity();
                    } else if (typeClass.equalsIgnoreCase("push")) {
                        pushUrl = getIntent().getStringExtra("pushUrl");

                        Intent intent = new Intent(SplashActivity.this, WebViewActivity.class);
                        intent.putExtra("url", pushUrl);
                        startActivity(intent);
                        finishAffinity();
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
                    typeClass = "";
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
        } finally {
            MySharedPreference.getInstance().saveNotificationObject(SharedPrefsConstants.NOTIFICATION_DATA, null);
        }


    };

    @Override
    public void onStart() {
        super.onStart();
        Branch.sessionBuilder(this).withCallback((referringParams, error) -> {
            if (error == null) {

                // option 1: log data
                Log.i("BRANCH SDK", referringParams.toString());


            } else {
                Log.i("BRANCH SDK", error.getMessage());
            }
        }).withData(this.getIntent().getData()).init();

        Branch.sessionBuilder(this).withCallback(branchReferralInitListener).withData(getIntent() != null ? getIntent().getData() : null).init();


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        try {
            Branch.sessionBuilder(this).withCallback(branchReferralInitListener).reInit();
        } catch (Exception e) {
        }
    }

    private Branch.BranchReferralInitListener branchReferralInitListener = (linkProperties, error) -> {
        // do stuff with deep link data (nav to page, display content, etc)
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

    private void intitView() {

        Branch.sessionBuilder(this).withCallback(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {

                    // option 1: log data
                    Log.i("deeplinkingcallback", referringParams.toString());

                    JSONObject sessionParams = referringParams;
                    String check = "", type, id;
                    try {
                        check = sessionParams.getString("+clicked_branch_link");

                        if (check.equalsIgnoreCase("true")) {
                            if (sessionParams.has("type")) {
                                type = sessionParams.getString("type");
                                id = sessionParams.getString("id");
                                callDeepLink(type, id);
                            } else {
                                imageView.post(() -> mHandler.postDelayed(runnable, 2000));
                            }

                        } else {
                            imageView.post(() -> mHandler.postDelayed(runnable, 2000));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        imageView.post(() -> mHandler.postDelayed(runnable, 2000));
                    }


                } else {
                    // Log.i("deeplinkingcallback", error.getMessage());
                    imageView.post(() -> mHandler.postDelayed(runnable, 2000));
                }
            }
        }).withData(this.getIntent().getData()).init();


    }

    void callDeepLink(String type, String id) {
        try {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, DeepLinkActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type", type);
                startActivity(intent);
                finish();

            }, 3000);
        } catch (Exception e) {
        }
    }
}
