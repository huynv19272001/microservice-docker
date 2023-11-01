package com.lpb.napas.ecom.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Log4j2
public class RSAUtils {
    public static PrivateKey getPrivateKey(String pemPkcs8PrivateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] keyData = Base64.decodeBase64(pemPkcs8PrivateKey);
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(keyData);
            return keyFactory.generatePrivate(privKeySpec);
        } catch (Exception e) {
            log.error("RSAUtils", e, "");
        }
        return null;
    }

    public static String makeSignature(String stInput, PrivateKey prKey, String algorithm)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance(algorithm);
        sig.initSign(prKey);
        sig.update(stInput.getBytes());
        byte[] signature = sig.sign();
        return Base64.encodeBase64String(signature);
    }

    public static PublicKey getPublicKeyFromPemFile(String pemPkcs8PrivateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] keyData = Base64.decodeBase64(pemPkcs8PrivateKey);
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(keyData);
            return keyFactory.generatePublic(pubKeySpec);
        } catch (Exception e) {
            log.error("RSAUtils", e, "");
        }
        return null;
    }

    public static boolean verifySignature(PublicKey pubKey, String data, String sigMsg, String algorithm)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance(algorithm);
        sig.initVerify(pubKey);
        sig.update(data.getBytes());
        byte[] signData = Base64.decodeBase64(sigMsg);
        return sig.verify(signData);
    }
}
