package com.app.skillontario.activities;


import androidx.appcompat.content.res.AppCompatResources;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityChangePasswordBinding;
import com.app.skillorterio.databinding.ActivityFeedBackBinding;

public class FeedBackActivity extends BaseActivity {

    private ActivityFeedBackBinding binding;
    Drawable myIcon;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityFeedBackBinding) viewBaseBinding;

        binding.actionBar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.actionBar.tvTitle.setText(R.string.feedback);

        myIcon = AppCompatResources.getDrawable(FeedBackActivity.this, R.drawable.ic_edit_text_rectangle);

        binding.etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    enableFocusEditText(binding.rlEmail, hasFocus);
                } else {
                    enableFocusEditText(binding.rlEmail, hasFocus);
                }
            }
        });

        binding.etTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    enableFocusEditText(binding.rlTitle, hasFocus);
                } else {
                    enableFocusEditText(binding.rlTitle, hasFocus);
                }
            }
        });

        binding.etMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    enableFocusEditText(binding.rlMessage, hasFocus);
                } else {
                    enableFocusEditText(binding.rlMessage, hasFocus);
                }
            }
        });

        binding.cvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Send feedback");
                onBackPressed();
            }
        });
    }

    void enableFocusEditText(RelativeLayout relativeLayout, boolean val) {
        if (val)
            relativeLayout.setBackground(myIcon);
        else
            relativeLayout.setBackground(null);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }

}