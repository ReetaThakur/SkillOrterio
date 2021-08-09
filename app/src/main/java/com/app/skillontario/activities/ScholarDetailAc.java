package com.app.skillontario.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.content.res.AppCompatResources;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.models.ScholarShipModal;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ScholarDetailAcBinding;
import com.app.skillorterio.databinding.ScholarOneAcBinding;
import com.bumptech.glide.Glide;

public class ScholarDetailAc extends BaseActivity {

    private ScholarDetailAcBinding binding;
    ScholarShipModal modal;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ScholarDetailAcBinding) viewBaseBinding;

        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());

        Bundle extras = getIntent().getExtras();

        try {
            modal = getIntent().getParcelableExtra("scholar");
        } catch (Exception e) {
        }

        if (modal != null) {
            if (TextUtils.isEmpty(modal.getTitle())) {
                return;
            } else {
                binding.tvTitle.setText(modal.getTitle());
            }


            if (TextUtils.isEmpty(modal.getImage())) {
                //  binding.newsLayout.imageNews.setVisibility(View.GONE);
                return;
            } else {
                Glide.with(this)
                        .load(modal.getImage()) // image url
                        .placeholder(R.drawable.place_holder_news)
                        .centerCrop()
                        .into(binding.image);
            }

            if (TextUtils.isEmpty(modal.getDesc())) {
                //binding.tvDesc1.setVisibility(View.GONE);
                binding.webViewNewsDesc.setVisibility(View.GONE);
                return;
            } else {
                binding.webViewNewsDesc.setVisibility(View.VISIBLE);
                // binding.tvDesc1.setText(modal.getDesc());
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.tvDesc1.setText(Html.fromHtml(modal.getDesc(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    binding.tvDesc1.setText(Html.fromHtml(modal.getDesc()));
                }*/

                binding.webViewNewsDesc.getSettings().setJavaScriptEnabled(true);
                binding.webViewNewsDesc.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                        Intent intent= new Intent(
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
                        "<html>  <head><style type=\"text/css\"> @font-face {  font-family: Poppins;      src: url(\"file:///android_asset/fonts/poppins_regular.ttf\")  } </style> </head><body>" + modal.getDesc() + "</body>",
                        "text/html",
                        "utf-8",
                        null);
                binding.webViewNewsDesc.setBackgroundColor(Color.TRANSPARENT);

            }

            try {
                if (!Patterns.WEB_URL.matcher(modal.getWebUrl()).matches()) {
                    binding.cvVisit.setVisibility(View.INVISIBLE);
                } else {
                    binding.cvVisit.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                binding.cvVisit.setVisibility(View.INVISIBLE);
            }

            //binding.cvWebsite.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(modal.getNewsUrl()))));

            binding.cvVisit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(modal.getWebUrl())));
                    } catch (Exception e) {
                        showToast("Url not supported");
                    }
                }
            });

        }

        //  binding.cvVisit.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skillsontario.com"))));
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