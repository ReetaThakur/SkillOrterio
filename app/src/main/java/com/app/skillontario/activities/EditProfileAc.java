package com.app.skillontario.activities;


import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.EditProfileAcBinding;

public class EditProfileAc extends BaseActivity {
    private EditProfileAcBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (EditProfileAcBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText("Edit Profile");
    }


    @Override
    protected int getLayoutById() {
        return R.layout.edit_profile_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

}
