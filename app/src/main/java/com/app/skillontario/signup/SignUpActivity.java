package com.app.skillontario.signup;

import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.app.skillontario.BottomBarActivity;
import com.app.skillontario.SignIn.SignInActivity;
import com.app.skillontario.activities.TakeQuizActivity;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.models.SignUpModel;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivitySignUpBinding;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;

public class SignUpActivity extends BaseActivity implements ApiResponseErrorCallback {

    private ActivitySignUpBinding binding;
    Drawable myIcon;
    private SignUpModel signUpModel;
    ApiResponseErrorCallback apiResponseErrorCallback;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivitySignUpBinding) viewBaseBinding;
        signUpModel = new SignUpModel(SignUpActivity.this);
        apiResponseErrorCallback = this;

        myIcon = AppCompatResources.getDrawable(SignUpActivity.this, R.drawable.ic_edit_text_rectangle);
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

        binding.etConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    enableFocusEditText(binding.rlConfirmPassword, binding.etConfirmPassword, hasFocus);
                } else {
                    enableFocusEditText(binding.rlConfirmPassword, binding.etConfirmPassword, hasFocus);
                }
            }
        });

        binding.tvSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        binding.tvHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        binding.cvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(SignUpActivity.this, BottomBarActivity.class));
                //  startActivity(new Intent(SignUpActivity.this, TakeQuizActivity.class));

                signUpModel.setEmail(binding.etMail.getText().toString().trim());
                signUpModel.setPassword(binding.etPassword.getText().toString().trim());
                signUpModel.setConfirmPassword(binding.etConfirmPassword.getText().toString().trim());

                API_INTERFACE.registerUser(RequestBodyGenerator.registerUser(signUpModel)).enqueue(
                        new ApiCallBack<>(SignUpActivity.this, apiResponseErrorCallback, 01, false));
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
        return R.layout.activity_sign_up;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 01) {

        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }
}