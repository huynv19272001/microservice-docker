package com.lpb.esb.service.common.utils.rsa;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Utils {
    private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String byteArray2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hex[(bytes[i] & 0xF0) >> 4]);
            sb.append(hex[bytes[i] & 0x0F]);
        }
        return sb.toString();
    }

    public static String encryptSHA1(String inputStr) {
        try {
            String stringFromSHA256 = getStringFromSHA256(inputStr);
            return new RSAUtil().encodeBase64(stringFromSHA256.getBytes());
        } catch (Exception ex) {
            return null;
        }
    }

    private static String getStringFromSHA256(String stringToEncrypt)
        throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
        messageDigest.update(stringToEncrypt.getBytes());
        return byteArray2Hex(messageDigest.digest());
    }

    @SuppressWarnings("unused")
    private static String getStringFromMd5(String stringToEncrypt)
        throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(stringToEncrypt
            .getBytes(StandardCharsets.US_ASCII));
        return byteArray2Hex(messageDigest.digest());
    }
}
