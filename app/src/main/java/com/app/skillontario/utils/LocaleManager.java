package com.app.skillontario.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LocaleManager {
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_FRENCH = "fr";
    private static final String LANGUAGE_KEY = "Language";

    private static LocaleManager mLocaleManager;

    private LocaleManager() {
    }

    public static LocaleManager getInstance() {
        if (mLocaleManager == null) {
            mLocaleManager = new LocaleManager();
        }

        return mLocaleManager;
    }

    public Context setLocale(Context c) {
        return updateResources(c, getLanguage());
    }

    public Context setNewLocale(Context c, String language) {
        persistLanguage(language);
        return updateResources(c, language);
    }

    private void persistLanguage(String language) {
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish

       /* AppPreferences.Companion.getMIntance()
                .setStringInSharedPreference(LANGUAGE_KEY, language);*/
    }

    private Context updateResources(Context context, String language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Resources res = context.getResources();
            Configuration config = new Configuration(res.getConfiguration());
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
            return context;
        } else
            return context;
    }

    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return config.getLocales().get(0);
        } else {
            return config.locale;
        }
    }

    private String getLanguage() {
       /* String lang = AppPreferences.Companion.getMIntance()
                .getStringFromSharedPreferce(LANGUAGE_KEY);
        if (TextUtils.isEmpty(lang)) {
            lang = LANGUAGE_ENGLISH;
        }
        return lang;*/
        return LANGUAGE_ENGLISH;
    }
}
