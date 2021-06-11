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

        List<String> listings = new ArrayList<>();
        listings.add("1");
        listings.add("2");
        listings.add("3");

        List<String> listings2 = new ArrayList<>();
        listings2.add("1");
        listings2.add("2");
        listings2.add("3");
        listings2.add("2");
        listings2.add("3");
        listings2.add("3");

        List<String> listings3 = new ArrayList<>();
        listings3.add("1");
        listings3.add("2");
        listings3.add("3");
        listings3.add("3");
        listings3.add("3");

        GridLayoutManager  gridLayoutManager1 = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        binding.rvGP.setLayoutManager(gridLayoutManager1);
        binding.rvGP.setHasFixedSize(true);

        PartnersAdapter adapter = new PartnersAdapter(this,listings);
        binding.rvGP.setAdapter(adapter);

        GridLayoutManager  gridLayoutManager2 = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        binding.rvPP.setLayoutManager(gridLayoutManager2);
        binding.rvPP.setHasFixedSize(true);

        PartnersPlatinumAdapter adapter2 = new PartnersPlatinumAdapter(this,listings2);
        binding.rvPP.setAdapter(adapter2);

        GridLayoutManager  gridLayoutManager3 = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        binding.rvPLP.setLayoutManager(gridLayoutManager3);
        binding.rvPLP.setHasFixedSize(true);

        PartnersPremiumAdapter adapter3 = new PartnersPremiumAdapter(this,listings3);
        binding.rvPLP.setAdapter(adapter3);


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