package com.app.skillontario.SignIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityChangePasswordBinding;
import com.app.skillorterio.databinding.ActivityResetPasswordBinding;

public class ChangePasswordActivity extends BaseActivity {

    private ActivityChangePasswordBinding binding;
    Drawable myIcon;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityChangePasswordBinding) viewBaseBinding;

        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());

        binding.actionBar.tvTitle.setText(R.string.change_password);

        myIcon = AppCompatResources.getDrawable(ChangePasswordActivity.this, R.drawable.ic_edit_text_rectangle);

        binding.etConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    enableFocusEditText(binding.rlConfirmPassword, hasFocus);
                } else {
                    enableFocusEditText(binding.rlConfirmPassword, hasFocus);
                }
            }
        });

        binding.etNewPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    enableFocusEditText(binding.rlNewPassword, hasFocus);
                } else {
                    enableFocusEditText(binding.rlNewPassword, hasFocus);
                }
            }
        });

        binding.etOldPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    enableFocusEditText(binding.rlOldPassword, hasFocus);
                } else {
                    enableFocusEditText(binding.rlOldPassword, hasFocus);
                }
            }
        });

        binding.cvSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Password saved successfully");
                onBackPressed();
            }
        });

        binding.imgEyePassword.setOnClickListener(v -> {

            if (binding.etOldPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                // ((ImageView(view)).setImageResource(R.drawable.hide_password);
                binding.imgEyePassword.setImageResource(R.drawable.ic_ic_image);
                //Show Password
                binding.etOldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // ((ImageView) (view)).setImageResource(R.drawable.show_password);

                //Hide Password
                binding.imgEyePassword.setImageResource(R.drawable.ic_close_eye);
                binding.etOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }

        });

        binding.imgNewPassword.setOnClickListener(v -> {

            if (binding.etNewPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                // ((ImageView(view)).setImageResource(R.drawable.hide_password);
                binding.imgNewPassword.setImageResource(R.drawable.ic_ic_image);
                //Show Password
                binding.etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // ((ImageView) (view)).setImageResource(R.drawable.show_password);

                //Hide Password
                binding.imgNewPassword.setImageResource(R.drawable.ic_close_eye);
                binding.etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }

        });

        binding.imgConfirmPassword.setOnClickListener(v -> {

            if (binding.etConfirmPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                // ((ImageView(view)).setImageResource(R.drawable.hide_password);
                binding.imgConfirmPassword.setImageResource(R.drawable.ic_ic_image);
                //Show Password
                binding.etConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // ((ImageView) (view)).setImageResource(R.drawable.show_password);

                //Hide Password
                binding.imgConfirmPassword.setImageResource(R.drawable.ic_close_eye);
                binding.etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }

        });


    }

    void enableFocusEditText(RelativeLayout relativeLayout, boolean val) {
        if (val)
            relativeLayout.setBackground(myIcon);
        else
            relativeLayout.setBackground(null);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_change_password;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }

}