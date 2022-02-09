package com.app.skillsontario.activities;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import com.app.skillsontario.R;
import com.app.skillsontario.SignIn.WelcomeActivity;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.baseClasses.CrashLogger;
import com.app.skillsontario.constants.AppConstants;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.ActivitySelectLanguageBinding;
import com.app.skillsontario.utils.MySharedPreference;


import java.util.Locale;

public class SelectLanguage extends BaseActivity {

    private ActivitySelectLanguageBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivitySelectLanguageBinding) viewBaseBinding;

        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.GUEST_FLOW, false);

        binding.imageViewEnglish.setOnClickListener(v -> {

            try {
                CrashLogger.INSTANCE.trackEventsFirebase("English_Language_Select", "SelectLanguageActivity");
            } catch (Exception e) {
            }

            MySharedPreference.getInstance().setStringData(SharedPrefsConstants.LANGUAGE_API, "eng");
            changeLocale("en", this);
            startActivity(new Intent(SelectLanguage.this, WelcomeActivity.class));
            finishAffinity();
        });

        binding.imageViewFrench.setOnClickListener(v -> {
            try {
                CrashLogger.INSTANCE.trackEventsFirebase("French_Language_Select", "SelectLanguageActivity");
            } catch (Exception e) {
            }

            MySharedPreference.getInstance().setStringData(SharedPrefsConstants.LANGUAGE_API, "fre");
            changeLocale("fr", this);
            startActivity(new Intent(SelectLanguage.this, WelcomeActivity.class));
            finishAffinity();


        });
    }

    public static void changeLocale(String lang, Context context) {
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