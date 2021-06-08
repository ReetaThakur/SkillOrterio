package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.skillontario.adapter.NotificationAdapter;
import com.app.skillontario.adapter.PopularCareerAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityNotificationBinding;
import com.app.skillorterio.databinding.ActivitySettingBinding;

public class NotificationActivity extends BaseActivity {

    private ActivityNotificationBinding binding;
    private NotificationAdapter adapter;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityNotificationBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText("Notifications");
        showNotificationRecycler();

        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());

    }

    private void showNotificationRecycler() {
        binding.recyNotification.setHasFixedSize(true);
        adapter = new NotificationAdapter(NotificationActivity.this);
        binding.recyNotification.setAdapter(adapter);

        binding.recyNotification.addOnItemTouchListener(new RecyclerItemClickListener(NotificationActivity.this, (view, position) -> {

        }));
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