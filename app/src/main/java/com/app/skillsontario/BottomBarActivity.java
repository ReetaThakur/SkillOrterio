package com.app.skillsontario;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import com.app.skillsontario.activities.BookmarkAc;
import com.app.skillsontario.activities.JobDetailsActivity;
import com.app.skillsontario.adapter.TabAdapter;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.constants.AppConstants;
import com.app.skillsontario.databinding.ActivityBottomBarBinding;
import com.app.skillsontario.home.DashboardFragment;
import com.app.skillsontario.home.EventFragment;
import com.app.skillsontario.home.HomeFragment;
import com.app.skillsontario.home.ResourcesFragment;
import com.app.skillsontario.utils.MySharedPreference;


import static com.app.skillsontario.activities.SettingActivity.language;
import static com.app.skillsontario.utils.Utils.updatLocalLanguage;

public class BottomBarActivity extends BaseActivity {

    private ActivityBottomBarBinding binding;
    private TabAdapter tabAdapter;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityBottomBarBinding) viewBaseBinding;
        if (language) {
            binding.viewPager.setVisibility(View.INVISIBLE);
            binding.bottomNavigationViewLinear.setVisibility(View.INVISIBLE);
        } else {
            binding.viewPager.setVisibility(View.VISIBLE);
            binding.bottomNavigationViewLinear.setVisibility(View.VISIBLE);
        }


        tabAdapter = new TabAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabAdapter.addFragment(new HomeFragment(), AppConstants.HOME);
        tabAdapter.addFragment(new EventFragment(), AppConstants.Tab2);
        tabAdapter.addFragment(new ResourcesFragment(), AppConstants.Tab3);
        tabAdapter.addFragment(new DashboardFragment(), AppConstants.Tab4);

        // binding.bottomNavigationViewLinear.setTypeface(Typeface.createFromAsset(getAssets(), "poppins_regular.ttf"));

        binding.bottomNavigationViewLinear.setBadgeValue(0, null);
        binding.bottomNavigationViewLinear.setBadgeValue(1, null); //invisible badge
        binding.bottomNavigationViewLinear.setBadgeValue(2, null);
        binding.bottomNavigationViewLinear.setBadgeValue(3, null);


        binding.viewPager.setAdapter(tabAdapter);
        //binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.setPagingEnabled(false);

        binding.viewPager.setOffscreenPageLimit(4);

        if (language) {
            binding.bottomNavigationViewLinear.setCurrentActiveItem(3);
        }


        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {

                binding.bottomNavigationViewLinear.setCurrentActiveItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


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

    @Override
    protected void onResume() {
        super.onResume();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                if (language) {
                    languageMethod(MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE));
                    binding.bottomNavigationViewLinear.setCurrentActiveItem(3);
                    language = false;
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.viewPager.setVisibility(View.VISIBLE);
                            binding.bottomNavigationViewLinear.setVisibility(View.VISIBLE);
                            binding.reP.setVisibility(View.GONE);
                        }
                    }, 70);

                    return;
                }

                try {
                    Intent intent = getIntent();
                    String className = intent.getStringExtra("class");
                    if (className.equalsIgnoreCase("BookmarkAc")) {
                        Intent i = new Intent(BottomBarActivity.this, BookmarkAc.class);
                        startActivity(i);

                    } else if (className.equalsIgnoreCase("JobDetailsActivity")) {
                        Intent i = new Intent(BottomBarActivity.this, JobDetailsActivity.class);
                        startActivity(i);
                    }

                    getIntent().replaceExtras(new Bundle());
                    getIntent().setAction("");
                    getIntent().setData(null);
                    getIntent().setFlags(0);
                } catch (Exception e) {
                    binding.reP.setVisibility(View.GONE);
                }

                try {
                    Intent intent = getIntent();
                    String id = intent.getStringExtra("if");

                    if (id.equalsIgnoreCase("2")) {
                        binding.bottomNavigationViewLinear.setCurrentActiveItem(1);
                    } else if (id.equalsIgnoreCase("4")) {
                        binding.bottomNavigationViewLinear.setCurrentActiveItem(3);
                    }

                    getIntent().replaceExtras(new Bundle());
                    getIntent().setAction("");
                    getIntent().setData(null);
                    getIntent().setFlags(0);
                } catch (Exception e) {
                    binding.reP.setVisibility(View.GONE);
                }

                binding.reP.setVisibility(View.GONE);
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