package com.app.skillsontario.activities;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.appcompat.content.res.AppCompatResources;

import com.app.skillsontario.R;
import com.app.skillsontario.apiConnection.ApiCallBack;
import com.app.skillsontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.baseClasses.BaseResponseModel;
import com.app.skillsontario.baseClasses.CrashLogger;
import com.app.skillsontario.constants.AppConstants;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.ActivityFeedBackBinding;
import com.app.skillsontario.models.FeedbackModal;
import com.app.skillsontario.utils.MySharedPreference;


import java.util.HashMap;

import static com.app.skillsontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillsontario.utils.Utils.updatLocalLanguage;

public class FeedBackActivity extends BaseActivity implements ApiResponseErrorCallback {

    private ActivityFeedBackBinding binding;
    Drawable myIcon;
    ApiResponseErrorCallback apiResponseErrorCallback;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityFeedBackBinding) viewBaseBinding;
        apiResponseErrorCallback = this;
        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());

      /*  binding.actionBar.tvTitle.setText(R.string.feedback);
        binding.etEmail.setText(R.string.email_address_feedback);
        binding.etTitle.setText(R.string.title);
        binding.etMessage.setText(R.string.message);
        binding.tvCon.setText(R.string.confirm1);*/

        myIcon = AppCompatResources.getDrawable(FeedBackActivity.this, R.drawable.ic_edit_text_rectangle);

        binding.etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    enableFocusEditText(binding.rlEmail, hasFocus);
                } else {
                    enableFocusEditText(binding.rlEmail, hasFocus);
                }
            }
        });

        binding.etTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    enableFocusEditText(binding.rlTitle, hasFocus);
                } else {
                    enableFocusEditText(binding.rlTitle, hasFocus);
                }
            }
        });

        binding.etMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    enableFocusEditText(binding.rlMessage, hasFocus);
                } else {
                    enableFocusEditText(binding.rlMessage, hasFocus);
                }
            }
        });

        binding.cvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    try {
                        Dialog dialog = new Dialog(FeedBackActivity.this, android.R.style.Theme_Light);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_feedback);

                        dialog.findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HashMap<String, Object> object = new HashMap<>();
                                object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
                                object.put("email", binding.etEmail.getText().toString().trim().toLowerCase());
                                object.put("title", binding.etTitle.getText().toString().trim());
                                object.put("message", binding.etMessage.getText().toString().trim());
                                CallApi(object);
                                dialog.dismiss();

                                try {
                                    CrashLogger.INSTANCE.trackEventsFirebase("Users_Giving_The_Feedback", "FeedBackActivity");
                                }catch (Exception e){}
                            }
                        });
                        dialog.show();
                    } catch (Exception e) {
                        onBackPressed();
                    }
                }

            }
        });
    }

    private boolean validation() {
        if (TextUtils.isEmpty(binding.etEmail.getText().toString().trim())) {
            binding.etEmail.setError(getString(R.string.please_enter_email_address));
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString().trim()).matches()) {
            binding.etEmail.setError(getString(R.string.please_enter_valid_email_address));
            return false;
        } else if (TextUtils.isEmpty(binding.etTitle.getText().toString().trim())) {
            binding.etTitle.setError(getString(R.string.empty_title));
            return false;
        } else if (TextUtils.isEmpty(binding.etMessage.getText().toString().trim())) {
            binding.etMessage.setError(getString(R.string.message));
            return false;
        }
        return true;

    }

    void enableFocusEditText(RelativeLayout relativeLayout, boolean val) {
        if (val)
            relativeLayout.setBackground(myIcon);
        else
            relativeLayout.setBackground(null);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }

    private void CallApi(HashMap<String, Object> object) {
        API_INTERFACE.Sendfeedback(object).enqueue(
                new ApiCallBack<>(FeedBackActivity.this, apiResponseErrorCallback, 10, true));

    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 10) {

            BaseResponseModel<FeedbackModal> responseModel = (BaseResponseModel<FeedbackModal>) responseObject;
            if (responseModel.getStatus()) {
                finish();
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        setText();
    }

    void setText() {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    languageMethod(MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE));
                    binding.actionBar.tvTitle.setText(R.string.feedback);
                    binding.etEmail.setHint(R.string.email_address_feedback);
                    binding.etTitle.setHint(R.string.title);
                    binding.etMessage.setHint(R.string.message);
                    binding.con.setText(R.string.confirm);

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