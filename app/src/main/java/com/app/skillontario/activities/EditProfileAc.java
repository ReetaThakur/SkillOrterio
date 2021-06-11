package com.app.skillontario.activities;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.Toast;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.EditProfileAcBinding;

public class EditProfileAc extends BaseActivity {
    private EditProfileAcBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (EditProfileAcBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText("Edit Profile");

        binding.ivInfo.setOnClickListener(v-> Toast.makeText(this,
                "Diversity, inclusion, and representation are top priorities for Skills Ontario. We work with communities across the province to ensure that we are providing equitable opportunities for all youth and building a diverse and robust skilled workforce. To better understand our impact and reach",
                Toast.LENGTH_LONG).show());

binding.s1.setOnClickListener(view -> schoolClick(1));
binding.s2.setOnClickListener(view -> schoolClick(2));

    }

    private void schoolClick(int i) {
        if(i==1){
            binding.s1.setTextColor(getResources().getColor(R.color.white));
            binding.s2.setTextColor(getResources().getColor(R.color.black));
            binding.s1.getBackground().setColorFilter(Color.parseColor("#34C759"), PorterDuff.Mode.SRC_ATOP);
            binding.s2.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
        }else {
            binding.s1.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
            binding.s2.getBackground().setColorFilter(Color.parseColor("#34C759"), PorterDuff.Mode.SRC_ATOP);
            binding.s1.setTextColor(getResources().getColor(R.color.black));
            binding.s2.setTextColor(getResources().getColor(R.color.white));
        }
    }


    @Override
    protected int getLayoutById() {
        return R.layout.edit_profile_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

}
