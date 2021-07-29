package com.app.skillontario.activities;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.text.TextUtils;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.app.skillontario.requestmodal.UpdateProfileModal;
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

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillontario.home.DashboardFragment.tvUserName;


public class EditProfileAc extends BaseActivity implements ApiResponseErrorCallback {
    private EditProfileAcBinding binding;
    Balloon balloon;
    DialogWithMsg dialogWithMsg;

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

        binding.actionBar.tvTitle.setText(R.string.Edit_Profile1);
        apiResponseErrorCallback = this;
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
            if (balloon.isShowing()) {
                balloon.dismiss();
            } else {
                balloon.showAlignBottom(binding.ivInfo);
            }
        });

        binding.done.setOnClickListener(v -> {
            if (validate()) {
                updateProfileModal.setId(MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
                updateProfileModal.setFname(binding.etFirstName.getText().toString());
                updateProfileModal.setLname(binding.etLastName.getText().toString());
                updateProfileModal.setGender(gender);
                updateProfileModal.setEmail(binding.etEmail.getText().toString());
                updateProfileModal.setSchool(school);
                updateProfileModal.setDob(inputDateStr);
                updateProfileModal.setCity(binding.etWindsor.getText().toString());
                updateProfileModal.setCountry(binding.etCountry.getText().toString());
                API_INTERFACE.updateProfile(RequestBodyGenerator.updateProfile(updateProfileModal)).enqueue(
                        new ApiCallBack<>(EditProfileAc.this, apiResponseErrorCallback, 01, true));
            }
        });
        setData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void setData() {
        binding.tvDob.setOnClickListener(v -> showCalender());
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
                binding.tvDob.setText(DateFormate(registrationModal.getDob()));
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

    private void showCalender() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("NewApi")
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // inputDateStr = year + "-" + (monthOfYear + 1)+ "-" +  dayOfMonth ;
                        String inputDate = year + "-" + dayOfMonth + "-" + (monthOfYear + 1);

                        DateFormat inputFormat = new SimpleDateFormat("yyyy-dd-mm");
                        DateFormat inputFormatset = new SimpleDateFormat("YYYY-mm-dd");
                        DateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy");
                        String outputDateStr = "";
                        Date date = null;
                        Date date1 = null;
                        try {
                            date = inputFormat.parse(inputDate);
                            date1 = inputFormat.parse(inputDate);
                            outputDateStr = outputFormat.format(date);
                            inputDateStr = inputFormatset.format(date1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        binding.tvDob.setText(outputDateStr);


                    }
                }, year, month, day);
        datePickerDialog.show();
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
        } else if (TextUtils.isEmpty(binding.tvDob.getText().toString())) {//valid_dob
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
}
