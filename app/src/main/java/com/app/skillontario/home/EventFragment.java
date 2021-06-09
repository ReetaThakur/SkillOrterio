package com.app.skillontario.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentEventBinding;
import com.app.skillorterio.databinding.FragmentHomeBinding;

public class EventFragment extends BaseFragment {

    private FragmentEventBinding binding;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initUi() {
        binding = (FragmentEventBinding) viewDataBinding;

        binding.tab.tv1.setOnClickListener(v->{
            setTint(binding.tab.tv1,  R.color.buttonColor);
            setTint(binding.tab.tv2, R.color.white);
            binding.eventL.setVisibility(View.VISIBLE);
            binding.newsL.setVisibility(View.GONE);
        });binding.tab.tv2.setOnClickListener(v->{
            setTint(binding.tab.tv1, R.color.white);
            setTint(binding.tab.tv2,  R.color.buttonColor);
            binding.newsL.setVisibility(View.VISIBLE);
            binding.eventL.setVisibility(View.GONE);
        });

        binding.eventL.setOnClickListener(v-> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skillsontario.com"))));
    }

    private void setTint(TextView imageView, int color_) {
           DrawableCompat.setTint(imageView.getBackground(), ContextCompat.getColor(getActivity(), color_));
           if(R.color.buttonColor==color_)
               imageView.setTextColor(getResources().getColor(R.color.white));
           else
               imageView.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_event;
    }

    @Override
    public void onClick(View view) {

    }
}