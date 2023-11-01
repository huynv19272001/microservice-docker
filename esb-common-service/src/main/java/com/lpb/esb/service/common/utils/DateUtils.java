package com.lpb.esb.service.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by tudv1 on 2021-09-20
 */
public class DateUtils {
    public static String convertTimeLong(Long time, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date(time));
    }

    public static String getCurrentLocalDateTimeStamp() {
        return LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public static String convertTime() {
        return LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static String convertDateString(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    public static Date convertStringToDate(String dateTime, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = df.parse(dateTime);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertDateToString(Date dateTime, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String strDate = formatter.format(dateTime);
        return strDate;
    }
}
