package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityNotificationBinding;
import com.app.skillorterio.databinding.ActivitySelectRoleBinding;
import com.app.skillorterio.databinding.ActivitySettingBinding;
import com.app.skillorterio.databinding.ActivityTermsOfServicesBinding;

import java.util.HashMap;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillontario.constants.SharedPrefsConstants.USER_ID;
import static com.app.skillontario.utils.Utils.updatLocalLanguage;

public class SettingActivity extends BaseActivity implements ApiResponseErrorCallback {

    private ActivitySettingBinding binding;
    boolean notiOnOff = false;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivitySettingBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText("Settings");
        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());

        binding.lPrivacy.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, PrivacyPolicyActivity.class)));

        binding.lChangePassword.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, ChangePasswordActivity.class)));

        binding.lContactUS.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, ContactUsAc.class)));

        binding.lFeedback.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, FeedBackActivity.class)));

        binding.lHelp.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, HelpActivity.class)));

        binding.lTerms.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, TermsOfServicesActivity.class)));


        binding.ivNotification.setOnClickListener(v -> {
            if (notiOnOff) {
                notiOnOff = false;
                binding.ivNotification.setImageResource(R.drawable.ic_notification_on);
            } else {
                notiOnOff = true;
                binding.ivNotification.setImageResource(R.drawable.ic_notification_off);
            }

            updateNotification("1", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.LANGUAGE_API));
        });

        binding.tvSettingEnglish.setOnClickListener(v -> {
            binding.tvSettingEnglish.setTextColor(Color.parseColor("#ffffff"));
            binding.tvSettingFrench.setTextColor(Color.parseColor("#000000"));
            binding.tvSettingEnglish.setBackgroundResource(R.drawable.ic_lang_rectangle);
            binding.tvSettingFrench.setBackgroundResource(R.drawable.ic_lang_rectangle_transparent);
        });

        binding.tvSettingFrench.setOnClickListener(v -> {
            binding.tvSettingEnglish.setTextColor(Color.parseColor("#000000"));
            binding.tvSettingFrench.setTextColor(Color.parseColor("#ffffff"));
            binding.tvSettingFrench.setBackgroundResource(R.drawable.ic_lang_rectangle);
            binding.tvSettingEnglish.setBackgroundResource(R.drawable.ic_lang_rectangle_transparent);
        });

        binding.cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loguot();
            }
        });


    }


    @Override
    protected int getLayoutById() {
        return R.layout.activity_setting;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                    Intent intent = new Intent(SettingActivity.this, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    MySharedPreference.getInstance().clearSharedPrefs();
                    startActivity(intent);
                    languageMethod(lang);
                    MySharedPreference.getInstance().setStringData(AppConstants.LANGUAGE, "lang");
                    finish();
                } else {
                    showToast(responseModel.getMessage());
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

        object.put("userId", MySharedPreference.getInstance().getStringData(USER_ID));
        object.put("notifyStatus", num);
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


}