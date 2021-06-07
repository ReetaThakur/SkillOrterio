package com.app.skillontario.home;


import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.app.skillontario.adapter.KeywordsAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.callbacks.KeywordSelected;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityHomeFilterBinding;

import java.util.ArrayList;

public class HomeFilterActivity extends BaseActivity implements KeywordSelected {

    private ActivityHomeFilterBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityHomeFilterBinding) viewBaseBinding;

        binding.actionBar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.actionBar.tvTitle.setText(R.string.set_your_filter);

        ArrayList<String> keywordsList = new ArrayList<>();
        keywordsList.add("Construction");
        keywordsList.add("Industrial");
        keywordsList.add("Manufacturing & Engineering");
        keywordsList.add("Services");
        keywordsList.add("Information Technology");
        keywordsList.add("Bachelor of Technology");
        keywordsList.add("Option 01");


        binding.rcvKeywords.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false));
        binding.rcvKeywords.setAdapter(new KeywordsAdapter(this, this, keywordsList));

        binding.rcvKeywordsTraining.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false));
        binding.rcvKeywordsTraining.setAdapter(new KeywordsAdapter(this, this, keywordsList));

        binding.rcvKeywordsEducation.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false));
        binding.rcvKeywordsEducation.setAdapter(new KeywordsAdapter(this, this, keywordsList));

    }


    @Override
    protected int getLayoutById() {
        return R.layout.activity_home_filter;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }

    @Override
    public void onTextClick(String text) {

    }
}