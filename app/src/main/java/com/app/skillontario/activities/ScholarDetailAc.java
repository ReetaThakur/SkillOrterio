package com.app.skillontario.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.app.skillontario.adapter.EventAdapter;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.models.EventsModal;
import com.app.skillontario.models.RegistrationModal;
import com.app.skillontario.models.ScholarModel;
import com.app.skillontario.models.ScholarShipModal;
import com.app.skillontario.requestmodal.GetEventRequest;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ScholarDetailAcBinding;
import com.app.skillorterio.databinding.ScholarOneAcBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillontario.utils.Utils.updatLocalLanguage;

public class ScholarDetailAc extends BaseActivity implements ApiResponseErrorCallback {

    private ScholarDetailAcBinding binding;
    ScholarShipModal modal;
    String id;

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

                try {
                    Glide.with(ScholarDetailAc.this)
                            .load(modal.getImage())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    binding.progress.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    binding.progress.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(binding.image);
                } catch (Exception e) {
                }
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

        } else {
            try {
                id = getIntent().getStringExtra("id");
                callScholar(true, id);

            } catch (Exception e) {
            }
        }

        //  binding.cvVisit.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skillsontario.com"))));
    }

    void callScholar(boolean custome, String id) {
        API_INTERFACE.geteventListScholar(RequestBodyGenerator.getScholar(id)).enqueue(
                new ApiCallBack<>(ScholarDetailAc.this, this, 1013, custome));
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


    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 1013) {
            BaseResponseModel<ArrayList<ScholarModel>> responseModel = (BaseResponseModel<ArrayList<ScholarModel>>) responseObject;
            try {
                if (responseModel.getStatus()) {
                    binding.tvTitle.setText(responseModel.getOutput().get(0).getTitle());

                    try {
                        Glide.with(ScholarDetailAc.this)
                                .load(responseModel.getOutput().get(0).getImage())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        binding.progress.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        binding.progress.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(binding.image);
                    } catch (Exception e) {
                    }

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
                            binding.cvVisit.setVisibility(View.INVISIBLE);
                        } else {
                            binding.cvVisit.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        binding.cvVisit.setVisibility(View.INVISIBLE);
                    }

                    binding.cvVisit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(responseModel.getOutput().get(0).getWebUrl())));
                            } catch (Exception e) {
                                showToast("Url not supported");
                            }
                        }
                    });

                }
            } catch (Exception e) {
                // binding.progress1.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                languageMethod(MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE));
                binding.tvS.setText(R.string.visit_website);
            }

        }, 50);
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