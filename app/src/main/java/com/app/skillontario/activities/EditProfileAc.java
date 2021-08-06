package com.app.skillontario.activities;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.app.skillontario.BottomBarActivity;
import com.app.skillontario.callbacks.OnYearSelectInterface;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.requestmodal.UpdateProfileModal;
import com.app.skillontario.utils.DropDownBottomSheet;
import com.app.skillorterio.R;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillorterio.databinding.EditProfileAcBinding;
import com.app.skillontario.dialogs.DialogWithMsg;
import com.app.skillontario.models.RegistrationModal;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.BalloonSizeSpec;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.app.skillontario.activities.SettingActivity.language;
import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillontario.home.DashboardFragment.tvUserName;
import static com.app.skillontario.utils.Utils.updatLocalLanguage;


public class EditProfileAc extends BaseActivity implements ApiResponseErrorCallback, OnYearSelectInterface {
    private EditProfileAcBinding binding;
    Balloon balloon;
    DialogWithMsg dialogWithMsg;
    OnYearSelectInterface onYearSelectInterface;
    int day;
    int month;
    int year;
    RegistrationModal registrationModal;
    ApiResponseErrorCallback apiResponseErrorCallback;
    UpdateProfileModal updateProfileModal;
    String gender = "1";
    String school = "1";
    String inputDateStr = "";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initUi() {

        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (EditProfileAcBinding) viewBaseBinding;
        onYearSelectInterface = this;
        binding.actionBar.tvTitle.setText(R.string.Edit_Profile1);
        apiResponseErrorCallback = this;
        binding.tvYears.setText("");
        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, true);
        registrationModal = MySharedPreference.getInstance().getUserData(SharedPrefsConstants.USER_DATA);
        binding.s1.setOnClickListener(view -> schoolClick(1));
        binding.s2.setOnClickListener(view -> schoolClick(2));
        binding.bt1.setOnClickListener(v -> CheckGender(1));
        binding.bt2.setOnClickListener(v -> CheckGender(2));
        updateProfileModal = new UpdateProfileModal(this);
        //  showPopUp();

        binding.ivInfo.setOnClickListener(v -> {
            //  showPopUp();
            showDilog();

        });

