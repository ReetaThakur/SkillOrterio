package com.app.skillontario.activities;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

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


        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());

        binding.id1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skillsontario.com")));
            }
        });

        binding.id2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skillsontario.com")));
            }
        });

        binding.id3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skillsontario.com")));
            }
        });

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