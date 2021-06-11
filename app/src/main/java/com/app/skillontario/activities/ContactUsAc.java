package com.app.skillontario.activities;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ContactUsAcBinding;

public class ContactUsAc extends BaseActivity {
    private ContactUsAcBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ContactUsAcBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText("Contact Us");


    }

    @Override
    protected int getLayoutById() {
        return R.layout.contact_us_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

}
