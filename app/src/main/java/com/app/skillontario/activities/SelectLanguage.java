package com.app.skillontario.activities;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivitySelectLanguageBinding;

import java.util.Locale;

public class SelectLanguage extends BaseActivity {

    private ActivitySelectLanguageBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivitySelectLanguageBinding) viewBaseBinding;

        binding.imageViewEnglish.setOnClickListener(v -> {
            changeLocale("en",this);
            startActivity(new Intent(SelectLanguage.this, SelectRoleActivity.class));
            finishAffinity();
        });

        binding.imageViewFrench.setOnClickListener(v -> {
            changeLocale("fr",this);
            startActivity(new Intent(SelectLanguage.this, SelectRoleActivity.class));
            finishAffinity();
        });
    }

    public static void changeLocale(final String lang, Context context) {
        try {
            if (lang.equalsIgnoreCase(""))
                return;
            Locale myLocale = new Locale(lang);//Set Selected Locale
            MySharedPreference.getInstance().setStringData(AppConstants.LANGUAGE, lang);
            Locale.setDefault(myLocale);//set new locale as default
            Configuration config = new Configuration();//get Configuration
            config.locale = myLocale;//set config locale as selected locale
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());//Update the config

        } catch (Exception er) {
            er.getMessage();
        }
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_select_language;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }
    
    
}