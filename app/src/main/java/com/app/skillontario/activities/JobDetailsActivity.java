package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.app.skillontario.adapter.ExpandRecyclerAdapter;
import com.app.skillontario.adapter.NotificationAdapter;
import com.app.skillontario.adapter.VideoAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.callbacks.ScrollViewListener;
import com.app.skillontario.expandRecycler.SampleChildBean;
import com.app.skillontario.expandRecycler.SampleGroupBean;
import com.app.skillontario.utils.ObservableScrollView;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityJobDetailsBinding;
import com.app.skillorterio.databinding.ActivitySettingBinding;

import java.util.ArrayList;
import java.util.List;

public class JobDetailsActivity extends BaseActivity {

    private ActivityJobDetailsBinding binding;
    ExpandRecyclerAdapter adapter;
    private boolean clickBookMark = false;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityJobDetailsBinding) viewBaseBinding;

        binding.imgBack.setOnClickListener(v -> onBackPressed());
        // binding.obs.setScrollViewListener(this::onScrollChanged);
        showExpandRecycler();

        VideoAdapter videoAdapter = new VideoAdapter(JobDetailsActivity.this);
        binding.recyVideo.setHasFixedSize(true);
        binding.recyVideo.setNestedScrollingEnabled(false);
        binding.recyVideo.setAdapter(videoAdapter);

       /* binding.recyVideo.addOnItemTouchListener(new RecyclerItemClickListener(JobDetailsActivity.this, (view, position) -> {

        }));*/

        binding.tvsSroll.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (binding.tvsSroll.getChildAt(0).getBottom()
                                <= (binding.tvsSroll.getHeight() + binding.tvsSroll.getScrollY())+binding.tvsSroll.getScrollY()) {
                            //scroll view is at bottom

                            changeColor(binding.imgBookmark, true);
                            changeColor(binding.imgShare, true);
                            changeColor(binding.imgBack, true);
                        } else {
                            //scroll view is not at bottom
                            changeColor(binding.imgBack, false);
                            changeColor(binding.imgBookmark, false);
                            changeColor(binding.imgShare, false);

                        }
                    }
                });


        binding.imgBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickBookMark) {
                    clickBookMark = false;
                    binding.imgBookmark.setImageResource(R.drawable.ic_job_uppar1);
                } else {
                    clickBookMark = true;
                    binding.imgBookmark.setImageResource(R.drawable.job_bookmark_click);
                }
            }
        });

        binding.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareCompat.IntentBuilder.from(JobDetailsActivity.this)
                        .setType("text/plain")
                        .setChooserTitle(getResources().getText(R.string.app_name))
                        .setText("https://www.skillsontario.com")
                        .startChooser();
            }
        });

      //  changeColor(binding.imgBack, false);
    }


    private void showExpandRecycler() {

        List<SampleGroupBean> list = new ArrayList<>(10);
        final List<SampleChildBean> childList = new ArrayList<>(1);
        childList.add(new SampleChildBean(getString(R.string.child)));

        list.add(new SampleGroupBean(childList, "Job Description"));
        list.add(new SampleGroupBean(childList, "Job Responsibilities"));
        list.add(new SampleGroupBean(childList, "Where they Work"));
        list.add(new SampleGroupBean(childList, "Education/Training Required"));


        binding.recyExpand.setHasFixedSize(true);
        binding.recyExpand.setNestedScrollingEnabled(false);
        adapter = new ExpandRecyclerAdapter(list);
        binding.recyExpand.setAdapter(adapter);

        binding.recyExpand.addOnItemTouchListener(new RecyclerItemClickListener(JobDetailsActivity.this, (view, position) -> {

        }));
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_job_details;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    void changeColor(ImageView imageView, boolean black) {
        if (black)
            imageView.setColorFilter(ContextCompat.getColor(JobDetailsActivity.this, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
        else
            imageView.setColorFilter(ContextCompat.getColor(JobDetailsActivity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
    }
}