package com.app.skillsontario.activities;

import android.content.Intent;

import com.app.skillsontario.R;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.ActivitySelectRoleBinding;
import com.app.skillsontario.signup.SignUpActivity;
import com.app.skillsontario.utils.MySharedPreference;


public class SelectRoleActivity extends BaseActivity {

    private ActivitySelectRoleBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivitySelectRoleBinding) viewBaseBinding;

        binding.imageViewParent.setOnClickListener(v->{

                MySharedPreference.getInstance().setStringData(SharedPrefsConstants.USER_TYPE, "3");
                startActivity(new Intent(SelectRoleActivity.this, SignUpActivity.class));
        });



        binding.imageViewStudent.setOnClickListener(v-> {

                MySharedPreference.getInstance().setStringData(SharedPrefsConstants.USER_TYPE, "2");
                startActivity(new Intent(SelectRoleActivity.this, SignUpActivity.class));
        });

    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_select_role;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }
}
