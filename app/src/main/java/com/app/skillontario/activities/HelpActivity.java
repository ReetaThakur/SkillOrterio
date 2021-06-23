package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityHelpBinding;
import com.app.skillorterio.databinding.ActivityTermsOfServicesBinding;

public class HelpActivity extends BaseActivity {

    private ActivityHelpBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityHelpBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText(R.string.help);


        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_help;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }
}