package com.lpb.esb.service.common.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static org.apache.commons.io.FilenameUtils.getExtension;

public class test {
    public static void main(String[] args) throws IOException, InvalidKeySpecException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        String data = "{\"billing_code\":\"PH04000034491\",\"order_id\":\"1011111111110006\",\"username\":\"partnerchain\",\"password\":\"8z3HlaBtTtUIrU9NnMkir50aefeX3zPmjPV4PcDyMp0K/cPlKRrAsn5jCvkoZmQleg7fEqr+6QEU9B7zgd1BNEobYJklJy2eXVfdW0/iyV4rq/zgvxJlP9WPW5Ts8yDkXrEU0Qk9u5z6bj08HRbVLd53vZmmqy7jiDZ74feVTnk\\u003d\",\"trans_date\":\"20230731230918\",\"service_code\":\"EVN\",\"channel_info\":{\"channelType\":\"website\",\"websiteAddress\":\"https://ecollect.vitapay.vn\",\"source\":\"internetBanking\",\"mobileNumber\":\"0987654399\"}}";
        byte[] messageBytes = data.getBytes("UTF8");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair key = keyGen.generateKeyPair();
        Signature sig = Signature.getInstance("SHA256WithRSA");
        sig.initSign(key.getPrivate());
        sig.update(messageBytes);
        byte[] signature1 = sig.sign();
//        sig.initVerify(key.getPublic());
//        sig.update(messageBytes);
//        boolean result = sig.verify(signature1);
         Base64.getEncoder().encodeToString(signature1);



    }
}
