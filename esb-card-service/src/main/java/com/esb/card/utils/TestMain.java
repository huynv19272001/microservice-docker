package com.esb.card.utils;

import java.util.Date;

public class TestMain {
    public static void main(String[] args) {
        Date curDate = new Date();
        System.out.println(ESBUtils.formatDate(curDate, ESBUtils.YYYYMMddHHmmssSSSSSS));
    }
}

