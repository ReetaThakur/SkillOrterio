package com.app.skillontario.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.app.skillontario.utils.Validations;
import com.app.skillorterio.R;

/**
 * Created by Gaurav on 20-11-2019.
 */
public class SignUpModel implements Parcelable {

    private String email = "";
    private String password = "";
    private String confirmPassword = "";
    private String mobile = "";
    private String userName = "";
    private Context context;
    private boolean isFaceIdActive;

    public SignUpModel(Context context) {
        this.context = context;
    }

    protected SignUpModel(Parcel in) {
        email = in.readString();
        password = in.readString();
        confirmPassword = in.readString();
        mobile = in.readString();
        userName = in.readString();
        isFaceIdActive = in.readByte() != 0;
    }

    public static final Creator<SignUpModel> CREATOR = new Creator<SignUpModel>() {
        @Override
        public SignUpModel createFromParcel(Parcel in) {
            return new SignUpModel(in);
        }

        @Override
        public SignUpModel[] newArray(int size) {
            return new SignUpModel[size];
        }
    };

    public String getEmail() {
        try {
            return email.toLowerCase();
        } catch (Exception e) {
            return "";
        }

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    public String isValidate(Context context) {
        if (TextUtils.isEmpty(getEmail()))
            return context.getResources().getString(R.string.empty_email);
        else if (!Validations.validateEmailID(getEmail(), context, R.string.invalid_email))
            return context.getResources().getString(R.string.invalid_email);
        else if (TextUtils.isEmpty(getUserName()))
            return context.getResources().getString(R.string.empty_username);
        else if (TextUtils.isEmpty(getPassword()))
            return context.getResources().getString(R.string.empty_password);
        else if (!Validations.validatePasswordFields(getPassword(), context, R.string.invalid_password))
            return context.getResources().getString(R.string.invalid_password);
        else if (TextUtils.isEmpty(getConfirmPassword()))
            return context.getResources().getString(R.string.empty_password);
        else if (!Validations.validatePasswordFields(getConfirmPassword(), context, R.string.invalid_password))
            return context.getResources().getString(R.string.invalid_password);
        else if (!getPassword().equals(getConfirmPassword())) {
            return context.getResources().getString(R.string.invalid_password_match);
        } else
            return "Success";
    }

    public String isValidLogin() {
        if (TextUtils.isEmpty(getUserName()))
            return context.getResources().getString(R.string.empty_email);
        else if (TextUtils.isEmpty(getPassword()))
            return context.getResources().getString(R.string.empty_password);
        /*else if (!Validations.validatePasswordFields(getPassword(), context, R.string.invalid_login_password))
            return context.getResources().getString(R.string.invalid_login_password);*/
        else
            return "Success";

    }

    public boolean isFaceIdActive() {
        return isFaceIdActive;
    }

    public void setFaceIdActive(boolean faceIdActive) {
        isFaceIdActive = faceIdActive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(confirmPassword);
        parcel.writeString(mobile);
        parcel.writeString(userName);
        parcel.writeByte((byte) (isFaceIdActive ? 1 : 0));
    }


}
