package com.lpb.napas.ecom.utils;

import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Log4j2
public class DateUtils {
    private static final String FORMAT_DDMMYYY = "dd/MM/yyyy";

    public static String convertSqlTimeStampToStr(Date sqlDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DDMMYYY);
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
        return dateFormat.format(utilDate);
    }

    public static String convertTimeLong(Long time, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date(time));
    }

    public static String getddMMyyHHmmssSS() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyHHmmssSS");
        LocalDateTime now = LocalDateTime.now();
        String strDate = "EC" + dtf.format(now);
        return strDate;
    }

    public static Date convertMMyyyy(String stringDate) {
        try {
            Date date = new SimpleDateFormat("MM/yyyy").parse(stringDate);
            return date;
        } catch (Exception e) {
            log.info("Exception convertDDmmYYYYHHMMSS: " + e);
            return null;
        }
    }
    public static Date convertyyyyMMddHHssMM(String stringDate) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stringDate);
            return date;
        } catch (Exception e) {
            log.info("Exception convertDDmmYYYYHHMMSS: " + e);
            return null;
        }
    }

}
