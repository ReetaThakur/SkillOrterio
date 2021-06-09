package com.app.skillontario.home;


import android.content.Intent;
import android.view.View;

import com.app.skillontario.activities.SettingActivity;
import com.app.skillontario.baseClasses.BaseFragment;
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

        binding.rlSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void onClick(View view) {

    }
}
