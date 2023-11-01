package com.lpb.esb.settle.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {

        int sec = Integer.parseInt("12000");
        Date d = new Date(sec * 1000L);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String time = df.format(d);
        Date date = new Date();
        date.setHours(date.getHours() - Integer.parseInt(time.replaceAll(":", "").substring(0, 6).substring(0, 2)));
        date.setMinutes(date.getMinutes() - Integer.parseInt(time.replaceAll(":", "").substring(0, 6).substring(2, 4)));
        date.setSeconds(date.getSeconds() - Integer.parseInt(time.replaceAll(":", "").substring(0, 6).substring(4, 6)));
        String dateFormat = null;

        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dateFormat = simpleDateFormat.format(date);
    }
}
