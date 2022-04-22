package com.example.medicinereminder.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtility {
    public static Long getDateMillis(String date) {
        long milliseconds = -1;
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

    public static String getDateString(Long date) {
        Date d = new Date(date);
        DateFormat f = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        return f.format(d);
    }
}
