package com.app.skillontario.SignIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.signup.SignUpActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivitySignInBinding;
import com.app.skillorterio.databinding.ActivitySignUpBinding;

public class SignInActivity extends BaseActivity {

    private ActivitySignInBinding binding;
    Drawable myIcon;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivitySignInBinding) viewBaseBinding;

        myIcon = AppCompatResources.getDrawable(SignInActivity.this, R.drawable.ic_edit_text_rectangle);

        binding.etMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    enableFocusEditText(binding.rlEmail, binding.etMail, hasFocus);
                } else {
                    enableFocusEditText(binding.rlEmail, binding.etMail, hasFocus);
                }
            }
        });

        binding.etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    enableFocusEditText(binding.rlPassword, binding.etPassword, hasFocus);
                } else {
                    enableFocusEditText(binding.rlPassword, binding.etPassword, hasFocus);
                }
            }
        });


    }

    void enableFocusEditText(RelativeLayout relativeLayout, EditText editText, boolean val) {
        if (val)
            relativeLayout.setBackground(myIcon);
        else
            relativeLayout.setBackground(null);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_sign_in;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }

}