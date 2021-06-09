package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.skillontario.adapter.ExpandRecyclerAdapter;
import com.app.skillontario.adapter.NotificationAdapter;
import com.app.skillontario.adapter.VideoAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.expandRecycler.SampleChildBean;
import com.app.skillontario.expandRecycler.SampleGroupBean;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityJobDetailsBinding;
import com.app.skillorterio.databinding.ActivitySettingBinding;

import java.util.ArrayList;
import java.util.List;

public class JobDetailsActivity extends BaseActivity {

    private ActivityJobDetailsBinding binding;
    ExpandRecyclerAdapter adapter;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityJobDetailsBinding) viewBaseBinding;

        binding.imgBack.setOnClickListener(v -> onBackPressed());

        showExpandRecycler();

        VideoAdapter videoAdapter = new VideoAdapter();
        binding.recyVideo.setHasFixedSize(true);
        binding.recyVideo.setNestedScrollingEnabled(false);
        binding.recyVideo.setAdapter(videoAdapter);

        binding.recyVideo.addOnItemTouchListener(new RecyclerItemClickListener(JobDetailsActivity.this, (view, position) -> {

        }));
    }

    private void showExpandRecycler() {

        List<SampleGroupBean> list = new ArrayList<>(10);
        final List<SampleChildBean> childList = new ArrayList<>(1);
        childList.add(new SampleChildBean(getString(R.string.child)));

        list.add(new SampleGroupBean(childList, "Job Description"));
        list.add(new SampleGroupBean(childList, "Job Responsibilities"));
        list.add(new SampleGroupBean(childList, "Where they Work"));
        list.add(new SampleGroupBean(childList, "Advice"));
        list.add(new SampleGroupBean(childList, "Education Required"));
        list.add(new SampleGroupBean(childList, "Education Required"));
        list.add(new SampleGroupBean(childList, "Education Required"));
        list.add(new SampleGroupBean(childList, "Education Required"));
        list.add(new SampleGroupBean(childList, "Education Required"));
        list.add(new SampleGroupBean(childList, "Education Required"));

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
}