package com.app.skillontario.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ScholarDetailAcBinding;
import com.app.skillorterio.databinding.ScholarOneAcBinding;

public class ScholarDetailAc extends BaseActivity {

    private ScholarDetailAcBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ScholarDetailAcBinding) viewBaseBinding;

        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());


        binding.cvVisit.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skillsontario.com"))));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }


    @Override
    protected int getLayoutById() {
        return R.layout.scholar_detail_ac;
    }


}