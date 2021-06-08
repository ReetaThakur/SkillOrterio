package com.app.skillontario.activities;

import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ScholarDetailAcBinding;
import com.app.skillorterio.databinding.ScholarOneAcBinding;

public class ScholarDetailAc extends BaseActivity {

    private ScholarDetailAcBinding binding;
    Drawable myIcon;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ScholarDetailAcBinding) viewBaseBinding;

        myIcon = AppCompatResources.getDrawable(ScholarDetailAc.this, R.drawable.ic_edit_text_rectangle);
    }

   


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }



    @Override
    protected int getLayoutById() {
        return R.layout.scholar_detail_ac;
    }


}