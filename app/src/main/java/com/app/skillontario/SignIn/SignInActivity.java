package com.app.skillontario.SignIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.app.skillontario.BottomBarActivity;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.models.RegistrationModal;
import com.app.skillontario.models.SignUpModel;
import com.app.skillontario.signup.SignUpActivity;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivitySignInBinding;
import com.app.skillorterio.databinding.ActivitySignUpBinding;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillontario.constants.AppConstants.FIREBASE_TOKEN;
import static com.app.skillontario.constants.SharedPrefsConstants.GUEST_FLOW;
import static com.app.skillontario.utils.Utils.getDeviceId;

public class SignInActivity extends BaseActivity implements ApiResponseErrorCallback {

    private ActivitySignInBinding binding;
    Drawable myIcon;
    ApiResponseErrorCallback apiResponseErrorCallback;
    String fcm = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im11a2VzaEBtYWlsaW5hdG9yLmNvbSIsImRldmljZUlkIjoiOTk5OTk5LTAwMDAtMDAwIiwiaWF0IjoxNjI0NTMxMjQ0LCJleHAiOjE2MjcxMjMyNDR9.0L8l7y2t7msJLjXKMo0w2KMjskk2hIKcEsOZ6aG5uLM";

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivitySignInBinding) viewBaseBinding;
        apiResponseErrorCallback = this;
        binding.tvEmailError.setVisibility(View.GONE);
        binding.tvPasswordError.setVisibility(View.GONE);
        Utils.hideKeyBoard(SignInActivity.this);

        myIcon = AppCompatResources.getDrawable(SignInActivity.this, R.drawable.ic_edit_text_rectangle);

        binding.etMail.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableFocusEditText(binding.rlEmail, binding.etMail, hasFocus);
            } else {
                enableFocusEditText(binding.rlEmail, binding.etMail, hasFocus);
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

        binding.cvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  startActivity(new Intent(SignInActivity.this, BottomBarActivity.class));

                binding.tvEmailError.setVisibility(View.GONE);
                binding.tvPasswordError.setVisibility(View.GONE);
                MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, false);

                if (Validation()) {

                    String usertype = MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_TYPE);
                    if (TextUtils.isEmpty(usertype)) {
                        usertype = "2";
                    }
                    callSignInApi(binding.etMail.getText().toString().trim(), binding.etPassword.getText().toString().trim(), usertype);

                }
            }
        });

        binding.forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ResetPasswordActivity.class));
            }
        });

        binding.tvHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        // binding.tvContinueAsGuest.setOnClickListener(v -> startActivity(new Intent(SignInActivity.this, BottomBarActivity.class)));
        // binding.tvHaveAnAccount.setOnClickListener(v -> startActivity(new Intent(SignInActivity.this, SignUpActivity.class)));
        binding.tvHaveAnAccount1.setOnClickListener(v -> startActivity(new Intent(SignInActivity.this, SignUpActivity.class)));


        binding.tvContinueAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MySharedPreference.getInstance().setBooleanData(GUEST_FLOW, true);

                SignUpModel signUpModel = new SignUpModel(SignInActivity.this);
                signUpModel.setEmail("guest@gmail.com");
                signUpModel.setPassword("123456");
                signUpModel.setConfirmPassword("123456");
                API_INTERFACE.registerUser(RequestBodyGenerator.registerUser(signUpModel, getDeviceId(SignInActivity.this), "4")).enqueue(
                        new ApiCallBack<>(SignInActivity.this, apiResponseErrorCallback, 01, true));

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

    }

    void callSignInApi(String email, String pass, String usertype) {
        HashMap<String, Object> object = new HashMap<>();

        object.put("email", email);
        object.put("password", pass);
        object.put("userType", "2");
        object.put("deviceId", getDeviceId(SignInActivity.this));
        object.put("deviceType", "android");

        if (MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN) != null) {
            if (MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN).equalsIgnoreCase("")) {
                object.put("fcmToken", fcm);
            } else
                object.put("fcmToken", MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN));
        } else {
            object.put("fcmToken", fcm);
        }


        String lang = MySharedPreference.getInstance().getStringData(SharedPrefsConstants.LANGUAGE_API);
        if (TextUtils.isEmpty(lang)) {
            lang = "eng";
        }

        object.put("lang", lang);

        API_INTERFACE.loginUser(object).enqueue(
                new ApiCallBack<>(SignInActivity.this, this, 104, true));
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

    private boolean Validation() {
        if (TextUtils.isEmpty(binding.etMail.getText().toString().trim())) {
            /// binding.etMail.setError(getString(R.string.please_enter_email_address));
            binding.tvEmailError.setVisibility(View.VISIBLE);
            binding.tvEmailError.setText(getString(R.string.please_enter_email_address));
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etMail.getText().toString().trim()).matches()) {
            // binding.etMail.setError(getString(R.string.please_enter_valid_email_address));
            binding.tvEmailError.setVisibility(View.VISIBLE);
            binding.tvEmailError.setText(getString(R.string.please_enter_valid_email_address));
            return false;
        } else if (TextUtils.isEmpty(binding.etPassword.getText().toString().trim())) {
            //binding.etPassword.setError(getString(R.string.please_enter_password));

            binding.tvPasswordError.setVisibility(View.VISIBLE);
            binding.tvPasswordError.setText(getString(R.string.please_enter_password));
            return false;
        }

        return true;
    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 104) {
            BaseResponseModel<RegistrationModal> responseModel = (BaseResponseModel<RegistrationModal>) responseObject;
            try {
                if (responseModel.getStatus()) {
                    MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, true);
                    MySharedPreference.getInstance().setStringData(SharedPrefsConstants.USER_TOKEN, responseModel.getOutput().getToken());
                    MySharedPreference.getInstance().setStringData(SharedPrefsConstants.USER_ID, responseModel.getOutput().getId());
                    MySharedPreference.getInstance().SaveUserData(SharedPrefsConstants.USER_DATA, responseModel.getOutput());

                    if (MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW)) {
                        // finish();
                        if (MySharedPreference.getInstance().getStringData(SharedPrefsConstants.GUEST_FLOW_CLASS)
                                .equalsIgnoreCase("homeFragment")) {
                            Intent intent = new Intent(SignInActivity.this, BottomBarActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else if (MySharedPreference.getInstance().getStringData(SharedPrefsConstants.GUEST_FLOW_CLASS)
                                .equalsIgnoreCase("dashboardFragment")) {
                            Intent intent = new Intent(SignInActivity.this, BottomBarActivity.class);
                            intent.putExtra("if", "4");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else if (MySharedPreference.getInstance().getStringData(SharedPrefsConstants.GUEST_FLOW_CLASS)
                                .equalsIgnoreCase("BookmarkAc")) {
                            Intent intent = new Intent(SignInActivity.this, BottomBarActivity.class);
                            intent.putExtra("class", "BookmarkAc");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else if (MySharedPreference.getInstance().getStringData(SharedPrefsConstants.GUEST_FLOW_CLASS)
                                .equalsIgnoreCase("JobDetailsActivity")) {
                            Intent intent = new Intent(SignInActivity.this, BottomBarActivity.class);
                            intent.putExtra("class", "JobDetailsActivity");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(SignInActivity.this, BottomBarActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.GUEST_FLOW, false);
                } else {
                    showToast(responseModel.message);
                }
            } catch (Exception e) {

            }

        } else if (flag == 01) {
            BaseResponseModel<RegistrationModal> responseModel = (BaseResponseModel<RegistrationModal>) responseObject;
            try {
                if (responseModel.getStatus()) {
                    MySharedPreference.getInstance().setStringData(SharedPrefsConstants.USER_TOKEN, responseModel.getOutput().getToken());
                    MySharedPreference.getInstance().setStringData(SharedPrefsConstants.USER_ID, responseModel.getOutput().getId());
                    MySharedPreference.getInstance().SaveUserData(SharedPrefsConstants.USER_DATA, responseModel.getOutput());
                    MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.GUEST_FLOW, true);
                    if (MySharedPreference.getInstance().getBooleanData(GUEST_FLOW)) {

                        if (MySharedPreference.getInstance().getStringData(SharedPrefsConstants.GUEST_FLOW_CLASS)
                                .equalsIgnoreCase("homeFragment")) {
                            Intent intent = new Intent(SignInActivity.this, BottomBarActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else if (MySharedPreference.getInstance().getStringData(SharedPrefsConstants.GUEST_FLOW_CLASS)
                                .equalsIgnoreCase("dashboardFragment")) {
                            Intent intent = new Intent(SignInActivity.this, BottomBarActivity.class);
                            intent.putExtra("if", "4");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else if (MySharedPreference.getInstance().getStringData(SharedPrefsConstants.GUEST_FLOW_CLASS)
                                .equalsIgnoreCase("BookmarkAc")) {
                            Intent intent = new Intent(SignInActivity.this, BottomBarActivity.class);
                            intent.putExtra("class", "BookmarkAc");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else if (MySharedPreference.getInstance().getStringData(SharedPrefsConstants.GUEST_FLOW_CLASS)
                                .equalsIgnoreCase("JobDetailsActivity")) {
                            Intent intent = new Intent(SignInActivity.this, BottomBarActivity.class);
                            intent.putExtra("class", "JobDetailsActivity");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(SignInActivity.this, BottomBarActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.GUEST_FLOW, true);
                } else {
                    Utils.showToast(this, responseModel.getMessage());
                }
            } catch (Exception e) {
            }


        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}