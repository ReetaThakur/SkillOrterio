package com.app.skillontario.activities;



import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivitySelectLanguageBinding;

public class SelectLanguage extends BaseActivity {

    private ActivitySelectLanguageBinding binding;

    @Override
    protected void initUi() {
        binding = (ActivitySelectLanguageBinding) viewBaseBinding;
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_select_language;
    }
}