package com.example.medicinereminder.model;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    public static Long getDateNowMilli(){
        Calendar cal = Calendar.getInstance();
        int year  = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date  = cal.get(Calendar.DATE);
        cal.clear();
        cal.set(year, month, date);
        long todayMillis = cal.getTimeInMillis();
        return todayMillis;
    }

    @SuppressLint("DefaultLocale")
    public static String getTimeString(String key){
        Long timeDate=Long.parseLong(key);
        Long time=(timeDate-getDateNowMilli())/1000;
        String format ="";
        String hour="";
        String minute="";
        long s = time % 60;
        long m = (time / 60) % 60;
        long h = (time / (60 * 60)) % 24;
        if (h == 0) {
            h += 12;
            format = "AM";
        } else if (h == 12) {
            format = "PM";
        } else if (h > 12) {
            h -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        if (h<10){
             hour="0"+h;
        }else{
            hour=h+"";
        }
        if (m<10){
             minute="0"+m;
        }else{
            minute=""+m;

        }
        return hour+":"+minute+" "+format;
    }


    public static Long getDateInMilli(String dateStr)
    {
        String[] dateDet = dateStr.split("-");
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateDet[0]));
        date.set(Calendar.MONTH,Integer.parseInt(dateDet[1]) -1);
        return date.getTimeInMillis();
    }


}
