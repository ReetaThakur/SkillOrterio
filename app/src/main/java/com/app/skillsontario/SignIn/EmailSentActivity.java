package com.app.skillsontario.SignIn;


import android.content.Intent;
import android.view.View;

import com.app.skillsontario.R;
import com.app.skillsontario.apiConnection.ApiCallBack;
import com.app.skillsontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.baseClasses.BaseResponseModel;
import com.app.skillsontario.databinding.ActivityEmailSentBinding;


import java.util.HashMap;

import static com.app.skillsontario.SignIn.ResetPasswordActivity.recoveryEmail;
import static com.app.skillsontario.constants.ApiConstants.API_INTERFACE;

public class EmailSentActivity extends BaseActivity implements ApiResponseErrorCallback {

    private ActivityEmailSentBinding binding;


    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityEmailSentBinding) viewBaseBinding;


        binding.cvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailSentActivity.this, SignInActivity.class));
                finishAffinity();
            }
        });

        binding.tvResendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> object = new HashMap<>();
                object.put("email", recoveryEmail);
                resetPass(object);
            }
        });

        binding.tvResendMail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> object = new HashMap<>();
                object.put("email", recoveryEmail);
                resetPass(object);

            }
        });

    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_email_sent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }

    private void resetPass(HashMap<String, Object> object) {
        API_INTERFACE.forgotPassword(object).enqueue(
                new ApiCallBack<>(EmailSentActivity.this, this, 114, true));
    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 114) {
            BaseResponseModel responseModel = (BaseResponseModel) responseObject;
            try {
                if (responseModel.getStatus()) {
                    showToast(responseModel.getMessage());
                    // startActivity(new Intent(ResetPasswordActivity.this, EmailSentActivity.class));
                }
            } catch (Exception e) {
            }

        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }
}