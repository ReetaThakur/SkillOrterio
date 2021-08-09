package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.skillontario.BottomBarActivity;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.models.ResourceModal;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityPrivacyPolicyBinding;
import com.app.skillorterio.databinding.ActivityResourcesDetailsBinding;
import com.bumptech.glide.Glide;

import static com.app.skillontario.activities.SettingActivity.language;
import static com.app.skillontario.utils.Utils.updatLocalLanguage;

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
                binding.webViewNewsDesc.setVisibility(View.GONE);
                return;
            } else {
                binding.webViewNewsDesc.setVisibility(View.VISIBLE);
                // binding.tvDesc1.setText(modal.getDesc());
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.tvDesc.setText(Html.fromHtml(modal.getResDesc(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    binding.tvDesc.setText(Html.fromHtml(modal.getResDesc()));
                }*/
                binding.webViewNewsDesc.getSettings().setJavaScriptEnabled(true);
                binding.webViewNewsDesc.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                        Intent intent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(request.getUrl().toString())
                        );
                        startActivity(intent);
                        return true;
                        //return super.shouldOverrideUrlLoading(view, request);
                    }
                });
                binding.webViewNewsDesc.loadDataWithBaseURL(
                        "",
                        "<html>  <head><style type=\"text/css\"> @font-face {  font-family: Poppins;      src: url(\"file:///android_asset/fonts/poppins_regular.ttf\")  } </style> </head><body>" + modal.getResDesc() + "</body>",
                        "text/html",
                        "utf-8",
                        null);
                binding.webViewNewsDesc.setBackgroundColor(Color.TRANSPARENT);

            }

            try {
                if (!Patterns.WEB_URL.matcher(modal.getResUrl()).matches()) {
                    binding.cvWebsite.setVisibility(View.INVISIBLE);
                } else {
                    binding.cvWebsite.setVisibility(View.VISIBLE);
                }


            } catch (Exception e) {
                binding.cvWebsite.setVisibility(View.INVISIBLE);
            }


            //binding.cvWebsite.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(modal.getNewsUrl()))));

            binding.cvWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (modal.getResUrl().contains("http:") || modal.getResUrl().contains("https:")) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(modal.getResUrl())));
                        }

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

    @Override
    protected void onResume() {
        super.onResume();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                languageMethod(MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE));
                binding.tv.setText(R.string.visit_website);
            }
        }, 70);
    }

    private void languageMethod(String lang) {

        if (lang != null) {
            if (lang.isEmpty()) {

                updatLocalLanguage("en", getBaseContext());

            } else {

                updatLocalLanguage(lang, getBaseContext());
            }
        } else {

            updatLocalLanguage("en", getBaseContext());
        }
    }

}