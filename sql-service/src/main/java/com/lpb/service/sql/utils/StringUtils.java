package com.lpb.service.sql.utils;

public class StringUtils {
    private static final String[] HEXCHARS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
        "E", "F" };

    public static String byteToHex(byte[] input) {
        StringBuilder builder = new StringBuilder();
        for (int ctr = 0; ctr < input.length; ctr++) {
            builder.append(HEXCHARS[(input[ctr] >> 4 & 0xF)]);
            builder.append(HEXCHARS[(input[ctr] & 0xF)]);
        }
        String output = builder.toString();
        return output;
    }

    public static byte[] hexToByte(String input) {
        int len = input.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("Input string size should be a multiple of 2 bytes.");
        }
        byte[] bytes = new byte[len / 2];
        for (int ctr = 0; ctr < len; ctr += 2) {
            bytes[(ctr / 2)] = ((byte) ((Character.digit(input.charAt(ctr), 16) << 4)
                + Character.digit(input.charAt(ctr + 1), 16)));
        }
        return bytes;
    }

    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().equals("");
    }
}
