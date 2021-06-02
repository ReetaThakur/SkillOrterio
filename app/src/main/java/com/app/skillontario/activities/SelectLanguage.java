package com.app.skillontario.activities;


import android.content.Intent;
import android.view.View;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.constants.AppConstants;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivitySelectLanguageBinding;

public class SelectLanguage extends BaseActivity {

    private ActivitySelectLanguageBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivitySelectLanguageBinding) viewBaseBinding;

        binding.imageViewEnglish.setOnClickListener(v -> {
            startActivity(new Intent(SelectLanguage.this, SelectRoleActivity.class));
            finishAffinity();
        });

        binding.imageViewFrench.setOnClickListener(v -> {
            startActivity(new Intent(SelectLanguage.this, SelectRoleActivity.class));
            finishAffinity();
        });
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_select_language;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }
}