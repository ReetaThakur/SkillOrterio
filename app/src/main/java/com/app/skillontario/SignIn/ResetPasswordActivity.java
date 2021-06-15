package com.app.skillontario.SignIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityResetPasswordBinding;
import com.app.skillorterio.databinding.ActivitySignInBinding;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;

public class ResetPasswordActivity extends BaseActivity {

    private ActivityResetPasswordBinding binding;
    Drawable myIcon;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityResetPasswordBinding) viewBaseBinding;

        myIcon = AppCompatResources.getDrawable(ResetPasswordActivity.this, R.drawable.ic_edit_text_rectangle);

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

        binding.cvSignIn.setOnClickListener(v -> startActivity(new Intent(ResetPasswordActivity.this, SignInActivity.class)));

    }

    private void resetPass() {
        API_INTERFACE.registerUser(RequestBodyGenerator.forgotPassword(binding.etMail.getText().toString())).enqueue(
                new ApiCallBack<>(this, new ApiResponseErrorCallback() {
                    @Override
                    public void getApiResponse(Object responseObject, int flag) {

                    }

                    @Override
                    public void getApiError(Throwable t, int flag) {

                    }
                }, 01, false));
    }

    void enableFocusEditText(RelativeLayout relativeLayout, EditText editText, boolean val) {
        if (val)
            relativeLayout.setBackground(myIcon);
        else
            relativeLayout.setBackground(null);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }

}