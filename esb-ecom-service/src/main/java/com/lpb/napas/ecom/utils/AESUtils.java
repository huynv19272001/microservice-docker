package com.lpb.napas.ecom.utils;

import com.lpb.napas.ecom.common.Constant;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Log4j2
public class AESUtils {
    public static String encryptWithAES(String data) {
        String encryptString = "";
        if (data != null && !data.trim().isEmpty()) {
            try {
                String cryptoKey = Constant.CRYPTOGRAPHY_KEY;
                String ivString = Constant.IV_STRING;
                encryptString = encryptAES(data, cryptoKey, ivString);
            } catch (Exception ex) {
                log.error("AESUtils", ex, "");
            }
        }
        return encryptString;
    }

    public static String decryptWithAES(String encryptedValue) {
        String decryptString = "";
        if (encryptedValue != null && !encryptedValue.trim().isEmpty()) {
            try {
                String cryptoKey = Constant.CRYPTOGRAPHY_KEY;
                String ivString = Constant.IV_STRING;
                decryptString = decryptAES(encryptedValue, cryptoKey, ivString);
            } catch (Exception ex) {
                log.error("AESUtils", ex, "");
            }
        }
        return decryptString;
    }

    public static String encryptAES(String input, String key, String iv) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);

            byte[] encrypted = cipher.doFinal(input.getBytes("UTF-8"));
            return new String(Base64.encodeBase64(encrypted));
        } catch (Exception ex) {
            log.error("AESUtils", ex, "");
            return null;
        }
    }

    public static String decryptAES(String encrypted, String key, String iv) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        } catch (Exception ex) {
            log.error("AESUtils", ex, "");
            return null;
        }
    }
}
