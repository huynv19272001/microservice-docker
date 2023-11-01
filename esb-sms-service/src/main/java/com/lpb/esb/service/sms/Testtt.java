package com.lpb.esb.service.sms;

import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
public class Testtt {
    public static String YYYYMMdd = "yyyyMMddhhmmss";
    public static void main(String[] args) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMdd);
            String a =  sdf.format(new Date());
            log.info(a);
        } catch (Exception e){
            log.error(e.getMessage());
        }

    }



    public static String formatDate(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMdd);
            return sdf.format(date);
        } catch (Exception ex) {
        }
        return "";
    }
}
