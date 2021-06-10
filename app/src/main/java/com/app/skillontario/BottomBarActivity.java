package com.app.skillontario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.os.Bundle;
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
import com.app.skillorterio.databinding.ActivityMainBinding;

public class BottomBarActivity extends BaseActivity {

    //private ActivityMainBinding binding;


    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
       // binding = (ActivityMainBinding) viewBaseBinding;


    }


    private void setTint(ImageView imageView, int color_) {
        imageView.setColorFilter(ContextCompat.getColor(this, color_));
    }

    @Override
    public void onBackPressed() {
      /*  if (binding.viewPager.getCurrentItem() != 0) {
            //updateBottomBar(AppConstants.HOME);
            binding.viewPager.setCurrentItem(0, true);
        } else {
            overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
            super.onBackPressed();
        }*/
    }


    @Override
    protected int getLayoutById() {
        return R.layout.activity_bottom_bar;
    }


}