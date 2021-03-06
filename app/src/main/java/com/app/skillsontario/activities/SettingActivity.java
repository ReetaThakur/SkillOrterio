package com.app.skillsontario.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.app.skillsontario.BottomBarActivity;
import com.app.skillsontario.R;
import com.app.skillsontario.SignIn.ChangePasswordActivity;
import com.app.skillsontario.SignIn.SignInActivity;
import com.app.skillsontario.apiConnection.ApiCallBack;
import com.app.skillsontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillsontario.apiConnection.RequestBodyGenerator;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.baseClasses.BaseResponseModel;
import com.app.skillsontario.baseClasses.CrashLogger;
import com.app.skillsontario.constants.AppConstants;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.ActivitySettingBinding;
import com.app.skillsontario.utils.MySharedPreference;
import com.app.skillsontario.utils.Utils;


import java.util.HashMap;

import static com.app.skillsontario.SignIn.SignInActivity.fcm;
import static com.app.skillsontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillsontario.constants.AppConstants.FIREBASE_TOKEN;
import static com.app.skillsontario.constants.AppConstants.IS_WALK_THROUGH;
import static com.app.skillsontario.constants.SharedPrefsConstants.GUEST_FLOW;
import static com.app.skillsontario.constants.SharedPrefsConstants.USER_ID;
import static com.app.skillsontario.utils.Utils.updatLocalLanguage;

public class SettingActivity extends BaseActivity implements ApiResponseErrorCallback {

