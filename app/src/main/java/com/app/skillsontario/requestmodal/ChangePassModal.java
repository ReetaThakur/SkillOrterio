package com.app.skillsontario.requestmodal;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class ChangePassModal implements Parcelable {
    Context context;
    String userId;
    String oldPass;
    String newPass;


    public ChangePassModal(Context context) {
        this.context = context;
    }

    protected ChangePassModal(Parcel in) {
        userId = in.readString();
        oldPass = in.readString();
        newPass = in.readString();
    }
    public static final Creator<ChangePassModal> CREATOR = new Creator<ChangePassModal>() {
        @Override
        public ChangePassModal createFromParcel(Parcel in) {
            return new ChangePassModal(in);
        }

        @Override
        public ChangePassModal[] newArray(int size) {
            return new ChangePassModal[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
