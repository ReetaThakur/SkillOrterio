package com.app.skillontario;


import com.app.skillontario.adapter.TabAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.constants.AppConstants;

import com.app.skillontario.home.EventFragment;
import com.app.skillontario.home.HomeFragment;
import com.app.skillontario.home.Tab3Fragment;
import com.app.skillontario.home.Tab4Fragment;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityMainBinding;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.view.View;
import android.widget.ImageView;


public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private TabAdapter tabAdapter;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityMainBinding) viewBaseBinding;

        binding.bottomNavBar.rlHome.setOnClickListener(this);
        binding.bottomNavBar.rlEvent.setOnClickListener(this);
        binding.bottomNavBar.rlTab3.setOnClickListener(this);
        binding.bottomNavBar.rlTab4.setOnClickListener(this);


        tabAdapter = new TabAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabAdapter.addFragment(new HomeFragment(), AppConstants.HOME);
        tabAdapter.addFragment(new EventFragment(), AppConstants.Tab2);
        tabAdapter.addFragment(new Tab3Fragment(), AppConstants.Tab3);
        tabAdapter.addFragment(new Tab4Fragment(), AppConstants.Tab4);

        binding.viewPager.setAdapter(tabAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.setPagingEnabled(false);
        binding.viewPager.setOffscreenPageLimit(4);
        updateBottomBar(AppConstants.HOME);

        //** TEST
        // startActivity(new Intent(this, EditProfileActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_home:
                updateBottomBar(AppConstants.HOME);
                binding.viewPager.setCurrentItem(0, true);
                break;

            case R.id.rl_event:
                updateBottomBar(AppConstants.Tab2);
                binding.viewPager.setCurrentItem(1, true);
                break;

            case R.id.rl_tab3:
                updateBottomBar(AppConstants.Tab3);
                binding.viewPager.setCurrentItem(2, true);
                break;

            case R.id.rl_tab4:
                binding.viewPager.setCurrentItem(3, true);
                updateBottomBar(AppConstants.Tab4);
                break;

        }
    }

    private void updateBottomBar(String selection) {
//        disabled icon
        binding.bottomNavBar.imgHome.clearColorFilter();
        binding.bottomNavBar.imgEvent.clearColorFilter();
        binding.bottomNavBar.imgTab3.clearColorFilter();
        binding.bottomNavBar.imgTab4.clearColorFilter();


        switch (selection) {
            case AppConstants.HOME:
                //   setTint(binding.bottomNavBar.imgHome, R.color.tab_selected);
                setBackgroundImageOnTab(0);
                break;
            case AppConstants.Tab2:
                //  setTint(binding.bottomNavBar.imgEvent, R.color.tab_selected);
                setBackgroundImageOnTab(1);
                break;
            case AppConstants.Tab3:
                // setTint(binding.bottomNavBar.imgTab3, R.color.tab_selected);
                setBackgroundImageOnTab(2);
                break;
            case AppConstants.Tab4:
                //setTint(binding.bottomNavBar.imgTab4, R.color.tab_selected);
                setBackgroundImageOnTab(3);
                break;

        }
    }

    private void setTint(ImageView imageView, int color_) {
        imageView.setColorFilter(ContextCompat.getColor(this, color_));
    }

    @Override
    public void onBackPressed() {
        if (binding.viewPager.getCurrentItem() != 0) {
            updateBottomBar(AppConstants.HOME);
            binding.viewPager.setCurrentItem(0, true);
        } else {
            super.onBackPressed();
        }
    }


    void setBackgroundImageOnTab(int pos) {
        if (pos == 0) {
            binding.bottomNavBar.lin1.setBackgroundResource(R.drawable.ic_rectangle__tab_background);
            binding.bottomNavBar.lin2.setBackgroundResource(0);
            binding.bottomNavBar.lin3.setBackgroundResource(0);
            binding.bottomNavBar.lin4.setBackgroundResource(0);

            binding.bottomNavBar.tv1.setVisibility(View.VISIBLE);
            binding.bottomNavBar.tv2.setVisibility(View.GONE);
            binding.bottomNavBar.tv3.setVisibility(View.GONE);
            binding.bottomNavBar.tv4.setVisibility(View.GONE);

            binding.bottomNavBar.imgHome.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white));
            binding.bottomNavBar.imgEvent.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_text_color));
            binding.bottomNavBar.imgTab3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_text_color));
            binding.bottomNavBar.imgTab4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_text_color));
        } else if (pos == 1) {
            binding.bottomNavBar.lin1.setBackgroundResource(0);
            binding.bottomNavBar.lin2.setBackgroundResource(R.drawable.ic_rectangle__tab_background);
            binding.bottomNavBar.lin3.setBackgroundResource(0);
            binding.bottomNavBar.lin4.setBackgroundResource(0);

            binding.bottomNavBar.tv1.setVisibility(View.GONE);
            binding.bottomNavBar.tv2.setVisibility(View.VISIBLE);
            binding.bottomNavBar.tv3.setVisibility(View.GONE);
            binding.bottomNavBar.tv4.setVisibility(View.GONE);


            binding.bottomNavBar.imgHome.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_text_color),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.bottomNavBar.imgEvent.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.bottomNavBar.imgTab3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_text_color),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.bottomNavBar.imgTab4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_text_color),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
        } else if (pos == 2) {
            binding.bottomNavBar.lin1.setBackgroundResource(0);
            binding.bottomNavBar.lin2.setBackgroundResource(0);
            binding.bottomNavBar.lin3.setBackgroundResource(R.drawable.ic_rectangle__tab_background);
            binding.bottomNavBar.lin4.setBackgroundResource(0);

            binding.bottomNavBar.tv1.setVisibility(View.GONE);
            binding.bottomNavBar.tv2.setVisibility(View.GONE);
            binding.bottomNavBar.tv3.setVisibility(View.VISIBLE);
            binding.bottomNavBar.tv4.setVisibility(View.GONE);

            binding.bottomNavBar.imgHome.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_text_color),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.bottomNavBar.imgEvent.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_text_color),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.bottomNavBar.imgTab3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.bottomNavBar.imgTab4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_text_color),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
        } else if (pos == 3) {
            binding.bottomNavBar.lin1.setBackgroundResource(0);
            binding.bottomNavBar.lin2.setBackgroundResource(0);
            binding.bottomNavBar.lin3.setBackgroundResource(0);
            binding.bottomNavBar.lin4.setBackgroundResource(R.drawable.ic_rectangle__tab_background);

            binding.bottomNavBar.tv1.setVisibility(View.GONE);
            binding.bottomNavBar.tv2.setVisibility(View.GONE);
            binding.bottomNavBar.tv3.setVisibility(View.GONE);
            binding.bottomNavBar.tv4.setVisibility(View.VISIBLE);

            binding.bottomNavBar.imgHome.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_text_color),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.bottomNavBar.imgEvent.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_text_color),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.bottomNavBar.imgTab3.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_text_color),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.bottomNavBar.imgTab4.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
        }

    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_main;
    }
}