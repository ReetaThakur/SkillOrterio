package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.app.skillontario.SignIn.ChangePasswordActivity;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.signup.SignUpActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityNotificationBinding;
import com.app.skillorterio.databinding.ActivitySelectRoleBinding;
import com.app.skillorterio.databinding.ActivitySettingBinding;
import com.app.skillorterio.databinding.ActivityTermsOfServicesBinding;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;

public class SettingActivity extends BaseActivity {

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
}