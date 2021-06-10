package com.app.skillontario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.app.skillontario.adapter.TabAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.home.DashboardFragment;
import com.app.skillontario.home.EventFragment;
import com.app.skillontario.home.HomeFragment;
import com.app.skillontario.home.ResourcesFragment;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityBottomBarBinding;
import com.app.skillorterio.databinding.ActivityMainBinding;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

public class BottomBarActivity extends BaseActivity {

    private ActivityBottomBarBinding binding;
    private TabAdapter tabAdapter;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityBottomBarBinding) viewBaseBinding;


        tabAdapter = new TabAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabAdapter.addFragment(new HomeFragment(), AppConstants.HOME);
        tabAdapter.addFragment(new EventFragment(), AppConstants.Tab2);
        tabAdapter.addFragment(new ResourcesFragment(), AppConstants.Tab3);
        tabAdapter.addFragment(new DashboardFragment(), AppConstants.Tab4);

        binding.bottomNavigationViewLinear.setTypeface(Typeface.createFromAsset(getAssets(), "poppins_regular.ttf"));

        binding.bottomNavigationViewLinear.setBadgeValue(0, null);
        binding.bottomNavigationViewLinear.setBadgeValue(1, null); //invisible badge
        binding.bottomNavigationViewLinear.setBadgeValue(2, null);
        binding.bottomNavigationViewLinear.setBadgeValue(3, null);


        binding.viewPager.setAdapter(tabAdapter);
        //binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.setPagingEnabled(false);

        binding.viewPager.setOffscreenPageLimit(4);

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                binding.bottomNavigationViewLinear.setBadgeValue(0, null);
                binding.bottomNavigationViewLinear.setBadgeValue(1, null); //invisible badge
                binding.bottomNavigationViewLinear.setBadgeValue(2, null);
                binding.bottomNavigationViewLinear.setBadgeValue(3, null);
                binding.bottomNavigationViewLinear.setCurrentActiveItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

      //  binding.viewPager.setOnTouchListener((v, event) -> true);


        binding.bottomNavigationViewLinear.setNavigationChangeListener((view, position) -> binding.viewPager.setCurrentItem(position, true));
    }


    private void setTint(ImageView imageView, int color_) {
        imageView.setColorFilter(ContextCompat.getColor(this, color_));
    }

    @Override
    public void onBackPressed() {
        if (binding.viewPager.getCurrentItem() != 0) {
            //updateBottomBar(AppConstants.HOME);
            binding.viewPager.setCurrentItem(0, true);
        } else {
            overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
            super.onBackPressed();
        }
    }


    @Override
    protected int getLayoutById() {
        return R.layout.activity_bottom_bar;
    }


}