    private ActivitySettingBinding binding;
    public static boolean language = false;


    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivitySettingBinding) viewBaseBinding;

        if (MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.NOTIFICATION_ON_OFF)) {
            binding.ivNotification.setImageResource(R.drawable.ic_notification_on);
            updateNotification("1", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.LANGUAGE_API));
        } else {
            updateNotification("0", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.LANGUAGE_API));
            binding.ivNotification.setImageResource(R.drawable.ic_notification_off);
        }

        binding.actionBar.tvTitle.setText(R.string.settings);
        binding.actionBar.ivBack.setOnClickListener(v -> {
            if (language) {
                manageBackPressed();
            } else {
                onBackPressed();
            }
            //onBackPressed();
        });

        binding.lPrivacy.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, PrivacyPolicyActivity.class)));

        binding.lChangePassword.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, ChangePasswordActivity.class)));

        binding.lContactUS.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, ContactUsAc.class)));

        binding.lFeedback.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, FeedBackActivity.class)));

        binding.lHelp.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, HelpActivity.class)));

        binding.lTerms.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, TermsOfServicesActivity.class)));


        binding.ivNotification.setOnClickListener(v -> {

            if (MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.NOTIFICATION_ON_OFF)) {
                MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.NOTIFICATION_ON_OFF, false);
                updateNotification("0", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.LANGUAGE_API));
                binding.ivNotification.setImageResource(R.drawable.ic_notification_off);

                try {
                    CrashLogger.INSTANCE.trackEventsFirebase("Turned_Off_Notification", "SettingActivity");
                } catch (Exception e) {
                }
            } else {
                updateNotification("1", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.LANGUAGE_API));
                binding.ivNotification.setImageResource(R.drawable.ic_notification_on);
                MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.NOTIFICATION_ON_OFF, true);
                try {
                    CrashLogger.INSTANCE.trackEventsFirebase("Turned_On_Notification", "SettingActivity");
                } catch (Exception e) {
                }
            }

        });

        binding.tvSettingEnglish.setOnClickListener(v -> {
            language = true;
            binding.tvSettingEnglish.setTextColor(Color.parseColor("#ffffff"));
            binding.tvSettingFrench.setTextColor(Color.parseColor("#000000"));
            binding.tvSettingEnglish.setBackgroundResource(R.drawable.ic_lang_rectangle);
            binding.tvSettingFrench.setBackgroundResource(R.drawable.ic_lang_rectangle_transparent);

            MySharedPreference.getInstance().setStringData(SharedPrefsConstants.LANGUAGE_API, "eng");
            MySharedPreference.getInstance().setStringData(AppConstants.LANGUAGE, "en");
            languageMethod(MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE));

            updateLanguage("eng");

            setText();


            try {
                CrashLogger.INSTANCE.trackEventsFirebase("English_Language_Select", "SettingActivity");
            } catch (Exception e) {
            }
        });

        binding.tvSettingFrench.setOnClickListener(v -> {
            language = true;
            binding.tvSettingEnglish.setTextColor(Color.parseColor("#000000"));
            binding.tvSettingFrench.setTextColor(Color.parseColor("#ffffff"));
            binding.tvSettingFrench.setBackgroundResource(R.drawable.ic_lang_rectangle);
            binding.tvSettingEnglish.setBackgroundResource(R.drawable.ic_lang_rectangle_transparent);

            MySharedPreference.getInstance().setStringData(SharedPrefsConstants.LANGUAGE_API, "fre");
            MySharedPreference.getInstance().setStringData(AppConstants.LANGUAGE, "fr");
            languageMethod(MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE));
            updateLanguage("fre");
            setText();

            try {
                CrashLogger.INSTANCE.trackEventsFirebase("French_Language_Select", "SettingActivity");
            } catch (Exception e) {
            }
        });

        binding.cvLogout.setOnClickListener(v -> {
            if (MySharedPreference.getInstance().getBooleanData(GUEST_FLOW)) {
                try {
                    Utils.guestMethod(SettingActivity.this, "setting");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                loguot();
        });


    }


    private void manageBackPressed() {
        languageMethod(MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE));
        Intent intent = new Intent(SettingActivity.this, BottomBarActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setText();

    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_setting;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (language) {
            manageBackPressed();
        }
        //  manageBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 105) {
            BaseResponseModel responseModel = (BaseResponseModel) responseObject;
        } else if (flag == 10) {
            try {
                BaseResponseModel responseModel = (BaseResponseModel) responseObject;
                if (responseModel.getStatus()) {
                    showToast(responseModel.getMessage());
                    //languageMethod();
                    String lang = MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE);


                    if (MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN) != null) {
                        if (MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN).equalsIgnoreCase("")) {

                        } else {
                            fcm = MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN);
                        }
                    }

                    Log.d("Sunny", " fcm tokn  " + fcm);
                    MySharedPreference.getInstance().clearSharedPrefs();
                    MySharedPreference.getInstance().setBooleanData(IS_WALK_THROUGH, true);
                    MySharedPreference.getInstance().setStringData(FIREBASE_TOKEN, fcm);

                    languageMethod(lang);

                    MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.GUEST_FLOW, false);
                    MySharedPreference.getInstance().setStringData(AppConstants.LANGUAGE, lang);
                    if (lang.equalsIgnoreCase("en")) {
                        MySharedPreference.getInstance().setStringData(SharedPrefsConstants.LANGUAGE_API, "eng");
                    } else {
                        MySharedPreference.getInstance().setStringData(SharedPrefsConstants.LANGUAGE_API, "fre");
                    }

                    Intent intent = new Intent(SettingActivity.this, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    try {
                        showToast(responseModel.getMessage());
                    } catch (Exception e) {
                    }

                }
            } catch (Exception e) {
            }
        }

    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }

    void updateNotification(String num, String lang) {
        HashMap<String, Object> object = new HashMap<>();

        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
        object.put("notifyStatus", num);
        object.put("lang", lang);

        API_INTERFACE.updateUser(object).enqueue(
                new ApiCallBack<>(SettingActivity.this, this, 105, false));
    }

    void updateLanguage(String lang) {
        HashMap<String, Object> object = new HashMap<>();

        object.put("userId", MySharedPreference.getInstance().getStringData(USER_ID));
        object.put("lang", lang);

        API_INTERFACE.updateUser(object).enqueue(
                new ApiCallBack<>(SettingActivity.this, this, 105, false));
    }

    private void loguot() {
        API_INTERFACE.logout(RequestBodyGenerator.Logout(MySharedPreference.getInstance().getStringData(USER_ID))).enqueue(
                new ApiCallBack<>(SettingActivity.this, this, 10, true));

        try {
            CrashLogger.INSTANCE.trackEventsFirebase("User_Logout", "SettingActivity");
        } catch (Exception e) {
        }
    }

    private void languageMethod(String lang) {

        if (lang != null) {
            if (lang.isEmpty()) {
                updatLocalLanguage("en", getBaseContext());

            } else {
                updatLocalLanguage(lang, getBaseContext());
            }
        } else {
            updatLocalLanguage("en", getBaseContext());
        }
    }

    void setText() {

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            try {
                languageMethod(MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE));
                binding.actionBar.tvTitle.setText(R.string.settings);
                binding.tvNotification.setText(R.string.notification);
                binding.tvChangePassword.setText(R.string.change_password);
                binding.tvLanguage1.setText(R.string.language);
                binding.tvSettingEnglish.setText(R.string.english);
                binding.tvSettingFrench.setText(R.string.fran_ais);
                binding.tvTermsOfService.setText(R.string.terms_of_service);
                binding.tvFeedback.setText(R.string.feedback);
                binding.tvPrivacyPolicy.setText(R.string.privacy_policy);
                binding.tvContactUs.setText(R.string.contact_us);
                //binding.tvLogout.setText(R.string.logout);
            } catch (Exception e) {
            }


            try {
                if (MySharedPreference.getInstance().getBooleanData(GUEST_FLOW)) {
                    //binding.cvLogout.setVisibility(View.GONE);
                    binding.tvLogout.setText(R.string.sign_up);
                    binding.lChangePassword.setVisibility(View.GONE);
                } else {
                    binding.tvLogout.setText(R.string.logout);
                    binding.cvLogout.setVisibility(View.VISIBLE);
                    binding.lChangePassword.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
            }

            try {
                if (MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE).equalsIgnoreCase("en")) {
                    binding.tvSettingEnglish.setTextColor(Color.parseColor("#ffffff"));
                    binding.tvSettingFrench.setTextColor(Color.parseColor("#000000"));
                    binding.tvSettingEnglish.setBackgroundResource(R.drawable.ic_lang_rectangle);
                    binding.tvSettingFrench.setBackgroundResource(R.drawable.ic_lang_rectangle_transparent);
                } else {
                    binding.tvSettingEnglish.setTextColor(Color.parseColor("#000000"));
                    binding.tvSettingFrench.setTextColor(Color.parseColor("#ffffff"));
                    binding.tvSettingFrench.setBackgroundResource(R.drawable.ic_lang_rectangle);
                    binding.tvSettingEnglish.setBackgroundResource(R.drawable.ic_lang_rectangle_transparent);
                }
            } catch (Exception e) {
            }

            try {

                if (MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.NOTIFICATION_ON_OFF)) {
                    binding.ivNotification.setImageResource(R.drawable.ic_notification_on);
                } else {
                    binding.ivNotification.setImageResource(R.drawable.ic_notification_off);
                }
            } catch (Exception e) {
            }


        }, 70);
    }


}