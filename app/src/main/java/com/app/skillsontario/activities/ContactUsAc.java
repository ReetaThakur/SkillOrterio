package com.app.skillsontario.activities;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.app.skillsontario.R;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.databinding.ContactUsAcBinding;


public class ContactUsAc extends BaseActivity {
    private ContactUsAcBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ContactUsAcBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText(R.string.contact_us);

        binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:5197499899"));
                    startActivity(intent);
                } catch (Exception e) {
                }

            }
        });

        binding.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@skillsontario.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "App feedback");
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    //ToastUtil.showShortToast(getActivity(), "There are no email client installed on your device.");
                } catch (Exception e) {
                }
            }
        });

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
