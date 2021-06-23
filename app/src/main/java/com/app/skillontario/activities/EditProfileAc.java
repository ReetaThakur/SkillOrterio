package com.app.skillontario.activities;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.signup.SignUpActivity;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.EditProfileAcBinding;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.BalloonSizeSpec;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillontario.utils.Utils.getDeviceId;

public class EditProfileAc extends BaseActivity {
    private EditProfileAcBinding binding;
    Balloon balloon;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (EditProfileAcBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText("Edit Profile");


        binding.s1.setOnClickListener(view -> schoolClick(1));
        binding.s2.setOnClickListener(view -> schoolClick(2));
        showPopUp();

        binding.ivInfo.setOnClickListener(v -> {
            showPopUp();
            if (balloon.isShowing()) {
                balloon.dismiss();
            } else {
                balloon.showAlignBottom(binding.ivInfo);
            }
        });
        binding.done.setOnClickListener(v->{
            if (binding.etFirstName.getText().toString().trim().equals("")) {
                binding.etFirstName.setError(getString(R.string.enter_first_name));
            } else if (!(binding.etLastName.getText().toString().trim().matches(""))) {
                binding.etLastName.setError(getString(R.string.enter_last_name));
            } else {

            }
        });
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
