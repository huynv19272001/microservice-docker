package com.lpb.esb.service.common.utils;

import lombok.extern.log4j.Log4j2;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;

/**
 * Created by tudv1 on 2022-04-19
 */
@Log4j2
public class EtaxVerifyUtils {

//    public static

    private static String executeVerifyByte(String dlieu, String ckyDTu, String certChainBase64Encoded) throws Exception {
        byte[] dlieuByte = Base64.getDecoder().decode(dlieu);
        byte[] ckyDTuByte = ckyDTu.getBytes();
        String kquaXThuc = "";
        //  X509Certificate x509Cert = null;
        try {
            // neu dung CertPath  doan nay
            CertPath certPath = loadCertPathFromBase64String(certChainBase64Encoded);
            List certsInChain = certPath.getCertificates();
            X509Certificate[] certChain = (X509Certificate[]) certsInChain.toArray(new X509Certificate[0]);
            X509Certificate x509Cert = certChain[0]; // get publickey TCT

            // neu truyền luôn publickey TCT  doan nay
//      byte[] x509CertEncode = Base64.getDecoder().decode(contentCTSBase64);
//            CertificateFactory cf = CertificateFactory.getInstance("X.509");
//            x509Cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(x509CertEncode));

            boolean kquaXThucByte = getSignatureVerify(dlieuByte, ckyDTuByte, x509Cert);
            log.info("kquaXThucByte: " + kquaXThucByte);
            if (kquaXThucByte == true) {
                kquaXThuc = "Chữ ký điện tử hợp lệ";
            } else {
                kquaXThuc = "Chữ ký điện tử không hợp lệ";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return kquaXThuc;
    }

    private static boolean getSignatureVerify(byte[] contentFile, byte[] ckyDTu, X509Certificate x509Cert) throws Exception {
        String sigAlgName = "SHA256withRSA";
        if (x509Cert.getSigAlgName().equals("SHA1withRSA")) {
            sigAlgName = "SHA1withRSA";
        }

        boolean kquaVerified = true;
        try {
            PublicKey publicKey = x509Cert.getPublicKey();
            Signature verify = Signature.getInstance(sigAlgName);
            verify.initVerify(publicKey);
            verify.update(contentFile);
            kquaVerified = verify.verify(ckyDTu);
            if (kquaVerified) {
                kquaVerified = true;
            } else {
                kquaVerified = false;
            }
        } catch (NoSuchAlgorithmException e) {
            kquaVerified = false;
            e.getLocalizedMessage();
        } catch (InvalidKeyException e) {
            kquaVerified = false;
            e.getLocalizedMessage();
        } catch (SignatureException e) {
            kquaVerified = false;
            e.printStackTrace();
        }
        return kquaVerified;
    }

    /**
     * Loads a certification chain from given Base64-encoded string, containing
     * ASN.1 DER formatted chain, stored with PkiPath encoding.
     */
    private static CertPath loadCertPathFromBase64String(String aCertChainBase64Encoded) throws CertificateException, IOException {
        byte[] certChainEncoded = Base64.getDecoder().decode(aCertChainBase64Encoded);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream certChainStream = new ByteArrayInputStream(certChainEncoded);
        CertPath certPath;
        try {
            certPath = cf.generateCertPath(certChainStream, "PkiPath");
        } finally {
            certChainStream.close();
        }
        return certPath;
    }

}
