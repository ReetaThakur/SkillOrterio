package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.skillontario.SignIn.WelcomeActivity;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.signup.SignUpActivity;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivitySelectLanguageBinding;
import com.app.skillorterio.databinding.ActivitySelectRoleBinding;

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
