package com.lpb.service.bidv.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

import com.lpb.service.bidv.common.Constant;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SHA256withRSA {
    @Autowired
    Constant constant;

    public byte[] makeSignature(String data, String privateKey, String password) throws InvalidKeyException, Exception {
        Security.addProvider(new BouncyCastleProvider());
        Signature rsa = Signature.getInstance(constant.getSIGNATURE_ALGORITHM(), "BC");
        rsa.initSign(getPrivateKeyFromPFX(privateKey, password));
        rsa.update(data.getBytes("US-ASCII"));
        return rsa.sign();
    }

    public boolean verify(String publicKeyFile, String signedData, String signature) {

        try {
            Security.addProvider(new BouncyCastleProvider());
            PublicKey publicKey = getPublicKey(publicKeyFile);
            Signature sig = Signature.getInstance("SHA256withRSA", "BC");
            sig.initVerify(publicKey);
            Base64 base64 = new Base64();
            sig.update(signedData.getBytes("US-ASCII"));

            if (!sig.verify(base64.decode(signature.getBytes(StandardCharsets.UTF_8)))) {
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verify(String publicKeyFile, String signedData, String signature, String type) {

        try {
            Security.addProvider(new BouncyCastleProvider());
            PublicKey publicKey = getPublicKey(publicKeyFile);
            Signature sig = Signature.getInstance("SHA256withRSA", "BC");
            sig.initVerify(publicKey);
            Base64 base64 = new Base64();
            sig.update(signedData.getBytes("US-ASCII"));

            if (!sig.verify(base64.decode(signature.getBytes(StandardCharsets.UTF_8)))) {
                if (type.equals("export") && constant.getCHECK_VERIFY().equals("false")) {
                    log.info("Veriry signature false but hotfix true");
                } else {
                    log.info("Veriry signature false");
                    return false;
                }
            }
            log.info("Veriry signature true");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public PrivateKey getPrivateKeyFromPFX(String filename, String password) throws Exception {
        KeyStore ks = KeyStore.getInstance("pkcs12", "SunJSSE");
        // KeyStore ks = KeyStore.getInstance("PKCS11");

        ks.load(new FileInputStream(filename), password.toCharArray());
        // iterate over all aliases
        Enumeration<String> es = ks.aliases();
        String alias = "";
        boolean isAliasWithPrivateKey = false;
        while (es.hasMoreElements()) {
            alias = (String) es.nextElement();
            // if alias refers to a private key break at that point as we want to use that
            // certificate
            if (isAliasWithPrivateKey = ks.isKeyEntry(alias)) {
                break;
            }
        }
        if (!isAliasWithPrivateKey) {
            return null;
        }

        KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias,
            new KeyStore.PasswordProtection(password.toCharArray()));
        /*
         * String privatePem =
         * formatCert(Base64.encodeBase64StringLocal(privateKey.getEncoded())); String
         * leafPrivateKey = PRIVATE_KEY_BEGIN + privatePem + PRIVATE_KEY_END;
         * System.out.println(leafPrivateKey);
         */
        return pkEntry.getPrivateKey();
    }

    public PublicKey getPublicKey(String filename) throws Exception {
        PublicKey publicKey = null;

        String key = new String(Files.readAllBytes(Paths.get(filename)), Charset.defaultCharset());

        String publicKeyTemp = key.replace("-----BEGIN PUBLIC KEY-----", "").replaceAll(System.lineSeparator(), "")
            .replaceAll("\\n", "").replace("-----END PUBLIC KEY-----", "")
            .replace("-----BEGIN CERTIFICATE-----", "").replace("-----END CERTIFICATE-----", "");

        Base64 base64 = new Base64();
        String sExtFile = getExtension(filename);
        if (sExtFile.equals("pem")) {
            byte[] decoded = base64.decode(publicKeyTemp);

            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance(constant.getKEY_FACTORY_ALGORITHM());
            publicKey = kf.generatePublic(spec);

        } else if (sExtFile.equals("cer")) {
            byte[] decoded = base64.decode(publicKeyTemp);
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            InputStream in = new ByteArrayInputStream(decoded);
            X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(in);
            publicKey = certificate.getPublicKey();

        }
        return publicKey;
    }


    private String getExtension(String sFile) {
        String extension = "";
        int i = sFile.lastIndexOf('.');
        int p = Math.max(sFile.lastIndexOf('/'), sFile.lastIndexOf('\\'));
        if (i > p) {
            extension = sFile.substring(i + 1);
        }
        return extension;
    }

    public static String bytesToString(byte[] bytes) {
        String result = java.util.Base64.getEncoder().encodeToString(bytes);
        return result;
    }

}
