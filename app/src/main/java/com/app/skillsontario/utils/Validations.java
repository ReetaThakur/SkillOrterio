package com.app.skillsontario.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validations {

    private static String ALPHA_NUMERIC_SPECIAL_PASSWORD_REGEX =
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])(?=\\S+$)[A-Za-z\\d$@$!%*#?&]{7,15}$";
    private static String INTEGER_NUMBER_REGEX = "^([0-9]+)$";
    private static String NAME_WITHOUT_NUMBER_REGEX = "^[a-zA-Z\\\\s](?!^\\d+$)(?=\\S+$).{2,20}$";
    private static String EMAIL_REGEX = "^([0-9a-zA-Z]([-\\.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{1,9})$";
    private static final String PHONE_REGEX = "^[+#*\\(\\)\\[\\]]*([0-9][ ext+-pw#*\\(\\)\\[\\]]*){8,15}$";
    private static final String POSTAL_CODE = "[ABCEGHJKLMNPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z][ ]?\\d[ABCEGHJ-NPRSTV-Z]\\d";

    public static boolean validatePasswordFields(String text, Context context, int messageId) {
        Pattern p = Pattern.compile(ALPHA_NUMERIC_SPECIAL_PASSWORD_REGEX, Pattern.CASE_INSENSITIVE);

        Matcher m = p.matcher(text);
        if (m.matches() && !text.isEmpty()) {
            if (isValidPassword(text, context, messageId))
                return true;
            else {
                showToast(context, context.getResources().getString(messageId));
                return false;
            }
        } else {
            showToast(context, context.getResources().getString(messageId));
            return false;
        }
    }

    public static boolean isValidPassword(final String password, Context context, int messageId) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    public static boolean isValidPostalCode(String postalCode) {
        Pattern p = Pattern.compile(POSTAL_CODE, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(postalCode);
        if (m.matches() && !postalCode.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateEmailID(String text, Context context, int messageId) {
        Pattern p = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

        if (text != null) {
            Matcher m = p.matcher(text);
            if (m.matches() && !text.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean validateMobileNumber(String mobile, Context context, int messageId) {

        Pattern p = Pattern.compile(PHONE_REGEX, Pattern.CASE_INSENSITIVE);

        if (mobile != null) {
            Matcher m = p.matcher(mobile);
            if (m.matches() && !mobile.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
