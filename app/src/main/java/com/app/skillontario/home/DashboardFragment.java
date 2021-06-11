package com.app.skillontario.home;


import android.content.Intent;
import android.view.View;

import com.app.skillontario.activities.BookmarkAc;
import com.app.skillontario.activities.EditProfileAc;
import com.app.skillontario.activities.PartnersActivity;
import com.app.skillontario.activities.SettingActivity;
import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillontario.quiz.TakeQuizAc;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentDashboardBinding;


public class DashboardFragment extends BaseFragment {

    private FragmentDashboardBinding binding;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initUi() {
        binding = (FragmentDashboardBinding) viewDataBinding;

        binding.rlSetting.setOnClickListener(v -> startActivity(new Intent(getActivity(), SettingActivity.class)));

        binding.editProfile.setOnClickListener(v -> startActivity(new Intent(getActivity(), EditProfileAc.class)));

        binding.rlBookmark.setOnClickListener(v -> startActivity(new Intent(getActivity(), BookmarkAc.class)));

        binding.rlPatner.setOnClickListener(v -> startActivity(new Intent(getActivity(), PartnersActivity.class)));

        binding.rlTakeQuiz.setOnClickListener(v -> startActivity(new Intent(getActivity(), TakeQuizAc.class)));

    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void onClick(View view) {

    }
}
