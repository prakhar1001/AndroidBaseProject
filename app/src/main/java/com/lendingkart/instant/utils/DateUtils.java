package com.lendingkart.instant.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Andy on 3/22/2016.
 */
public class DateUtils {

    public static long getTimeInMilliSeconds(String dateString, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");
        String dateInString = dateString + " " + time;
        Date date;
        try {
            date = sdf.parse(dateInString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
