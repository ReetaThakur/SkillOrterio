package com.app.skillontario.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.content.res.AppCompatResources;

import com.app.skillontario.SignIn.ResetPasswordActivity;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityHelpBinding;
import com.app.skillorterio.databinding.ScholarOneAcBinding;

public class ScholarOneAc extends BaseActivity {

    private ScholarOneAcBinding binding;
    Drawable myIcon;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ScholarOneAcBinding) viewBaseBinding;

        myIcon = AppCompatResources.getDrawable(ScholarOneAc.this, R.drawable.ic_edit_text_rectangle);

        binding.rclick.setOnClickListener(v-> startActivity(new Intent(this,ScholarDetailAc.class)));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }



    @Override
    protected int getLayoutById() {
        return R.layout.scholar_one_ac;
    }


}