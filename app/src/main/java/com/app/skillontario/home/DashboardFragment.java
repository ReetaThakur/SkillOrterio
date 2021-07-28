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
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentDashboard1Binding;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;

public class DashboardFragment extends BaseFragment {

    private FragmentDashboard1Binding binding;
    boolean User_Type = false;
    public  static TextView tvUserName;
    public DashboardFragment() {
        // Required empty public constructor
    }
    RegistrationModal registrationModal;

    @Override
    protected void initUi() {
        binding = (FragmentDashboard1Binding) viewDataBinding;
        User_Type= MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW);
        binding.rlSetting.setOnClickListener(v -> startActivity(new Intent(getActivity(), SettingActivity.class)));
        registrationModal = MySharedPreference.getInstance().getUserData(SharedPrefsConstants.USER_DATA);
        tvUserName= binding.tvUserName;
        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_Type = MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW);
                if (!User_Type) {
                    startActivity(new Intent(getActivity(), EditProfileAc.class));
                } else {
                    try {
                        Utils.guestMethod(getActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        binding.rlBookmark.setOnClickListener(v -> {
            User_Type = MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW);
            if (!User_Type) {
                startActivity(new Intent(getActivity(), BookmarkAc.class));
            } else {
                try {
                    Utils.guestMethod(getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        binding.rlPatner.setOnClickListener(v -> startActivity(new Intent(getActivity(), PartnersActivity.class)));

        binding.rlTakeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_Type = MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW);
                if (!User_Type) {
                    startActivity(new Intent(getActivity(), TakeQuizAc.class));
                } else {
                    try {
                        Utils.guestMethod(getActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        setData();
    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_dashboard1;
    }

    @Override
    public void onClick(View view) {

    }

    void setData() {
        if(registrationModal.getFname() != null ) {
            if (!registrationModal.getFname().isEmpty() && !registrationModal.getFname().equalsIgnoreCase("null")) {
                binding.tvUserName.setText(registrationModal.getFname()+" "+registrationModal.getLname());
            }else  if (!registrationModal.getEmail().isEmpty() && !registrationModal.getEmail().equalsIgnoreCase("null")) {
                binding.tvUserName.setText(registrationModal.getEmail());
            }
        }else  if(registrationModal.getEmail() != null ) {
            if (!registrationModal.getEmail().isEmpty() && !registrationModal.getEmail().equalsIgnoreCase("null")) {
                binding.tvUserName.setText(registrationModal.getEmail());
            }
        }

        if(registrationModal.getSchool() != null) {
            if (!registrationModal.getSchool().isEmpty() && !registrationModal.getSchool().equalsIgnoreCase("null")) {
                if(Integer.parseInt(registrationModal.getSchool())==0){
                    binding.tvSchoolName.setText(R.string.elementary_school);
                }else {
                    binding.tvSchoolName.setText(R.string.high_school);
                }
            }
        }
    }

}
