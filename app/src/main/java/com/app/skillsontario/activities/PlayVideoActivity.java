package com.app.skillsontario.activities;

import com.app.skillsontario.R;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.databinding.ActivityPlayVideoBinding;


import java.util.HashMap;

public class PlayVideoActivity extends BaseActivity {

    private ActivityPlayVideoBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityPlayVideoBinding) viewBaseBinding;

        //binding.actionBar.tvTitle.setText(R.string.help);
        HashMap<String, String> extraHeaders = new HashMap<>();
        extraHeaders.put("foo", "bar");
        binding.andExoPlayerView.setSource("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4", extraHeaders);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_play_video;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }
}