        binding.done.setOnClickListener(v -> {
            if (validate()) {
                updateProfileModal.setId(MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
                updateProfileModal.setFname(binding.etFirstName.getText().toString().trim());
                updateProfileModal.setLname(binding.etLastName.getText().toString().trim());
                updateProfileModal.setGender(gender);
                updateProfileModal.setEmail(binding.etEmail.getText().toString().trim());
                updateProfileModal.setSchool(school);
                updateProfileModal.setDob(binding.tvYears.getText().toString().trim());
                updateProfileModal.setCity(binding.etWindsor.getText().toString());
                updateProfileModal.setCountry(binding.etCountry.getText().toString());
                API_INTERFACE.updateProfile(RequestBodyGenerator.updateProfile(updateProfileModal)).enqueue(
                        new ApiCallBack<>(EditProfileAc.this, apiResponseErrorCallback, 01, true));
            }
        });
        setData();

        binding.layoutYearSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DropDownBottomSheet bottomSheet = new DropDownBottomSheet(EditProfileAc.this, 1, onYearSelectInterface);
                bottomSheet.show(getSupportFragmentManager(), "");
            }
        });

        binding.tvYears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DropDownBottomSheet bottomSheet = new DropDownBottomSheet(EditProfileAc.this, 1, onYearSelectInterface);
                bottomSheet.show(getSupportFragmentManager(), "");
            }
        });
    }

    void setText() {
        binding.etFirstName.setHint(R.string.enter_first_name);
        binding.etLastName.setHint(R.string.enter_last_name);
        binding.etEmail.setHint(R.string.enter_your_email);
        binding.tvYears.setHint(R.string.select_age);
        binding.etWindsor.setHint(R.string.select_city);
        binding.etCountry.setHint(R.string.select_pro);


    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                languageMethod(MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE));
                setText();

            }
        }, 50);
    }

    void showDilog() {
        dialogWithMsg = new DialogWithMsg(EditProfileAc.this, 0, "", getString(R.string.demo_), getString(R.string.ok_got_it), null);
        dialogWithMsg.show();
    }


    void setData() {

        if (registrationModal.getFname() != null) {
            if (!registrationModal.getFname().isEmpty() && !registrationModal.getFname().equalsIgnoreCase("null")) {
                binding.etFirstName.setText(registrationModal.getFname());
            }
        }
        if (registrationModal.getLname() != null) {
            if (!registrationModal.getLname().isEmpty() && !registrationModal.getLname().equalsIgnoreCase("null")) {
                binding.etLastName.setText(registrationModal.getLname());
            }
        }
        if (registrationModal.getEmail() != null) {
            if (!registrationModal.getEmail().isEmpty() && !registrationModal.getEmail().equalsIgnoreCase("null")) {
                binding.etEmail.setText(registrationModal.getEmail());
            }
        }
        if (registrationModal.getCity() != null) {
            if (!registrationModal.getCity().isEmpty() && !registrationModal.getCity().equalsIgnoreCase("null")) {
                binding.etWindsor.setText(registrationModal.getCity());
            }
        }
        if (registrationModal.getCountry() != null) {
            if (!registrationModal.getCountry().isEmpty() && !registrationModal.getCountry().equalsIgnoreCase("null")) {
                binding.etCountry.setText(registrationModal.getCountry());
            }
        }
        if (registrationModal.getSchool() != null) {
            if (!registrationModal.getSchool().isEmpty() && !registrationModal.getSchool().equalsIgnoreCase("null")) {
                schoolClick(Integer.parseInt(registrationModal.getSchool()));
            }
        }
        if (registrationModal.getGender() != null) {
            if (!registrationModal.getGender().isEmpty() && !registrationModal.getGender().equalsIgnoreCase("null")) {
                CheckGender(Integer.parseInt(registrationModal.getGender()));
            }
        }
        if (registrationModal.getDob() != null) {
            if (!registrationModal.getDob().isEmpty() && !registrationModal.getDob().equalsIgnoreCase("null")) {
                binding.tvYears.setText(registrationModal.getDob());
            }
        }
    }


    private void schoolClick(int i) {
        if (i == 1) {
            binding.s1.setTextColor(getResources().getColor(R.color.white));
            binding.s2.setTextColor(getResources().getColor(R.color.black));
            binding.s1.getBackground().setColorFilter(Color.parseColor("#34C759"), PorterDuff.Mode.SRC_ATOP);
            binding.s2.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
            school = "1";
        } else {
            binding.s1.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
            binding.s2.getBackground().setColorFilter(Color.parseColor("#34C759"), PorterDuff.Mode.SRC_ATOP);
            binding.s1.setTextColor(getResources().getColor(R.color.black));
            binding.s2.setTextColor(getResources().getColor(R.color.white));
            school = "2";
        }
    }

    private void CheckGender(int i) {
        if (i == 1) {
            binding.bt1.setChecked(true);
            gender = "1";
        } else {
            binding.bt2.setChecked(true);
            gender = "2";
        }

    }

    @Override
    protected int getLayoutById() {
        return R.layout.edit_profile_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }


    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String DateFormate(String str_date) {
        String newDateString = "2021-06-22 11:11 AM";
        //2021-10-14T00:00:00.000Z
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat format_set = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        try {
            Date date = format.parse(str_date);
            Date date1 = format_set.parse(str_date);

            format = new SimpleDateFormat("MMM dd yyyy");
            format_set = new SimpleDateFormat("YYYY-MM-dd");

            newDateString = format.format(date);
            ;
            inputDateStr = format_set.format(date1);
            ;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateString;
    }

    private boolean validate() {
        if (TextUtils.isEmpty(binding.etFirstName.getText().toString())) {
            binding.etFirstName.setError(getString(R.string.enter_first_name));
            return false;
        } else if (TextUtils.isEmpty(binding.etLastName.getText().toString())) {
            binding.etLastName.setError(getString(R.string.enter_last_name));
            return false;
        } else if (TextUtils.isEmpty(binding.etEmail.getText().toString())) {
            binding.etEmail.setError(getString(R.string.please_enter_email_address));
            return false;
        } else if (!(binding.etEmail.getText().toString().trim().matches(Utils.emailPattern))) {
            binding.etEmail.setError(getString(R.string.please_enter_valid_email_address));
            return false;
        } else if (TextUtils.isEmpty(binding.tvYears.getText().toString())) {//valid_dob
            showToast(getString(R.string.valid_dob));
            return false;
        } else if (TextUtils.isEmpty(binding.etWindsor.getText().toString())) {
            binding.etWindsor.setError(getString(R.string.city_town_or_post_office));
            return false;
        } else if (TextUtils.isEmpty(binding.etCountry.getText().toString())) {
            binding.etCountry.setError(getString(R.string.country_required));
            return false;
        }
        return true;
    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 01) {
            BaseResponseModel<RegistrationModal> responseModel = (BaseResponseModel<RegistrationModal>) responseObject;
            if (responseModel.getStatus()) {
                MySharedPreference.getInstance().SaveUserData(SharedPrefsConstants.USER_DATA, responseModel.getOutput());
                //tvUserName.setText(binding.etFirstName.getText().toString() + " " + binding.etLastName.getText().toString());

                showToast(responseModel.getMessage());
                finish();
            } else {
                Utils.showToast(this, responseModel.getMessage());
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }

    @Override
    public void onTextClick(String text) {
        binding.tvYears.setText(text);
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
