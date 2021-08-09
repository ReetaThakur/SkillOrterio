package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.app.skillontario.BottomBarActivity;
import com.app.skillontario.SignIn.ChangePasswordActivity;
import com.app.skillontario.SignIn.SignInActivity;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.signup.SignUpActivity;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityNotificationBinding;
import com.app.skillorterio.databinding.ActivitySelectRoleBinding;
import com.app.skillorterio.databinding.ActivitySettingBinding;
import com.app.skillorterio.databinding.ActivityTermsOfServicesBinding;

import java.util.HashMap;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillontario.constants.AppConstants.FIREBASE_TOKEN;
import static com.app.skillontario.constants.SharedPrefsConstants.GUEST_FLOW;
import static com.app.skillontario.constants.SharedPrefsConstants.USER_ID;
import static com.app.skillontario.utils.Utils.updatLocalLanguage;

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
        binding.actionBar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (language) {
                    manageBackPressed();
                } else {
                    onBackPressed();
                }
                //onBackPressed();
            }
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
            } else {
                updateNotification("1", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.LANGUAGE_API));
                binding.ivNotification.setImageResource(R.drawable.ic_notification_on);
                MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.NOTIFICATION_ON_OFF, true);
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
        });

        binding.cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MySharedPreference.getInstance().getBooleanData(GUEST_FLOW)) {
                    try {
                        Utils.guestMethod(SettingActivity.this, "setting");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    loguot();
            }
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
                    String token = MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN);
                    MySharedPreference.getInstance().clearSharedPrefs();

                    Intent intent = new Intent(SettingActivity.this, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    languageMethod(lang);
                    MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.GUEST_FLOW, false);
                    MySharedPreference.getInstance().setStringData(AppConstants.LANGUAGE, lang);
                    if (lang.equalsIgnoreCase("en")) {
                        MySharedPreference.getInstance().setStringData(SharedPrefsConstants.LANGUAGE_API, "eng");
                    } else {
                        MySharedPreference.getInstance().setStringData(SharedPrefsConstants.LANGUAGE_API, "fre");
                    }

                    MySharedPreference.getInstance().setStringData(FIREBASE_TOKEN, token);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        showToast(responseModel.getMessage());
                    }catch (Exception e){}

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

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
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


            }
        }, 70);
    }


}