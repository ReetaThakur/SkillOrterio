package com.app.skillontario.activities;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.dialogs.DialogWithMsg;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.EditProfileAcBinding;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.BalloonSizeSpec;

public class EditProfileAc extends BaseActivity {
    private EditProfileAcBinding binding;
    Balloon balloon;
    DialogWithMsg dialogWithMsg;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (EditProfileAcBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText("Edit Profile");


        binding.s1.setOnClickListener(view -> schoolClick(1));
        binding.s2.setOnClickListener(view -> schoolClick(2));
        showPopUp();

        binding.ivInfo.setOnClickListener(v -> {
          /*  showPopUp();
            if (balloon.isShowing()) {
                balloon.dismiss();
            } else {
                balloon.showAlignBottom(binding.ivInfo);
            }*/

            showDilog();
        });

        binding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showToast("Profile saved");
                onBackPressed();
            }
        });

    }

    void showDilog() {
        dialogWithMsg = new DialogWithMsg(EditProfileAc.this, 0, "", getString(R.string.demo_), "OK", null);
        dialogWithMsg.show();
    }

    void showPopUp() {
        balloon = new Balloon.Builder(EditProfileAc.this)
                .setArrowSize(10)
                .setArrowOrientation(ArrowOrientation.TOP)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                .setArrowPosition(0.5f)
                .setWidth(BalloonSizeSpec.WRAP)
                .setHeight(BalloonSizeSpec.WRAP)
                .setTextSize(14f)
                .setCornerRadius(20f)
                .setPadding(16)
                .setTextGravity(10)
                .setMarginRight(16)
                .setMarginLeft(16)
                .setText("Diversity, inclusion, and representation are top priorities for Skills Ontario. We work with communities across the province to ensure that we are providing equitable opportunities for all youth and building a diverse and robust skilled workforce. To better understand our impact and reach")
                .setTextColor(ContextCompat.getColor(EditProfileAc.this, R.color.black))
                .setTextIsHtml(false)
                .setBackgroundColor(ContextCompat.getColor(EditProfileAc.this, R.color.white))
                .setBalloonAnimation(BalloonAnimation.FADE)
                .build();
    }

    private void schoolClick(int i) {
        if (i == 1) {
            binding.s1.setTextColor(getResources().getColor(R.color.white));
            binding.s2.setTextColor(getResources().getColor(R.color.black));
            binding.s1.getBackground().setColorFilter(Color.parseColor("#34C759"), PorterDuff.Mode.SRC_ATOP);
            binding.s2.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
        } else {
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
