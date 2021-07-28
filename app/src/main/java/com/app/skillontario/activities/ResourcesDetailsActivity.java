package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.models.ResourceModal;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityPrivacyPolicyBinding;
import com.app.skillorterio.databinding.ActivityResourcesDetailsBinding;
import com.bumptech.glide.Glide;

public class ResourcesDetailsActivity extends BaseActivity {

    private ActivityResourcesDetailsBinding binding;
    ResourceModal modal;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityResourcesDetailsBinding) viewBaseBinding;

      /*  binding.cvWebsite.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skillsontario.com")));
        });*/


        Bundle extras = getIntent().getExtras();

        try {
            modal = getIntent().getParcelableExtra("model");
        } catch (Exception e) {
        }

        if (modal != null) {
            if (TextUtils.isEmpty(modal.getResTitle())) {
                return;
            } else {
                binding.tvTitle.setText(modal.getResTitle());
            }


            if (TextUtils.isEmpty(modal.getResDesc())) {
                binding.tvDesc.setVisibility(View.GONE);
                return;
            } else {
                binding.tvDesc.setVisibility(View.VISIBLE);
                // binding.tvDesc1.setText(modal.getDesc());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.tvDesc.setText(Html.fromHtml(modal.getResDesc(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    binding.tvDesc.setText(Html.fromHtml(modal.getResDesc()));
                }

            }

            if (!Patterns.WEB_URL.matcher(modal.getResUrl()).matches()) {
                binding.cvWebsite.setVisibility(View.INVISIBLE);
            } else {
                binding.cvWebsite.setVisibility(View.VISIBLE);
            }


            //binding.cvWebsite.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(modal.getNewsUrl()))));

            binding.cvWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(modal.getResUrl())));
                    } catch (Exception e) {
                        showToast("Url not supported");
                    }
                }
            });

        }
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_resources_details;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }
}