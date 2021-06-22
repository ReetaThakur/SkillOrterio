package com.app.skillontario.apiConnection;

import android.annotation.SuppressLint;
import android.provider.Settings;

import com.app.skillontario.baseClasses.AppController;
import com.app.skillontario.models.SignUpModel;
import com.app.skillontario.utils.MySharedPreference;

import java.util.ArrayList;
import java.util.HashMap;

import static com.app.skillontario.constants.AppConstants.FIREBASE_TOKEN;
import static com.app.skillontario.constants.SharedPrefsConstants.USER_ID;


public class RequestBodyGenerator {

    @SuppressLint("HardwareIds")
    public static HashMap<String, Object> loginUser(String email, String password) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("email", email);
        object.put("password", password);
        object.put("device_id", Settings.Secure.getString(AppController.context.getContentResolver(), Settings.Secure.ANDROID_ID));
        object.put("firebase_token", MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN));

        return object;
    }

    public static HashMap<String, Object> registerUser(SignUpModel signUpModel) {
        HashMap<String, Object> object = new HashMap<>();

        object.put("email", "mk70@mailinator.com");
        object.put("password", "mk@123");
        object.put("fname", "");
        object.put("lname", "");
        object.put("gender", "");

        object.put("school", "");
        object.put("city", "");
        object.put("province", "");
        object.put("dob", "1970-01-01");
        object.put("deviceType", "IOS");
        object.put("deviceId", "999999-0000-000");
        object.put("userType", "2");
        object.put("notifyStatus", "1");
        object.put("terms", "1");
        object.put("fcmToken", "SSDDFFGGG");
        object.put("status", "");

        return object;
    }


    public static HashMap<String, Object> forgotPassword(String email) {
        HashMap<String, Object> object = new HashMap<>();
        object.put("email", email);
        return object;
    }

    public static HashMap<String, Object> userID() {
        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(USER_ID));
        return object;
    }

    public static HashMap<String, Object> changePassword(String password, String oldPassword) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(USER_ID));
        object.put("currPassword", oldPassword);
        object.put("newPassword", password);

        return object;
    }

    public static HashMap<String, Object> getCatalogue(String catalogueId, int page) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(USER_ID));
        object.put("catalogueId", catalogueId);
        object.put("page", page);
        return object;
    }

    public static HashMap<String, Object> uploadImage(int catalogueId, int lotId) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(USER_ID));
        object.put("catalogueId", catalogueId);
        object.put("lotId", lotId);
        return object;
    }

    public static HashMap<String, Object> getMiscList(int page) {
        HashMap<String, Object> object = new HashMap<>();
        object.put("page", page);
        return object;
    }

    public static HashMap<String, Object> getList(String pickup, String condition, String keyword) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("conditionId", condition);
        object.put("pickupId", pickup);
        object.put("keywordId", keyword);
        object.put("page", "1");
        return object;
    }

    public static HashMap<String, Object> createCatalogue(String auctionId, String catalogueName, String cataloguersName, String email) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(USER_ID));
        object.put("auctionId", auctionId);
        object.put("catalogueName", catalogueName);
        object.put("cataloguersName", cataloguersName);
        object.put("streetNumber", "");
        object.put("streetName", "");
        object.put("email", email);

        return object;
    }

}
