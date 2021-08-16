package com.app.skillontario.SignIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.app.skillontario.BottomBarActivity;
import com.app.skillontario.activities.BookmarkAc;
import com.app.skillontario.activities.JobDetailsActivity;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityChangePasswordBinding;
import com.app.skillorterio.databinding.ActivityResetPasswordBinding;

import java.util.HashMap;

import static com.app.skillontario.activities.SettingActivity.language;
import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillontario.constants.AppConstants.IS_WALK_THROUGH;
import static com.app.skillontario.utils.Utils.updatLocalLanguage;

public class ChangePasswordActivity extends BaseActivity implements ApiResponseErrorCallback {

    private ActivityChangePasswordBinding binding;
    Drawable myIcon;
    ApiResponseErrorCallback apiResponseErrorCallback;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityChangePasswordBinding) viewBaseBinding;
        apiResponseErrorCallback = this;
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
                //showToast("Password saved successfully");
                //  onBackPressed();
                if (validate()) {
                    HashMap<String, Object> object = new HashMap<>();
                    object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
                    object.put("oldPass", binding.etOldPassword.getText().toString().trim());
                    object.put("newPass", binding.etNewPassword.getText().toString().trim());

                    API_INTERFACE.changePassword(object).enqueue(
                            new ApiCallBack<>(ChangePasswordActivity.this, apiResponseErrorCallback, 01, true));
                }
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

    @Override
    protected void onResume() {
        super.onResume();
        setText();
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

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 01) {
            try {
                BaseResponseModel responseModel = (BaseResponseModel) responseObject;
                if (responseModel.getStatus()) {
                    Intent intent = new Intent(ChangePasswordActivity.this, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    MySharedPreference.getInstance().clearSharedPrefs();
                    MySharedPreference.getInstance().setBooleanData(IS_WALK_THROUGH, true);
                    startActivity(intent);
                    showToast(responseModel.getMessage());

                } else {
                    showToast(responseModel.getMessage());
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }

    private boolean validate() {
        if (TextUtils.isEmpty(binding.etOldPassword.getText().toString())) {
            // binding.etOldPassword.setError(getString(R.string.old_password));
            showToast(getString(R.string.old_password));
            return false;
        } else if (TextUtils.isEmpty(binding.etNewPassword.getText().toString())) {
            showToast(getString(R.string.enter_new_password));
            //  binding.etNewPassword.setError(getString(R.string.enter_new_password));
            return false;
        } else if (TextUtils.isEmpty(binding.etConfirmPassword.getText().toString())) {
            showToast(getString(R.string.please_confirm_password));
            // binding.etConfirmPassword.setError(getString(R.string.please_confirm_password));
            return false;
        } else if (!binding.etConfirmPassword.getText().toString().equalsIgnoreCase(binding.etNewPassword.getText().toString())) {
            showToast(getString(R.string.password_not_match));
            return false;
        }
        return true;
    }

    void setText() {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    languageMethod(MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE));
                    binding.actionBar.tvTitle.setText(R.string.change_password);
                    binding.etOldPassword.setHint(R.string.old_password);
                    binding.etNewPassword.setHint(R.string.new_password);
                    binding.etConfirmPassword.setHint(R.string.confirm_password);
                } catch (Exception e) {
                }
            }
        }, 70);
    }

    private void languageMethod(String lang) {

        if (lang != null) {
            if (lang.isEmpty()) {

                updatLocalLanguage("en", getBaseContext());

            } else {

                updatLocalLanguage(lang, getBaseContext());
            }
        } else {

            updatLocalLanguage("en", getBaseContext());
        }
    }
}