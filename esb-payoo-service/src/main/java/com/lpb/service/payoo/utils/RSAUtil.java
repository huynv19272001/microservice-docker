package com.lpb.service.payoo.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;
@Component
@Log4j2
public class RSAUtil {
    public static PrivateKey privateKeyGW;

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048, new SecureRandom());
            KeyPair pair = generator.generateKeyPair();

            return pair;
        } catch (Exception e) {
            log.info("Exception generateKeyPair: " + e);
            e.printStackTrace();
            return null;
        }
    }

    public static String sign(String plainText, PrivateKey privateKey) {
        try {
            Signature privateSignature = Signature.getInstance("SHA256withRSA");
            privateSignature.initSign(privateKey);
            privateSignature.update(plainText.getBytes(UTF_8));

            byte[] signature = privateSignature.sign();

            return Base64.getEncoder().encodeToString(signature);
        } catch (Exception e) {
            log.info("Exception sign: " + e);
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verify(String plainText, String signature, PublicKey publicKey) {
        try {
            Signature publicSignature = Signature.getInstance("SHA256withRSA");
            publicSignature.initVerify(publicKey);
            publicSignature.update(plainText.getBytes(UTF_8));

            byte[] signatureBytes = Base64.getDecoder().decode(signature);

            return publicSignature.verify(signatureBytes);
        } catch (Exception e) {
            log.info("Exception verify: " + e);
            e.printStackTrace();
            return false;
        }
    }

    public static PublicKey getPublicKeyByFile(String pfxFile) {
        try {
            FileInputStream fis = new FileInputStream(pfxFile);
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(fis);

            return cert.getPublicKey();
        } catch (Exception e) {
            log.info("Exception getPublicKey: " + e);
            e.printStackTrace();
            return null;
        }
    }

    public static String loadPfx(String pfx_file, String password) {
        try {
            FileInputStream fisGW = new FileInputStream(new File(pfx_file));

            KeyStore keyGW = KeyStore.getInstance("PKCS12");
            char[] cpasswordGW = password.toCharArray();
            keyGW.load(fisGW, cpasswordGW);
            privateKeyGW = (PrivateKey)keyGW.getKey((String)keyGW.aliases().nextElement(), cpasswordGW);

            fisGW.close();
            return "OK";
        } catch (IOException|java.security.KeyStoreException|java.security.NoSuchAlgorithmException|java.security.UnrecoverableKeyException|java.security.cert.CertificateException e) {
            return "Load pfx " + pfx_file + " ERR: " + e.toString();
        }
    }
    public static String sign(String doc, String fileFolder, String pass) {
        String loadPfx = loadPfx(fileFolder, pass);
        if (!loadPfx.equals("OK")) return loadPfx;

        String kq = "";
        try {
            Signature signatureAlgorithmGW = Signature.getInstance("SHA256withRSA");
            signatureAlgorithmGW.initSign(privateKeyGW);
            signatureAlgorithmGW.update(doc.getBytes(UTF_8));
            kq = Base64.getEncoder().encodeToString(signatureAlgorithmGW.sign());
        } catch (InvalidKeyException|SignatureException e) {} catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return kq;
    }
}
