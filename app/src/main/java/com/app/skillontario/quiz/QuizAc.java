package com.app.skillontario.quiz;

import androidx.fragment.app.FragmentStatePagerAdapter;
import com.app.skillontario.adapter.TabAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.QuizAcBinding;

public class QuizAc extends BaseActivity {
    private TabAdapter tabAdapter;
    private QuizAcBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (QuizAcBinding) viewBaseBinding;

        setAdapter();
    }

    private void setAdapter() {
        tabAdapter = new TabAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabAdapter.addFragment(new QuizChildFragment("1"), "");
        tabAdapter.addFragment(new QuizChildFragment("2"), "");
        tabAdapter.addFragment(new QuizChildFragment("3"), "");
        tabAdapter.addFragment(new QuizChildFragment("4"), "");
        // tabAdapter.addFragment(new GetStartedFr("5"), "");
        // tabAdapter.addFragment(new GetStartedFr("6"), "");


        binding.viewPager.setAdapter(tabAdapter);
       // binding.tab.setupWithViewPager(binding.viewPager, true);

       /*
        binding.viewPager.setPagingEnabled(false);
        binding.viewPager.setOffscreenPageLimit(4);
        */
    }


    @Override
    protected int getLayoutById() {
        return R.layout.quiz_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }
}