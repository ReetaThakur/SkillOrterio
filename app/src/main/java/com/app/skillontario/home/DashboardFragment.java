package com.app.skillontario.home;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.app.skillontario.activities.BookmarkAc;
import com.app.skillontario.activities.EditProfileAc;
import com.app.skillontario.activities.PartnersActivity;
import com.app.skillontario.activities.SettingActivity;
import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.models.RegistrationModal;
import com.app.skillontario.quiz.TakeQuizAc;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentDashboard1Binding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.app.skillontario.constants.SharedPrefsConstants.GUEST_FLOW;


public class DashboardFragment extends BaseFragment {
    public static TextView tvUserName;
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
        // binding.tvUserName.setText("");
        try {
            if (MySharedPreference.getInstance().getUserData(SharedPrefsConstants.USER_DATA) != null) {
                RegistrationModal registrationModal = new RegistrationModal();
                registrationModal = MySharedPreference.getInstance().getUserData(SharedPrefsConstants.USER_DATA);

                try {
                    if (registrationModal.getFname().equalsIgnoreCase("")) {
                        binding.tvUserName.setText(capitalize("" + registrationModal.getEmail()));
                    } else
                        binding.tvUserName.setText(capitalize("" + registrationModal.getFname() + " " + registrationModal.getLname()));

                } catch (Exception e) {
                    binding.tvUserName.setText(capitalize("" + registrationModal.getEmail()));
                }

            }
        } catch (Exception e) {
        }
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

    private String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }


}
