package com.app.skillontario.requestmodal;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class UpdateProfileModal implements Parcelable {
    Context context;

    String id;
    String fname;
    String lname;
    String gender;
    String school;
    String city;
    String dob;
    String email;
    String country;

    public UpdateProfileModal(Context context) {
        this.context = context;
    }

    protected UpdateProfileModal(Parcel in) {
        email = in.readString();
        id = in.readString();
        fname = in.readString();
        lname = in.readString();
        gender = in.readString();
        school = in.readString();
        city = in.readString();
        dob = in.readString();
        email = in.readString();
        country = in.readString();

    }
    public static final Creator<UpdateProfileModal> CREATOR = new Creator<UpdateProfileModal>() {
        @Override
        public UpdateProfileModal createFromParcel(Parcel in) {
            return new UpdateProfileModal(in);
        }

        @Override
        public UpdateProfileModal[] newArray(int size) {
            return new UpdateProfileModal[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
