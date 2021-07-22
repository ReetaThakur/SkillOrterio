package com.app.skillontario.SignIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityResetPasswordBinding;

import java.util.HashMap;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;

public class ResetPasswordActivity extends BaseActivity implements ApiResponseErrorCallback {

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

        binding.cvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation()) {
                    HashMap<String, Object> object = new HashMap<>();
                    object.put("email", binding.etMail.getText().toString().trim());
                    resetPass(object);
                }
            }
        });

    }

    private void resetPass(HashMap<String, Object> object) {
        API_INTERFACE.forgotPassword(object).enqueue(
                new ApiCallBack<>(ResetPasswordActivity.this, this, 105, true));
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

    private boolean Validation() {
        if (TextUtils.isEmpty(binding.etMail.getText().toString().trim())) {
            binding.etMail.setError(getString(R.string.please_enter_email_address));
            return false;
        } else if (!(binding.etMail.getText().toString().trim().matches(Utils.emailPattern))) {
            binding.etMail.setError(getString(R.string.please_enter_valid_email_address));
            return false;
        }
        return true;
    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 105) {
            BaseResponseModel responseModel = (BaseResponseModel) responseObject;
            try {
                if (responseModel.getStatus()) {
                    showToast(responseModel.getMessage());
                    finish();
                }
            } catch (Exception e) {
            }

        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }
}