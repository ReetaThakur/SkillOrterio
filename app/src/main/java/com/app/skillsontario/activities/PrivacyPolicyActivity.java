package com.app.skillsontario.activities;

import android.os.Handler;
import android.os.Looper;

import com.app.skillsontario.R;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.constants.AppConstants;
import com.app.skillsontario.databinding.ActivityPrivacyPolicyBinding;
import com.app.skillsontario.utils.MySharedPreference;


import static com.app.skillsontario.utils.Utils.updatLocalLanguage;

public class PrivacyPolicyActivity extends BaseActivity {

    private ActivityPrivacyPolicyBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityPrivacyPolicyBinding) viewBaseBinding;

        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());

       // binding.webview.loadUrl("file:///android_asset/privacy.html");

        if (MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE).
                equalsIgnoreCase("fr")) {
            binding.webview.loadUrl("file:///android_asset/privacy_fr.html");
        } else {
            binding.webview.loadUrl("file:///android_asset/privacy.html");
        }

    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_privacy_policy;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
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
                    binding.privacyTitle.setText(R.string.privacy_notice);
                    binding.privacyd1.setText(R.string.terms_text);
                    binding.privacyd2.setText(R.string.term1);
                    binding.privacyd3.setText(R.string.term1);
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