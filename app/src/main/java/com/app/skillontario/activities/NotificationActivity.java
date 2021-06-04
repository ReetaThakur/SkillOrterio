package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityNotificationBinding;
import com.app.skillorterio.databinding.ActivityTermsOfServicesBinding;

public class NotificationActivity extends BaseActivity {

    private ActivityNotificationBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityNotificationBinding) viewBaseBinding;


    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_notification;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }
}