package com.app.skillontario.home;


import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.app.skillontario.activities.BookmarkAc;
import com.app.skillontario.activities.EditProfileAc;
import com.app.skillontario.activities.PartnersActivity;
import com.app.skillontario.activities.SettingActivity;
import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillontario.constants.AppConstants;
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
import static com.app.skillontario.utils.Utils.updatLocalLanguage;


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
        // binding.tvUserName.setText(""); binding.dashboradSchool
        setText();

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

    void setText() {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    languageMethod(MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE));

                    binding.editProfile.setText(R.string.edit_profile);
                    binding.tv1.setText(R.string.career_quiz_results);
                    binding.tv2.setText(R.string.bookmarked_careers);
                    binding.tv3.setText(R.string.partners);
                    binding.tv4.setText(R.string.settings);
                } catch (Exception e) {
                }

                RegistrationModal registrationModal = new RegistrationModal();
                if (MySharedPreference.getInstance().getBooleanData(GUEST_FLOW)) {
                    binding.editProfile.setVisibility(View.GONE);
                    binding.tvUserName.setText(R.string.guest_user);
                    binding.dashboradSchool.setVisibility(View.GONE);

                } else {
                    binding.editProfile.setVisibility(View.VISIBLE);
                    try {
                        if (MySharedPreference.getInstance().getUserData(SharedPrefsConstants.USER_DATA) != null) {
                            registrationModal = MySharedPreference.getInstance().getUserData(SharedPrefsConstants.USER_DATA);
                            try {
                                if (registrationModal.getSchool() != null) {
                                    if (registrationModal.getSchool().equalsIgnoreCase("1")) {
                                        binding.dashboradSchool.setVisibility(View.VISIBLE);
                                        binding.dashboradSchool.setText(R.string.primary_school);
                                    } else if (registrationModal.getSchool().equalsIgnoreCase("2")) {
                                        binding.dashboradSchool.setVisibility(View.VISIBLE);
                                        binding.dashboradSchool.setText(R.string.secondary_school);
                                    } else {
                                        binding.dashboradSchool.setVisibility(View.GONE);
                                    }

                                } else
                                    binding.dashboradSchool.setVisibility(View.GONE);
                            } catch (Exception e) {

                            }
                            try {
                                if (registrationModal.getFname().equalsIgnoreCase("")) {
                                    binding.tvUserName.setText("" + registrationModal.getEmail());
                                } else
                                    binding.tvUserName.setText(capitalize("" + registrationModal.getFname() + " " + registrationModal.getLname()));

                            } catch (Exception e) {
                                binding.tvUserName.setText("" + registrationModal.getEmail());
                            }

                        }
                    } catch (Exception e) {
                    }
                }
            }
        }, 70);
    }

    private void languageMethod(String lang) {

        if (lang != null) {
            if (lang.isEmpty()) {

                updatLocalLanguage("en", getActivity());

            } else {

                updatLocalLanguage(lang, getActivity());
            }
        } else {

            updatLocalLanguage("en", getActivity());
        }
    }

}
