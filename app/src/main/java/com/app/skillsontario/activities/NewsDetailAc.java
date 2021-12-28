package com.app.skillsontario.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.skillsontario.R;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.constants.AppConstants;
import com.app.skillsontario.databinding.NewsDetailAcBinding;
import com.app.skillsontario.models.NewsModal;
import com.app.skillsontario.utils.MySharedPreference;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.app.skillsontario.utils.Utils.updatLocalLanguage;

public class NewsDetailAc extends BaseActivity {
    private NewsDetailAcBinding binding;
    String url, title;
    NewsModal newsModal;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (NewsDetailAcBinding) viewBaseBinding;

        Bundle extras = getIntent().getExtras();

        try {
            if (extras != null) {
                url = getIntent().getStringExtra("url");
                title = getIntent().getStringExtra("title");
            }
        } catch (Exception e) {
        }

        try {
            newsModal = getIntent().getParcelableExtra("model");
        } catch (Exception e) {
        }

        if (newsModal != null) {
            if (TextUtils.isEmpty(newsModal.getNewsTitle())) {
                return;
            } else {
                binding.newsLayout.tvNewsTitle.setText(newsModal.getNewsTitle());
            }

            try {
                binding.newsLayout.tvNewsDate.setText(changeDate(newsModal.getCreatedAt()));
            } catch (Exception e) {
            }


            if (TextUtils.isEmpty(newsModal.getNewsImage())) {
                //  binding.newsLayout.imageNews.setVisibility(View.GONE);
                return;
            } else {

                try {
                    Glide.with(NewsDetailAc.this)
                            .load(newsModal.getNewsImage())
                            .centerCrop()
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    binding.newsLayout.progress.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    binding.newsLayout.progress.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(binding.newsLayout.imageNews);
                } catch (Exception e) {
                }
            }

            if (TextUtils.isEmpty(newsModal.getNewsDesc())) {
                //binding.newsLayout.tvNewsDesc.setVisibility(View.GONE);
                binding.newsLayout.webViewNewsDesc.setVisibility(View.GONE);
                return;
            } else {
                //binding.newsLayout.tvNewsDesc.setVisibility(View.VISIBLE);
                binding.newsLayout.webViewNewsDesc.setVisibility(View.VISIBLE);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.newsLayout.tvNewsDesc.setText(Html.fromHtml(newsModal.getNewsDesc(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    binding.newsLayout.tvNewsDesc.setText(Html.fromHtml(newsModal.getNewsDesc()));
                }*/
                binding.newsLayout.webViewNewsDesc.getSettings().setJavaScriptEnabled(true);
                binding.newsLayout.webViewNewsDesc.setWebViewClient(new WebViewClient() {
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
                binding.newsLayout.webViewNewsDesc.loadDataWithBaseURL(
                        "",
                        "<html>  <head><style type=\"text/css\"> @font-face {  font-family: Poppins;      src: url(\"file:///android_asset/fonts/poppins_regular.ttf\")  } </style> </head><body>" + newsModal.getNewsDesc() + "</body>",
                        "text/html",
                        "utf-8",
                        null);
                binding.newsLayout.webViewNewsDesc.setBackgroundColor(Color.TRANSPARENT);


            }

            try {
                if (!Patterns.WEB_URL.matcher(newsModal.getNewsUrl()).matches()) {
                    binding.cvWebsite.setVisibility(View.INVISIBLE);
                } else {
                    binding.cvWebsite.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                binding.cvWebsite.setVisibility(View.INVISIBLE);
            }


            //binding.cvWebsite.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(newsModal.getNewsUrl()))));

            binding.cvWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(newsModal.getNewsUrl())));
                    } catch (Exception e) {
                        showToast("Url not supported");
                    }
                }
            });

        }


        /*if (url != null && !url.isEmpty()) {
            loadData(url);
        }*/


    }

    @Override
    protected int getLayoutById() {
        return R.layout.news_detail_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String changeDate(String dateString) {
        String dateStr = "", timeStr = "", finalDate = "";
        try {

            // .//String dateString = dateN;

            String inPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
            //   String outPatternDate = "yyyy-MM-dd";
            String outPatternDate = "LLL dd, yyyy";
            String outPatternTime = "hh:mm aa";

            SimpleDateFormat inFormat = new SimpleDateFormat(inPattern, Locale.getDefault());
            SimpleDateFormat outFormat = new SimpleDateFormat(outPatternDate, Locale.getDefault());
            SimpleDateFormat outFormatTime = new SimpleDateFormat(outPatternTime, Locale.getDefault());

            try {
                Date inDate = inFormat.parse(dateString);
                dateStr = outFormat.format(inDate);
                timeStr = outFormatTime.format(inDate);

                Log.e("TEST", dateStr);
                finalDate = "" + dateStr + " | " + timeStr;
            } catch (ParseException e) {
                e.printStackTrace();
                finalDate = dateString;
            }
        } catch (Exception e) {
            e.printStackTrace();
            finalDate = dateString;
        }

        return finalDate;
    }


    @Override
    protected void onResume() {
        super.onResume();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                languageMethod(MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE));
                // binding.tv.setText(R.string.visit_website);
                try {
                    binding.newsLayout.tvNewsDate.setText(changeDate(newsModal.getCreatedAt()));
                    binding.tv.setText(R.string.visit_website);
                } catch (Exception e) {
                }
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