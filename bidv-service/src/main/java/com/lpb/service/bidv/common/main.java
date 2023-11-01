package com.lpb.service.bidv.common;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class main {
    public static void main(String[] args) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        String password = "changemeplease123a@";
        String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCW0VomTHsZ4VoNCWI4L74ief91 bNKeBtbngsAO33DKnM6YY645KhJsw4rYaNllGTpO9iF7vqPVxcQ4dokXvlylo+ni \n" +
            "E7oUVxPJ1htQs+pt5fcDFZl0QMR3oVUAETmJcBJ368O1hKMSsssf2klBMJJpg8fg \n" +
            "49IofEHjm5qkGPqkCQIDAQAB\n";
        String publicKeyTemp = key.replaceAll("\\s+", "");
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyTemp.getBytes("UTF-8"));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        PublicKey fileGeneratedPublicKey = keyFactory.generatePublic(spec);
        RSAPublicKey rsaPub = (RSAPublicKey) (fileGeneratedPublicKey);
        BigInteger publicKeyModulus = rsaPub.getModulus();
        BigInteger publicKeyExponent = rsaPub.getPublicExponent();
        RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(publicKeyModulus, publicKeyExponent);
        PublicKey pubKey = keyFactory.generatePublic(rsaPubKey);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] a = cipher.doFinal(password.getBytes());
        org.apache.tomcat.util.codec.binary.Base64.encodeBase64(a);
        Base64.getEncoder().encodeToString(a);
    }
}
