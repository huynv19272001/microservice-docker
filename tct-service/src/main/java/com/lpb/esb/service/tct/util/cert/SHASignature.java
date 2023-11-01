package com.lpb.esb.service.tct.util.cert;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class SHASignature {

    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    // private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    // private static final String SIGNATURE_ALGORITHM = "NONEWithRSA";

    private String pfxPass = "123456";

    public byte[] sign(String data, String privateKey) throws InvalidKeyException, Exception {
        Security.addProvider(new BouncyCastleProvider());
        Signature rsa = Signature.getInstance(SIGNATURE_ALGORITHM, "BC");
        rsa.initSign(getPrivateKeyFromPFX(privateKey));
        rsa.update(data.getBytes(StandardCharsets.UTF_8));
        return rsa.sign();
    }

//    public boolean verify(String publicKeyFile, String signedData, String signature) {
//
//        try {
//            Security.addProvider(new BouncyCastleProvider());
//            PublicKey publicKey = getPublicKey(publicKeyFile);
//            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM, "BC");
//            sig.initVerify(publicKey);
//
//            Base64 base64 = new Base64();
//
//            byte[] dataBytes = base64.encode(signedData.getBytes(StandardCharsets.UTF_8));
//            dataBytes = base64.decode(dataBytes);
//            sig.update(signedData.getBytes(StandardCharsets.UTF_8));
//
//            if (!sig.verify(base64.decode(signature.getBytes(StandardCharsets.UTF_8)))) {
//                return false;
//            }
//
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public PrivateKey getPrivateKeyFromPFX(String filename) throws Exception {
        KeyStore ks = KeyStore.getInstance("pkcs12", "SunJSSE");
        // KeyStore ks = KeyStore.getInstance("PKCS11");

        ks.load(new FileInputStream(filename), pfxPass.toCharArray());
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
            new KeyStore.PasswordProtection(pfxPass.toCharArray()));
        /*
         * String privatePem =
         * formatCert(Base64.encodeBase64StringLocal(privateKey.getEncoded())); String
         * leafPrivateKey = PRIVATE_KEY_BEGIN + privatePem + PRIVATE_KEY_END;
         * System.out.println(leafPrivateKey);
         */
        return pkEntry.getPrivateKey();

    }

    public KeyStore.PrivateKeyEntry getPrvFromPFX(String filename) throws Exception {
        KeyStore ks = KeyStore.getInstance("pkcs12", "SunJSSE");
        // KeyStore ks = KeyStore.getInstance("PKCS11");

        ks.load(new FileInputStream(filename), pfxPass.toCharArray());
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
            new KeyStore.PasswordProtection(pfxPass.toCharArray()));
        /*
         * String privatePem =
         * formatCert(Base64.encodeBase64StringLocal(privateKey.getEncoded())); String
         * leafPrivateKey = PRIVATE_KEY_BEGIN + privatePem + PRIVATE_KEY_END;
         * System.out.println(leafPrivateKey);
         */
        return pkEntry;

    }

//    public PublicKey getPublicKey(String filename) throws Exception {
//        PublicKey publicKey = null;
//
//        String key = new String(Files.readAllBytes(Paths.get(filename)), Charset.defaultCharset());
//
//        String publicKeyTemp = key.replace("-----BEGIN PUBLIC KEY-----", "").replaceAll(System.lineSeparator(), "")
//            .replaceAll("\\n", "").replace("-----END PUBLIC KEY-----", "")
//            .replace("-----BEGIN CERTIFICATE-----", "").replace("-----END CERTIFICATE-----", "");
//
//        Base64 base64 = new Base64();
//        String sExtFile = getExtension(filename);
//        if (sExtFile.equals("pem")) {
//            byte[] decoded = base64.decode(publicKeyTemp);
//
//            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
//            KeyFactory kf = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
//            publicKey = kf.generatePublic(spec);
//
//        } else if (sExtFile.equals("cer")) {
//            byte[] decoded = base64.decode(publicKeyTemp);
//            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
//            InputStream in = new ByteArrayInputStream(decoded);
//            X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(in);
//            publicKey = certificate.getPublicKey();
//
//        }
//        return publicKey;
//    }

    private String getExtension(String sFile) {
        String extension = "";
        int i = sFile.lastIndexOf('.');
        int p = Math.max(sFile.lastIndexOf('/'), sFile.lastIndexOf('\\'));
        if (i > p) {
            extension = sFile.substring(i + 1);
        }
        return extension;
    }

    /**
     * Inserts newline characters every 64 characters in the provided base64 encoded
     * pem string
     *
     * @param base64EncodedCert the body of the base64 encoded certificate
     * @return the properly formatted PEM string
     */
    private static String formatCert(String base64EncodedCert) {
        ArrayList<String> certLines = new ArrayList<>();
        while (base64EncodedCert.length() > 64) {
            // peel off the next 64 characters
            certLines.add(base64EncodedCert.substring(0, 64));
            base64EncodedCert = base64EncodedCert.substring(64);
        }
        certLines.add(base64EncodedCert);

        for (int i = 0; i < certLines.size(); i++) {
            // add newline to the end of each line
            certLines.set(i, certLines.get(i).concat(System.lineSeparator()));
        }

        String pemBody = "";
        for (int i = 0; i < certLines.size(); i++) {
            // add all lines back together
            pemBody += certLines.get(i);
        }

        return pemBody;
    }

    public static String bytes2String(byte[] bytes) {
        String result = java.util.Base64.getEncoder().encodeToString(bytes);
        return result;
    }

    public static String convert(String value, String fromEncoding, String toEncoding)
        throws UnsupportedEncodingException {
        return new String(value.getBytes(fromEncoding), toEncoding);
    }

    public static String charset(String value, String charsets[]) throws UnsupportedEncodingException {
        String probe = StandardCharsets.UTF_8.name();
        for (String c : charsets) {
            Charset charset = Charset.forName(c);
            if (charset != null) {
                if (value.equals(convert(convert(value, charset.name(), probe), probe, charset.name()))) {
                    return c;
                }
            }
        }
        return StandardCharsets.UTF_8.name();
    }

    public static String getSHAHash(String input) throws UnsupportedEncodingException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] messageDigest = md.digest(input.getBytes("utf-8"));
            return new String(java.util.Base64.getEncoder().encode(messageDigest));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
