package com.esb.transaction.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;

public class RSAUtils {
    private static final String CHARSET_UTF8 = "UTF8";

    public static String encryptString(String inputString, String module, String exponentString) throws Exception {
        byte[] bytes = inputString.getBytes(CHARSET_UTF8);

        int maxLength = 1024 / 8 - 42;
        int dataLength = bytes.length;
        int iterations = dataLength / maxLength;
        StringBuilder stringBuilder = new StringBuilder();
        Cipher cipher = createCipherEncrypt(module, exponentString);
        for (int i = 0; i <= iterations; i++) {
            byte[] tempBytes = new byte[(dataLength - maxLength * i > maxLength) ? maxLength
                    : dataLength - maxLength * i];
            tempBytes = Arrays.copyOfRange(bytes, maxLength * i, maxLength * i
                    + tempBytes.length);
            byte[] encryptedBytes = cipher.doFinal(tempBytes);
            reverse(encryptedBytes);
            stringBuilder
                    .append(new String(Base64.encodeBase64(encryptedBytes)));
        }
        return stringBuilder.toString();
    }

    public static String decryptString(String inputString, String dmodule, String delement) throws Exception {
        int dwKeySize = 1024;
        int base64BlockSize = ((dwKeySize / 8) % 3 != 0) ? (((dwKeySize / 8) / 3) * 4) + 4
                : ((dwKeySize / 8) / 3) * 4;
        int iterations = inputString.length() / base64BlockSize;
        byte[] byteResult = new byte[0];
        Cipher cipher = createCipherDecrypt(dmodule, delement);
        for (int i = 0; i < iterations; i++) {
            byte[] encryptedBytes = DatatypeConverter
                    .parseBase64Binary(inputString.substring(base64BlockSize
                            * i, base64BlockSize * i + base64BlockSize));
            reverse(encryptedBytes);
            byteResult = append(byteResult, cipher.doFinal(encryptedBytes));

        }
        return new String(byteResult, CHARSET_UTF8);
    }

    private static Cipher createCipherEncrypt(String module, String exponentString) throws Exception {
        // String publicKeyXml = AppManager.getString("CORE_CARD_PUBLIC_KEY");
        //String publicKeyXml = "<RSAKeyValue><Modulus>07r5u7cwOkTrcpIv7VaHRF6ClEDzba9ONNoPByhyN9/rC3ysnuoMLq9dOiCfj8gJhzeU2aZ/o8ub1xFKeS90gEUcWqEz/wNBN+XOF0ZkFsumoWB1geGyxn+vCfZrUQahg0bI/ndB2AGpPbhbTnaO6oXjRz5vkqS8zweH/8gemrU=</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>";

        byte[] modulusBytes = com.sun.org.apache.xml.internal.security.utils.Base64
                .decode(module);
        byte[] exponentBytes = com.sun.org.apache.xml.internal.security.utils.Base64
                .decode(exponentString);
        BigInteger modulus = new BigInteger(1, modulusBytes);
        BigInteger exponent = new BigInteger(1, exponentBytes);

        RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PublicKey pubKey = fact.generatePublic(rsaPubKey);

        // PublicKey publicKey = XmlRSA.getPublicKeyFromXML(publicKeyXml);
        Cipher cipher = Cipher
                .getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher;
    }

