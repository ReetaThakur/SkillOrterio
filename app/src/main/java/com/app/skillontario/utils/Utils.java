package com.app.skillontario.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.app.skillontario.utils.topSnackBar.TSnackBar;
import com.app.skillorterio.R;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    /**
     * Hides the soft keyboard
     */
    public static void hideKeyBoard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // Crashlytics.logException(e);
        }
    }

    public static void changeStatusBarColor(Context context, Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    public static void hideKeyboardFromFragment(Context context, Fragment fragment) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(fragment.requireView().getRootView().getWindowToken(), 0);
        } catch (Exception e) {
            //  Crashlytics.logException(e);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
            inputMethodManager.showSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            //  Crashlytics.logException(e);
        }
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void shareTextOnly(Context context, String headingText, String longMsg) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sharingIntent.setType("text/plain");
        String shareMessage = "\nTax Volt: Canada’s best tax software! Download Now  \n\n" + "https://play.google.com/store/apps/details?id=com.appstudio.taxvolt";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, headingText);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        context.startActivity(Intent.createChooser(sharingIntent, "Share Using..."));
    }

    public static String saveBitmap(Bitmap bmp, String imageName) {
        try {
            Bitmap resized = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * 0.7), (int) (bmp.getHeight() * 0.7), false);
            String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TaxVolt/";
            String filepath = root + imageName;

            FileOutputStream fos = new FileOutputStream(filepath);
            resized.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return filepath;
        } catch (Exception e) {
            Log.e("Could not save", e.getMessage());
            e.printStackTrace();
        }

        return "";
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min((float) maxImageSize / realImage.getWidth(), (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);
        return newBitmap;
    }


    public static void showSuccessSnackBar(Context context, String msgStr) {
        TSnackBar.make(((Activity) context).findViewById(android.R.id.content), msgStr, TSnackBar.LENGTH_SHORT).show();
    }

    public static void showErrorSnackBar(Context context, String msgStr) {
        TSnackBar.make(((Activity) context).findViewById(android.R.id.content), msgStr, TSnackBar.LENGTH_SHORT,
                ContextCompat.getColor(context, R.color.red)).show();
    }

    public static boolean isInputCorrect(Editable s, int size, int dividerPosition, char divider) {
        boolean isCorrect = s.length() <= size;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && (i + 1) % dividerPosition == 0) {
                isCorrect &= divider == s.charAt(i);
            } else {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }
        return isCorrect;
    }

    public static Boolean isValidExpiry(String expiry) {
        try {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            String[] date = expiry.split("/");
            if (date[0].isEmpty()) {
                return false;
            } else if (Integer.parseInt(date[0]) > 12 || Integer.parseInt(date[0]) == 0) {
                return false;
            } else if (date[1] != null && date[1].isEmpty()) {
                return false;
            } else if (date[1] != null && Integer.parseInt(date[1]) == 0) {
                return false;
            } else if (date[1] != null && Integer.parseInt(date[1]) > 31) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String concatString(char[] digits, int dividerPosition, char divider) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider);
                }
            }
        }

        return formatted.toString();
    }

    public static char[] getDigitArray(final Editable s, final int size) {
        char[] digits = new char[size];
        int index = 0;
        for (int i = 0; i < s.length() && index < size; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }
        return digits;
    }

    public static String getDeviceId(Context context) {
        String m_deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return m_deviceId;
    }


    public static String DateFormate(String date) {
        String newDateString = "2021-06-22 11:11 AM";
        try {
            newDateString = convert(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return newDateString;
    }

    public static String convert(String dateString) throws ParseException {
        System.out.println("Given date is " + dateString);
//2021-10-01T00:00:00.000Z
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
        Date date = sdf.parse(dateString);
/*
        System.out.println("MM/dd/yyyy formatted date : " + new SimpleDateFormat("MMM dd  yyyy hh:mm aaa").format(date));
        System.out.println("yyyy-MM-dd formatted date : " + new SimpleDateFormat("yyyy-MM-dd").format(date));*/
        return "" + new SimpleDateFormat("MMM dd  yyyy hh:mm aaa").format(date);
    }
}
