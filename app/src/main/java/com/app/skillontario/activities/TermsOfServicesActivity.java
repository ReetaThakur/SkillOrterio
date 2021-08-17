package com.app.skillontario.activities;


import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;

import com.app.skillorterio.databinding.ActivityTermsOfServicesBinding;

import static com.app.skillontario.utils.Utils.updatLocalLanguage;

public class TermsOfServicesActivity extends BaseActivity {

    private ActivityTermsOfServicesBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityTermsOfServicesBinding) viewBaseBinding;

        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());

        if (MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE).
                equalsIgnoreCase("fr")) {

            binding.webview.loadUrl("file:///android_asset/terms_fr.html");
        } else {
            binding.webview.loadUrl("file:///android_asset/terms.html");
        }


    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_terms_of_services;
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
                    // binding.actionBar.tvTitle.setText(R.string.change_password);
                    binding.title.setText(R.string.terms_of_services);
                    binding.d1.setText(R.string.terms_text);
                    binding.d2.setText(R.string.term1);
                    binding.d3.setText(R.string.term1);

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