    private static Cipher createCipherDecrypt(String dmodule, String delement) throws Exception {
        // String privateKeyXml = AppManager.getString("CORE_CARD_PRIVATE_KEY");
        // String privateKeyXml =
        // "<RSAKeyValue><Modulus>07r5u7cwOkTrcpIv7VaHRF6ClEDzba9ONNoPByhyN9/rC3ysnuoMLq9dOiCfj8gJhzeU2aZ/o8ub1xFKeS90gEUcWqEz/wNBN+XOF0ZkFsumoWB1geGyxn+vCfZrUQahg0bI/ndB2AGpPbhbTnaO6oXjRz5vkqS8zweH/8gemrU=</Modulus><Exponent>AQAB</Exponent><P>7dDzIHMNHLiJdWNhUUjo7b/uvK2Oa1NGi0UVEQIWP3OlnZxfGAhYRIg9+hWuq5tIUOWaLZ3OlnKYj3ChCfKXqw==</P><Q>4+tq7zvkp5vUmQu5I+k5DORy0nE2suquUq65rjGve8k7AEZwRo+sBW3YKjKsO16Ef//JD7jPY9sqSZTU7Pm3Hw==</Q><DP>CffXKQYJh6mogVYgDyMqOETyQdBYhuEftNi5cFQREKnNSFT0m3JGGOFJD8F259SDbHijRMQ3k5DMGj8TUPJtLw==</DP><DQ>dSvmAa9s0a4PEc8xEwEnTzVywjVpxXlYvTEOTDtG2ACP0Ihl2CCscu4Rn0AFw0/IFnXNS8Pa+p8FkmZtQJbwSQ==</DQ><InverseQ>GxbONsojpIGWkByp7KtEYK8xpzBF7ym7Eh6CkT28yOC6BrH3AOiH5JuujKFia/Rnt8CtLEF3KcpUX5DkFnNyVw==</InverseQ><D>p4jObMNyDNlV+G1GMSWDmQyzhD20oLjTSCy3MXHGlwG+SXOYX5JQm2w1d3Yy66FcMLtBM8t0OhY1NMNAy41KP3hpnj9DKYz9+w1Y4vFg9xefEQJDANAkEnBH5340nCrG8ZJ1uRRp5NX5k7quGD0nv77kGtLnfUmS3u+6aPS4OK0=</D></RSAKeyValue>";

        byte[] expBytes = com.sun.org.apache.xml.internal.security.utils.Base64
                .decode(delement);
        byte[] modBytes = com.sun.org.apache.xml.internal.security.utils.Base64
                .decode(dmodule);

        BigInteger modules = new BigInteger(1, modBytes);
        BigInteger exponent = new BigInteger(1, expBytes);

        KeyFactory factory = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec privSpec = new RSAPrivateKeySpec(modules, exponent);
        PrivateKey privKey = factory.generatePrivate(privSpec);

        // PrivateKey privateKey = XmlRSA.getPrivateKeyFromXML(privateKeyXml);
        Cipher cipher = Cipher
                .getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        return cipher;
    }

    public static void reverse(byte[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        byte tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    private static byte[] append(byte[] byteResult, byte[] byteAppend) {
        int byteResultLength = byteResult.length;
        int byteAppendLength = byteAppend.length;
        if (byteResultLength == 0) {
            return byteAppend;
        }
        byte[] rs = new byte[byteResultLength + byteAppendLength];
        for (int i = 0; i < byteResultLength; i++) {
            rs[i] = byteResult[i];
        }
        for (int i = byteResultLength; i < byteResultLength + byteAppendLength; i++) {
            rs[i] = byteAppend[i - byteResultLength];
        }
        return rs;
    }

    public static String genneSHA1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }


    public static String decryptStr(String inputString, String key) throws Exception {
        if (!"".equals(key) && inputString != null && !"".equals(inputString)) {
            Document doc = createXmlDocument(key);
            String dmodule = parserXmlElement(doc, "Modulus");
            String delement = parserXmlElement(doc, "D");
            return decryptString(inputString, dmodule, delement);
        }

        return "";

    }

    public static String encryptStr(String inputString, String key) throws Exception {
        if (!"".equals(key) && inputString != null && !"".equals(inputString)) {
            Document doc = createXmlDocument(key);
            String module = parserXmlElement(doc, "Modulus");
            String exponentString = parserXmlElement(doc, "Exponent");
            return encryptString(inputString, module, exponentString);
        }

        return "";

    }

    public static Document createXmlDocument(String xml) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(xml));

        Document doc = builder.parse(src);
        return doc;
    }

    public static String parserXmlElement(Document doc, String tagName) {
        try {
            return doc.getElementsByTagName(tagName).item(0).getTextContent();
        } catch (Exception e) {
            return "";
        }
    }
}
