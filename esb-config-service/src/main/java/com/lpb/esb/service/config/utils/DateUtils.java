package com.lpb.esb.service.config.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Log4j2
public class DateUtils {

    public static String YYYYMMdd = "YYYY-MM-dd HH:mm:ss";

    public static String formatDate(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMdd);
            return sdf.format(date);
        } catch (Exception ex) {
            log.error("formatDate Exception ex " + ex.getMessage());
        }
        return "";
    }
}
