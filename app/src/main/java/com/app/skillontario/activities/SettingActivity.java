package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.skillontario.SignIn.ChangePasswordActivity;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityNotificationBinding;
import com.app.skillorterio.databinding.ActivitySelectRoleBinding;
import com.app.skillorterio.databinding.ActivitySettingBinding;
import com.app.skillorterio.databinding.ActivityTermsOfServicesBinding;

public class SettingActivity extends BaseActivity {

    private ActivitySettingBinding binding;

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