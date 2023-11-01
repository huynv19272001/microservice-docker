/**
 * @author Trung.Nguyen
 * @date 30-Nov-2022
 */
package com.lpb.esb.etax.payment.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class ShaUtils {

    public String hash256(String inputData) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = mDigest.digest(inputData.getBytes());
        StringBuffer sb = new StringBuffer(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
