package com.app.skillontario.signup;

import androidx.appcompat.content.res.AppCompatResources;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.skillontario.BottomBarActivity;
import com.app.skillontario.SignIn.SignInActivity;
import com.app.skillontario.SignIn.WelcomeActivity;
import com.app.skillontario.activities.PrivacyPolicyActivity;
import com.app.skillontario.activities.TermsOfServicesActivity;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.models.RegistrationModal;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.models.SignUpModel;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivitySignUpBinding;

import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillontario.utils.Utils.getDeviceId;

public class SignUpActivity extends BaseActivity implements ApiResponseErrorCallback {

    private ActivitySignUpBinding binding;
    Drawable myIcon;
    private SignUpModel signUpModel;
    ApiResponseErrorCallback apiResponseErrorCallback;
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";
    private boolean verifyImage = false;


    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivitySignUpBinding) viewBaseBinding;
        Utils.hideKeyBoard(SignUpActivity.this);
        signUpModel = new SignUpModel(SignUpActivity.this);
        apiResponseErrorCallback = this;

        //  setLocale(SignUpActivity.this, "");

        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, false);

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

                if (Validation()) {
                    if (verifyImage) {
                        //startActivity(new Intent(SignUpActivity.this, BottomBarActivity.class));

                        String usertype = MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_TYPE);
                        if (TextUtils.isEmpty(usertype)) {
                            usertype = "2";
                        }
                        signUpModel.setEmail(binding.etMail.getText().toString().trim());
                        signUpModel.setPassword(binding.etPassword.getText().toString().trim());
                        signUpModel.setConfirmPassword(binding.etConfirmPassword.getText().toString().trim());
                        API_INTERFACE.registerUser(RequestBodyGenerator.registerUser(signUpModel, getDeviceId(SignUpActivity.this), usertype)).enqueue(
                                new ApiCallBack<>(SignUpActivity.this, apiResponseErrorCallback, 01, true));

                    } else {
                        showToast(getString(R.string.notice));
                    }

                }

            }
        });

        binding.imgEyePassword.setOnClickListener(v -> {

            if (binding.etPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                // ((ImageView(view)).setImageResource(R.drawable.hide_password);
                binding.imgEyePassword.setImageResource(R.drawable.ic_ic_image);
                //Show Password
                binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // ((ImageView) (view)).setImageResource(R.drawable.show_password);

                //Hide Password
                binding.imgEyePassword.setImageResource(R.drawable.ic_close_eye);
                binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }

        });

        binding.imgEyeConfirmPassword.setOnClickListener(v -> {

            if (binding.etConfirmPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                // ((ImageView(view)).setImageResource(R.drawable.hide_password);
                binding.imgEyeConfirmPassword.setImageResource(R.drawable.ic_ic_image);
                //Show Password
                binding.etConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // ((ImageView) (view)).setImageResource(R.drawable.show_password);

                //Hide Password
                binding.imgEyeConfirmPassword.setImageResource(R.drawable.ic_close_eye);
                binding.etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }

        });

        binding.imgVerify.setOnClickListener(v -> {
            if (verifyImage) {

                verifyImage = false;
                binding.imgVerify.setImageResource(R.drawable.ic_not_verified);
            } else {
                verifyImage = true;
                binding.imgVerify.setImageResource(R.drawable.ic_done_signup);
            }

        });

        binding.tvAgreement.setOnClickListener(v ->

                startActivity(new Intent(SignUpActivity.this, TermsOfServicesActivity.class)));

        binding.tvPrivacy.setOnClickListener(v ->

                startActivity(new Intent(SignUpActivity.this, PrivacyPolicyActivity.class)));

        binding.tvContinueAsGuest.setOnClickListener(v ->

                startActivity(new Intent(SignUpActivity.this, BottomBarActivity.class)));
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
            BaseResponseModel<RegistrationModal> responseModel = (BaseResponseModel<RegistrationModal>) responseObject;
            if (responseModel.getStatus()) {
                MySharedPreference.getInstance().setStringData(SharedPrefsConstants.USER_TOKEN, responseModel.getOutput().getToken());
                MySharedPreference.getInstance().setStringData(SharedPrefsConstants.USER_ID, responseModel.getOutput().getId());

                Intent intent = new Intent(SignUpActivity.this, BottomBarActivity.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                Utils.showToast(this, responseModel.getMessage());
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {
        Log.d("yugal error  ", t.toString());
    }

    public void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale("fr");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());
    }

    private boolean Validation() {
        if (TextUtils.isEmpty(binding.etMail.getText().toString().trim())) {
            binding.etMail.setError(getString(R.string.please_enter_email_address));
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etMail.getText().toString().trim()).matches()) {
            binding.etMail.setError(getString(R.string.please_enter_valid_email_address));
            return false;
        } else if (TextUtils.isEmpty(binding.etPassword.getText().toString().trim())) {
            binding.etPassword.setError(getString(R.string.please_enter_password));
            return false;
        } else if (TextUtils.isEmpty(binding.etConfirmPassword.getText().toString().trim())) {
            binding.etConfirmPassword.setError(getString(R.string.please_confirm_password));
            return false;
        } else if (!binding.etConfirmPassword.getText().toString().equalsIgnoreCase(binding.etPassword.getText().toString())) {
            showToast(getString(R.string.password_not_match));
            return false;
        }
        if (!verifyImage) {
            showToast(getString(R.string.term_codition));
            return false;
        }
        return true;
    }
}