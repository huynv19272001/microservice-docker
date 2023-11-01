package com.esb.transaction.utils;

import lombok.extern.log4j.Log4j2;

import java.math.BigInteger;
import java.security.MessageDigest;

@Log4j2
public class MD5Utils {
    public static String encryptMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger bi = new BigInteger(1, md.digest(str.getBytes()));
            return bi.toString(16);
        } catch (Exception ex) {
            log.info("ENCRYPT: " + ex.getMessage());
            return null;
        }
    }
}
