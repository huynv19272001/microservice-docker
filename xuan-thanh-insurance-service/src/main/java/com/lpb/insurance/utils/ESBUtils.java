package com.lpb.insurance.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
@Log4j2
public class ESBUtils {
    public static String YYMMdd = "YYMMdd";
    public static String HHmmssSS = "HHmmssSS";
    public static String YYYYMMddHHmmssSSSSSS = "YYYYMMddHHmmssSSSSSS";
    public static String HHmmssSSSS = "HHmmssSSSS";
    public static String YYYYMMdd = "YYYYMMdd";
    public static String yyyyMMddTHHmmssSSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static String formatDate(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception ex) {
            log.error("formatDate Exception ex " + ex.getMessage());
        }
        return "";
    }

    public static String genRefCode(String cardNumber, String userName) {
        StringBuilder bu = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(ESBUtils.YYMMdd);
        bu.append(userName);
        bu.append(dateFormat.format(cal.getTime()));

        if (cardNumber.length() > 16) {
            bu.append(cardNumber.substring(9, 19));
        } else if (cardNumber.length() == 16) {
            bu.append(cardNumber.substring(6, 16));
        } else {
            bu.append("0000000000");
        }
        dateFormat = new SimpleDateFormat(ESBUtils.HHmmssSS);
        bu.append(dateFormat.format(cal.getTime()));
        return bu.toString();
    }

    public static String genRequestCode(String channelCode) {
        StringBuilder bu = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(ESBUtils.YYYYMMdd);
        bu.append(channelCode);
        bu.append(dateFormat.format(cal.getTime()));
        dateFormat = new SimpleDateFormat(ESBUtils.HHmmssSSSS);
        bu.append(dateFormat.format(cal.getTime()));
        return bu.toString();
    }
}
