package com.app.skillontario.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.renderscript.Element;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;


import com.app.skillontario.activities.SettingActivity;
import com.app.skillontario.activities.SplashActivity;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillorterio.R;
import com.app.skillontario.SignIn.SignInActivity;
import com.app.skillontario.signup.SignUpActivity;
import com.app.skillontario.utils.topSnackBar.TSnackBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class Utils {
    public static boolean Language = false;
    public static String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";

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
        if (Build.VERSION.SDK_INT >= 21) {
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

    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static String DateFormate(String str_date) {
        String newDateString = "";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(str_date);
            df = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
            df.setTimeZone(TimeZone.getDefault());
            newDateString = df.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDateString;
    }

    @SuppressLint({"NewApi", "LocalSuppress"})
    public static void AddEvent(Context context, String dtstart, String enddate, String title, String des) {
      /*  //MMM dd,yyyy hh:mm a"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd,yyyy hh:mm a", Locale.getDefault());
        LocalDateTime localDate = LocalDateTime.parse(dtstart, formatter);
        // LocalDateTime localDate = OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime();
        long strdate = localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli();
        LocalDateTime localendDate = LocalDateTime.parse(enddate, formatter);
        long endDate = localendDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli();// For next 10min
      */
        SimpleDateFormat S_df = new SimpleDateFormat("MMM dd,yyyy hh:mm a", Locale.ENGLISH);
        Calendar S_cal = new GregorianCalendar();
        SimpleDateFormat E_df = new SimpleDateFormat("MMM dd,yyyy hh:mm a", Locale.ENGLISH);
        Calendar E_cal = new GregorianCalendar();
        Date S_date = null;
        Date E_date = null;
        try {
            S_date = S_df.parse(dtstart);
            S_cal.setTime(S_date);
            E_date = E_df.parse(enddate);
            E_cal.setTime(E_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, S_cal.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, E_cal.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, des)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        context.startActivity(intent);
    }

    public static String DateFormateNews(String str_date) {
        String newDateString = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = format.parse(str_date);
            format = new SimpleDateFormat("MMM dd,yyyy");
            newDateString = format.format(date);
            ;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateString;
    }

    public static void guestMethod(final Context context, String className) throws Exception {
        final BottomSheetDialog guestUser = new BottomSheetDialog(context);
        View sheetView = LayoutInflater.from(context).inflate(R.layout.geust_user_bottom, null);
        guestUser.setContentView(sheetView);
        guestUser.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        guestUser.setCanceledOnTouchOutside(true);
        guestUser.setCancelable(true);
        guestUser.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        guestUser.show();
        LinearLayout ll_Logout = sheetView.findViewById(R.id.ll_Logout);
        LinearLayout ll_signin = sheetView.findViewById(R.id.ll_signin);
        ll_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreference.getInstance().setStringData(SharedPrefsConstants.GUEST_FLOW_CLASS, className);
                context.startActivity(new Intent(context, SignUpActivity.class));
                guestUser.dismiss();
            }
        });
        ll_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreference.getInstance().setStringData(SharedPrefsConstants.GUEST_FLOW_CLASS, className);
                context.startActivity(new Intent(context, SignInActivity.class));
                guestUser.dismiss();
            }
        });
    }

    @SuppressLint("NewApi")
    public static void openD(final Context context, String dtstart, String enddate, String title, String des) {
        Dialog dialogMood = new Dialog(context);
        dialogMood.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMood.setCancelable(true);
        if (dialogMood.getWindow() != null) {
            dialogMood.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialogMood.setContentView(R.layout.news_dialog);
        dialogMood.findViewById(R.id.done).setOnClickListener(view1 -> {
            AddEvent(context, dtstart, enddate, title, des);
            dialogMood.dismiss();
        });
        dialogMood.show();
    }


    public static void getHtmlEncodeset(String TEXT, TextView tvDes) {
        Spanned htmlAsSpanned = Html.fromHtml(TEXT); // used by TextView
        tvDes.setText(htmlAsSpanned);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDes.setText(Html.fromHtml(TEXT, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvDes.setText(Html.fromHtml(TEXT));
        }
    }

    public static String getHtmlEncode(String TEXT) {
        Spanned htmlAsSpanned = Html.fromHtml(TEXT); // used by TextView

        return htmlAsSpanned.toString();
    }

    public static void updatLocalLanguage(String lang, Context context) {
        try {
            if (lang.equalsIgnoreCase(""))
                return;
            Locale myLocale = new Locale(lang);//Set Selected Locale
            Locale.setDefault(myLocale);//set new locale as default
            Configuration config = new Configuration();//get Configuration
            config.locale = myLocale;//set config locale as selected locale
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());//Update the config
            MySharedPreference.getInstance().setStringData(AppConstants.LANGUAGE, lang);

        } catch (Exception er) {
            er.getMessage();
        }
    }

    public static void updatLocalLanguage_local(String lang, Context context, Locale locale) {
        // optional - Helper method to save the selected language to SharedPreferences in case you might need to attach to activity context (you will need to code this)
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            context.createConfigurationContext(configuration);
        } else {
            resources.updateConfiguration(configuration, displayMetrics);
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static void share(Context context, String title, String text,String url ,Bitmap image) {


        BranchUniversalObject branchUniversalObject = new BranchUniversalObject()
                .setCanonicalIdentifier("skillsontario/12345")
                .setTitle(title)
                .setContentDescription(text)
                .setContentImageUrl(url)
                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .setContentMetadata(new ContentMetadata().addCustomMetadata("referring_user_pic", "https://www.skillsontario.com"));
// Define the link properties for analytics and redirection control


        LinkProperties linkProperties = new LinkProperties()
                .setChannel("Facebook")
                .setFeature("referral")
                .setCampaign("content 123 launch")
                .setStage("new user")
                .addControlParameter("$desktop_url", "https://www.skillsontario.com");

        branchUniversalObject.generateShortUrl(context, linkProperties, new Branch.BranchLinkCreateListener() {
            @Override
            public void onLinkCreate(String url, BranchError error) {
                if (error == null) {
                    Log.i("MyApp", "got my Branch link to share: " + url);
                }
            }
        });


        // Define the style of the share sheet
        ShareSheetStyle shareSheetStyle = new ShareSheetStyle(context, title, text)
                //ShareSheetStyle shareSheetStyle = new ShareSheetStyle(((Activity)context), "This text will be the user’s message if supported.")
                .setAsFullWidthStyle(false)
                .setSharingTitle("Share With");


        branchUniversalObject.showShareSheet(((Activity) context), linkProperties, shareSheetStyle, new Branch.BranchLinkShareListener() {
            @Override
            public void onShareLinkDialogLaunched() {

            }

            @Override
            public void onShareLinkDialogDismissed() {
                Log.i("ShareFunction", "Share screen dismissed");
            }

            @Override
            public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {


            }

            @Override
            public void onChannelSelected(String channelName) {

                if (channelName.equals("Facebook")) {

                }


            }
        });


    }
}
