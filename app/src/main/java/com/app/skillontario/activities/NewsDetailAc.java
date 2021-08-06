package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.models.NewsModal;
import com.app.skillontario.quiz.QuizStepAc;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.NewsDetailAcBinding;
import com.app.skillorterio.databinding.TakeQuizAcBinding;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsDetailAc extends BaseActivity {
    private NewsDetailAcBinding binding;
    String url, title;
    NewsModal newsModal;

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

            binding.newsLayout.tvNewsDate.setText(changeDate(newsModal.getNewsDate()));

            if (TextUtils.isEmpty(newsModal.getNewsImage())) {
                //  binding.newsLayout.imageNews.setVisibility(View.GONE);
                return;
            } else {
                Glide.with(this)
                        .load(newsModal.getNewsImage()) // image url
                        .placeholder(R.drawable.place_holder_news)
                        .centerCrop()
                        .into(binding.newsLayout.imageNews);
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
                binding.newsLayout.webViewNewsDesc.setWebViewClient(new WebViewClient(){
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
                binding.newsLayout.webViewNewsDesc.loadDataWithBaseURL(
                        "",
                        "<html>  <head><style type=\"text/css\"> @font-face {  font-family: Poppins;      src: url(\"file:///android_asset/fonts/poppins_regular.ttf\")  } </style> </head><body>" + newsModal.getNewsDesc() + "</body>",
                        "text/html",
                        "utf-8",
                        null);
                binding.newsLayout.webViewNewsDesc.setBackgroundColor(Color.TRANSPARENT);
               // binding.newsLayout.webViewNewsDesc.setOnLongClickListener(false);
               // binding.newsLayout.webViewNewsDesc.isLongClickable()


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

    private void loadData(String url) {
        binding.webview.setWebChromeClient(new MyWebChromeClient());
        binding.webview.getSettings().setLoadWithOverviewMode(true);
        binding.webview.getSettings().setSupportZoom(true);
        binding.webview.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = binding.webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        binding.webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
// Do something
            }
        });
        binding.webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

// runs when a page starts loading
                binding.pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

// page finishes loading
                binding.pb.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

// runs when there's a failure in loading page
                binding.pb.setVisibility(View.GONE);
                showToast("Failure on loading web page");
            }
        });
        binding.webview.loadUrl(url);
    }

    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {

        }

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

    public String changeDate(String dateString) {
        String dateStr = "", timeStr = "", finalDate = "";
        try {

            // .//String dateString = dateN;

            String inPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
            //   String outPatternDate = "yyyy-MM-dd";
            String outPatternDate = "LLL-dd-yyyy";
            String outPatternTime = "HH:mm aa";

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

}