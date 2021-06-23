package com.app.skillontario.activities;


import android.view.View;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;

import com.app.skillorterio.databinding.ActivityTermsOfServicesBinding;

public class TermsOfServicesActivity extends BaseActivity {

    private ActivityTermsOfServicesBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityTermsOfServicesBinding) viewBaseBinding;

        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());


    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_terms_of_services;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }
}