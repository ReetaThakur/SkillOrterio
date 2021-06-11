package com.app.skillontario.activities;

import com.app.skillontario.adapter.NotificationAdapter;
import com.app.skillontario.adapter.PartnersAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityNotificationBinding;

public class PartnersActivity extends BaseActivity {

    private ActivityNotificationBinding binding;
    private PartnersAdapter adapter;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityNotificationBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText("Notifications");
        showNotificationRecycler();

        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());

    }

    private void showNotificationRecycler() {
       // binding.recyNotification.setHasFixedSize(true);
       // adapter = new PartnersAdapter(PartnersActivity.this);
       // binding.recyNotification.setAdapter(adapter);

       // binding.recyNotification.addOnItemTouchListener(new RecyclerItemClickListener(PartnersActivity.this, (view, position) -> {

      //  }));
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_partners;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }
}