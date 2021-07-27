package com.app.skillontario.home;


import android.content.Intent;
import android.view.View;

import com.app.skillontario.activities.BookmarkAc;
import com.app.skillontario.activities.EditProfileAc;
import com.app.skillontario.activities.PartnersActivity;
import com.app.skillontario.activities.SettingActivity;
import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.quiz.TakeQuizAc;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentDashboard1Binding;

import static com.app.skillontario.constants.SharedPrefsConstants.GUEST_FLOW;


public class DashboardFragment extends BaseFragment {

    private FragmentDashboard1Binding binding;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initUi() {
        binding = (FragmentDashboard1Binding) viewDataBinding;

        binding.rlSetting.setOnClickListener(v -> startActivity(new Intent(getActivity(), SettingActivity.class)));

        binding.editProfile.setOnClickListener(v -> startActivity(new Intent(getActivity(), EditProfileAc.class)));

        binding.rlPatner.setOnClickListener(v -> startActivity(new Intent(getActivity(), PartnersActivity.class)));


        binding.rlTakeQuiz.setOnClickListener(v -> {
            if (!MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW)) {
                startActivity(new Intent(getActivity(), TakeQuizAc.class));
            } else {
                try {
                    Utils.guestMethod(getActivity(), "dashboardFragment");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        binding.rlBookmark.setOnClickListener(v -> {
            if (!MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW)) {
                startActivity(new Intent(getActivity(), BookmarkAc.class));
            } else {
                try {
                    Utils.guestMethod(getActivity(), "dashboardFragment");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (MySharedPreference.getInstance().getBooleanData(GUEST_FLOW)) {
            binding.editProfile.setVisibility(View.GONE);
        } else {
            binding.editProfile.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_dashboard1;
    }

    @Override
    public void onClick(View view) {

    }
}
