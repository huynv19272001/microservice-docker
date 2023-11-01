package com.lpb.esb.service.common.utils;

public class StringUtils {
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().equals("");
    }

}
