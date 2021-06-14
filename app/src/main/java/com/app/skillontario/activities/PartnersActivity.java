package com.app.skillontario.activities;

import com.app.skillontario.adapter.NotificationAdapter;
import com.app.skillontario.adapter.PartnersAdapter;
import com.app.skillontario.adapter.PartnersPlatinumAdapter;
import com.app.skillontario.adapter.PartnersPremiumAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityNotificationBinding;
import com.app.skillorterio.databinding.ActivityPartnersBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;

public class PartnersActivity extends BaseActivity {

    private ActivityPartnersBinding binding;
    private PartnersAdapter adapter;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityPartnersBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText("Partners");
        showRecycler();

        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());

    }

    private void showRecycler() {






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