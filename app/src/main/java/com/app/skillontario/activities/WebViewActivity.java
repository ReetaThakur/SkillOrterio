package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityWebViewBinding;
import com.app.skillorterio.databinding.NewsDetailAcBinding;

public class WebViewActivity extends BaseActivity {
    private ActivityWebViewBinding binding;
    String newsModal, title;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityWebViewBinding) viewBaseBinding;

        Bundle extras = getIntent().getExtras();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //finish();
            }
        });

        try {
            if (extras != null) {
                newsModal = getIntent().getStringExtra("url");
                title = getIntent().getStringExtra("title");
            }
        } catch (Exception e) {
        }

        if (newsModal != null && !newsModal.isEmpty()) {
            loadData(newsModal);
        }

        if (TextUtils.isEmpty(title)) {
            return;
        } else {
            binding.tvTitle.setText(title);
            //binding.tvTitle.setGravity(View.TEXT_ALIGNMENT_CENTER);
        }

        ///  binding.cvWebsite.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skillsontario.com"))));
    }

    private void loadData(String url) {
        binding.webview.setWebChromeClient(new WebViewActivity.MyWebChromeClient());
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
        return R.layout.activity_web_view;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
        finish();
    }

}