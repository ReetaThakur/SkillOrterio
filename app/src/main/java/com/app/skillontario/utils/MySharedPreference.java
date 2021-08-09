package com.app.skillontario.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.skillontario.baseClasses.AppController;
import com.app.skillontario.models.CareerModal;
import com.app.skillontario.models.RegistrationModal;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


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

    public void saveNotificationObject(String key, JSONObject object) {
        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public JSONObject getNotificationObject(String key) {
        Gson gson = new Gson();
        String json = sharedpreferences.getString(key, "");
        JSONObject obj = gson.fromJson(json, JSONObject.class);
        return obj;
    }

    public void saveMap(Map<String, Object> inputMap) {
        final String mapKey = "map";
        SharedPreferences.Editor editor = sharedpreferences.edit();
        JSONObject jsonObject = new JSONObject(inputMap);
        String jsonString = jsonObject.toString();
        editor.remove(mapKey).apply();
        editor.putString(mapKey, jsonString);
        editor.commit();

    }

    public Map<String, Object> loadMap() {
        Map<String, Object> outputMap = new HashMap<>();
        final String mapKey = "map";
        try {
            String jsonString = sharedpreferences.getString(mapKey, (new JSONObject()).toString());
            JSONObject jsonObject = new JSONObject(jsonString);
            Iterator<String> keysItr = jsonObject.keys();
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                outputMap.put(key, jsonObject.get(key));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }

}
