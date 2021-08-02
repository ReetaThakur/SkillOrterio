package com.app.skillontario.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.skillontario.baseClasses.AppController;
import com.app.skillontario.models.CareerModal;
import com.app.skillontario.models.RegistrationModal;
import com.google.gson.Gson;

import java.util.ArrayList;


public class MySharedPreference {

    private static MySharedPreference object;
    private static final String MyPREFERENCES = "SkillsOntarioPrefs";
    private SharedPreferences sharedpreferences;


    public static MySharedPreference getInstance() {
        if (object == null) {
            object = new MySharedPreference(AppController.context);
        }
        return object;
    }

    private MySharedPreference(Context mContext) {
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }


    //************************** getter setter for string data ***************************
    public void setStringData(String key, String value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringData(String key) {
        return sharedpreferences.getString(key, "");
    }

    public String getStringDataDoc(String key) {
        return sharedpreferences.getString(key, null);
    }


    //************************** getter setter for Int data ***************************
    public void setIntData(String key, int value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getIntData(String key) {
        return sharedpreferences.getInt(key, 1);
    }


    //******************************** getter setter for boolean data ***********************************
    public void setBooleanData(String key, boolean value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Boolean getBooleanData(String key) {
        return sharedpreferences.getBoolean(key, false);
    }


    //************************ Clear All SharedPreference Values **********************
    public void clearSharedPrefs() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();      //its clear all data.
        editor.apply();
    }


    //************************ Clear Particular SharedPreference Values **********************
    public void removeData(String key) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void SaveUserData(String key, RegistrationModal myObject) {
        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myObject);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public RegistrationModal getUserData(String key) {
        Gson gson = new Gson();
        String json = sharedpreferences.getString(key, "");
        RegistrationModal obj = gson.fromJson(json, RegistrationModal.class);
        return obj;
    }


}
