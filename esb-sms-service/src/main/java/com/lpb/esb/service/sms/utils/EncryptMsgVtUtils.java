package com.lpb.esb.service.sms.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.cert.CertificateException;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by tudv1 on 2022-07-08
 */
@Component
@Log4j2
public class EncryptMsgVtUtils {
    private byte[] iv;// = new SecureRandom().generateSeed(16);

    private final int AES_KEY_SIZE = 128;

    public String encryptMessage(String mess, String initVec, String priCP, String pubVT) throws Exception {
        priCP = standardKey(priCP);
        pubVT = standardKey(pubVT);
        iv = initVec.getBytes("UTF-8");
        SecretKey secretKeyCP = generateSharedSecret(priCP, pubVT);
        String cipherText = encryptString(secretKeyCP, mess);
        return cipherText;
    }

    private String standardKey(String inputKey) {
        inputKey = inputKey.replace("\n", "");
        inputKey = inputKey.replace("\r", "");
        inputKey = inputKey.replace(" ", "");
        return inputKey;
    }

    private SecretKey generateSharedSecret(String privateKeyStr,
                                           String publicKeyStr) {
        try {
            PrivateKey privateKey;
            PublicKey publicKey;

            publicKey = loadPublicKey(publicKeyStr);
            privateKey = loadPrivateKey(privateKeyStr, false);

            return generateSharedSecret(privateKey, publicKey);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    private PublicKey loadPublicKey(String pubCer) throws IOException,
        NoSuchAlgorithmException, InvalidKeySpecException,
        CertificateException {
        PublicKey publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                decodeBase64(pubCer));
            publicKey = (PublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    private String fullyReadFile(File file) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        byte[] bytesOfFile = new byte[(int) file.length()];
        dis.readFully(bytesOfFile);
        dis.close();
        String sRead = new String(bytesOfFile);
        return sRead.trim();
    }

    private PrivateKey loadPrivateKey(String key, Boolean isFile)
        throws IOException, NoSuchAlgorithmException,
        InvalidKeySpecException {
        PrivateKey privateKey;
        String sReadFile;
        if (isFile.booleanValue()) {
            File file = new File(key);
            sReadFile = fullyReadFile(file);
        } else {
            sReadFile = key.trim();
        }
        if ((sReadFile.startsWith("-----BEGIN PRIVATE KEY-----"))
            && (sReadFile.endsWith("-----END PRIVATE KEY-----"))) {
            sReadFile = sReadFile.replace("-----BEGIN PRIVATE KEY-----", "");
            sReadFile = sReadFile.replace("-----END PRIVATE KEY-----", "");
            sReadFile = sReadFile.replace("\n", "");
            sReadFile = sReadFile.replace("\r", "");
            sReadFile = sReadFile.replace(" ", "");
        } else {
            // return null;
        }
        byte[] b = decodeBase64(sReadFile);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);

        KeyFactory factory = KeyFactory.getInstance("EC");
        privateKey = factory.generatePrivate(spec);
        return privateKey;
    }

    private byte[] decodeBase64(String dataToDecode) {
        // byte[] bDecoded =
        // org.bouncycastle.util.encoders.Base64.decode(dataToDecode);
        // java.util.Base64.Decoder d = java.util.Base64.getDecoder();
        // byte[] bDecoded = d.decode(dataToDecode);
        //
        // return bDecoded;
        BASE64Decoder b64d = new BASE64Decoder();
        byte[] bDecoded = null;
        try {
            bDecoded = b64d.decodeBuffer(dataToDecode);
        } catch (IOException var4) {
            var4.printStackTrace();
        }
        return bDecoded;
    }

    private SecretKey generateSharedSecret(PrivateKey privateKey,
                                           PublicKey publicKey) {
        try {
            KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH");
            keyAgreement.init(privateKey);
            keyAgreement.doPhase(publicKey, true);
            SecretKeySpec key = new SecretKeySpec(
                keyAgreement.generateSecret(), "AES");
            return key;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    private String encryptString(SecretKey key, String plainText) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] plainTextBytes = plainText.getBytes("UTF-8");
            byte[] cipherText;
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            cipherText = new byte[cipher.getOutputSize(plainTextBytes.length)];
            int encryptLength = cipher.update(plainTextBytes, 0,
                plainTextBytes.length, cipherText, 0);
            encryptLength += cipher.doFinal(cipherText, encryptLength);
            return bytesToHex(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String bytesToHex(byte[] data, int length) {
        String digits = "0123456789ABCDEF";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i != length; i++) {
            int v = data[i] & 0xff;
            buffer.append(digits.charAt(v >> 4));
            buffer.append(digits.charAt(v & 0xf));
        }
        return buffer.toString();
    }

    private String bytesToHex(byte[] data) {
        return bytesToHex(data, data.length);
    }
}
