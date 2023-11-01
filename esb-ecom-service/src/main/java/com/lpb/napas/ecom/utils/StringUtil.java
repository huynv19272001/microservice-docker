package com.lpb.napas.ecom.utils;

public class StringUtil {
    public static boolean isEmptyOrNull(String str) {
        return str == null || str.length() == 0 || str.trim().length() == 0;
    }
}
