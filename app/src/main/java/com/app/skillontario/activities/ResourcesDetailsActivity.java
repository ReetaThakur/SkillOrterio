package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityPrivacyPolicyBinding;
import com.app.skillorterio.databinding.ActivityResourcesDetailsBinding;

public class ResourcesDetailsActivity extends BaseActivity {

    private ActivityResourcesDetailsBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityResourcesDetailsBinding) viewBaseBinding;


    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_resources_details;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }
}