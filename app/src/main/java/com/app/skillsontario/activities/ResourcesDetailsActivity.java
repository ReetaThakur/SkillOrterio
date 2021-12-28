package com.app.skillsontario.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.skillsontario.R;
import com.app.skillsontario.apiConnection.ApiCallBack;
import com.app.skillsontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillsontario.apiConnection.RequestBodyGenerator;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.baseClasses.BaseResponseModel;
import com.app.skillsontario.constants.AppConstants;
import com.app.skillsontario.databinding.ActivityResourcesDetailsBinding;
import com.app.skillsontario.models.ResourceModal;
import com.app.skillsontario.models.ScholarModel;
import com.app.skillsontario.utils.MySharedPreference;


import static com.app.skillsontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillsontario.utils.Utils.updatLocalLanguage;

import java.util.ArrayList;

public class ResourcesDetailsActivity extends BaseActivity implements ApiResponseErrorCallback {

    private ActivityResourcesDetailsBinding binding;
    ResourceModal modal;
    boolean callFrom = false;
    String url = "";

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityResourcesDetailsBinding) viewBaseBinding;

        Bundle extras = getIntent().getExtras();

        try {
            modal = getIntent().getParcelableExtra("model");
        } catch (Exception e) {
        }

        if (modal != null) {
            callFrom = false;
            if (TextUtils.isEmpty(modal.getResTitle())) {

            } else {
                binding.tvTitle.setText(modal.getResTitle());
            }


            if (TextUtils.isEmpty(modal.getResDesc())) {
                binding.webViewNewsDesc.setVisibility(View.GONE);

            } else {
                binding.webViewNewsDesc.setVisibility(View.VISIBLE);

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
        } else {
            try {
                String id = getIntent().getStringExtra("id");
                callScholar(true, id);
                callFrom = true;
            } catch (Exception e) {
            }
        }

        binding.cvWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!callFrom) {
                        if (modal.getResUrl().contains("http:") || modal.getResUrl().contains("https:")) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(modal.getResUrl())));
                        }
                    } else {
                        if (url.contains("http:") || url.contains("https:")) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(modal.getResUrl())));
                        }
                    }

                } catch (Exception e) {
                    showToast("Url not supported");
                }
            }
        });
    }

    void callScholar(boolean custome, String id) {
        API_INTERFACE.geteventListScholar(RequestBodyGenerator.getRecources(id)).enqueue(
                new ApiCallBack<>(ResourcesDetailsActivity.this, this, 1018, custome));
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

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 1018) {
            BaseResponseModel<ArrayList<ScholarModel>> responseModel = (BaseResponseModel<ArrayList<ScholarModel>>) responseObject;
            try {
                if (responseModel.getStatus()) {

                    binding.tvTitle.setText(responseModel.getOutput().get(0).getTitle());
                    url = responseModel.getOutput().get(0).getWebUrl();

                    try {
                        binding.webViewNewsDesc.setVisibility(View.VISIBLE);

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
                                "<html>  <head><style type=\"text/css\"> @font-face {  font-family: Poppins;      src: url(\"file:///android_asset/fonts/poppins_regular.ttf\")  } </style> </head><body>" + responseModel.getOutput().get(0).getDesc() + "</body>",
                                "text/html",
                                "utf-8",
                                null);
                        binding.webViewNewsDesc.setBackgroundColor(Color.TRANSPARENT);
                    } catch (Exception e) {
                    }

                    try {
                        if (!Patterns.WEB_URL.matcher(responseModel.getOutput().get(0).getWebUrl()).matches()) {
                            binding.cvWebsite.setVisibility(View.INVISIBLE);
                        } else {
                            binding.cvWebsite.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        binding.cvWebsite.setVisibility(View.INVISIBLE);
                    }
                }
            } catch (Exception e) {
            }
        }

    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }
}