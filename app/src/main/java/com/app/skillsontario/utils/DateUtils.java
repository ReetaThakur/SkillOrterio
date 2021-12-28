package com.app.skillsontario.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DateUtils {

    public static String getCurrentDate() {
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(calender.getTime());
    }

    public static boolean isValidDOB(String dob){

        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date =  sdf.parse(dob);
            Objects.requireNonNull(date).getTime();

            // Calculate time difference in milliseconds
            long timeDifferenceMilliseconds = currentTime - date.getTime();
            Log.e("current time", String.valueOf(currentTime));
            Log.e("calendar time", String.valueOf(date.getTime()));

            long diffSeconds = timeDifferenceMilliseconds / 1000;
            long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
            long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
            long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
            long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
            long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
            long diffYears = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 365.0));

            if (diffYears < 1)
                return false;
            else
                return true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
