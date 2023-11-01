package com.lpb.esb.service.common.utils.rsa;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;

/**
 * RSA PAYMENT GATEWAY
 *
 * @author hunglv5
 */
public class RSAUtil {
    //	private static String module = "u9gTDzINo0zUsc+hubvt4GYKil8nE8ZUppCLOfa+fTBsvUiTPIAZXTutXYCalP30C6I1x54MOck/hFhco/khwgSOC/oS9tytOPsQAk1rN1cZjV20W8ZtyzKQu1GiE9WQv2Re39uhoPQqtyvXYOnYJfmRqCqkkXeRiV8uU5eHC+s=";
    private static String exponentString = "AQAB";
//	private static String delement = "iHeGATgf6E9dBoENwV3ih3AaoERYo2km/otsWoQEB/cySKF9NirqQm2kmZVguHV24yzJXWhZAXJPjtqTEx1fg9EZrBvFODNntaPdpBsQoWQ9poc34VMktb8ry4UfMZOqe/9WUjwZSCpXB9oFfDs2ZftWxfbEURGw5r3Y7IWT5+E=";

    // comment out

    /**
     * @param strEnc
     * @param module="u9gTDzINo0zUsc+hubvt4GYKil8nE8ZUppCLOfa+fTBsvUiTPIAZXTutXYCalP30C6I1x54MOck/hFhco/khwgSOC/oS9tytOPsQAk1rN1cZjV20W8ZtyzKQu1GiE9WQv2Re39uhoPQqtyvXYOnYJfmRqCqkkXeRiV8uU5eHC+s=";
     * @param delement="iHeGATgf6E9dBoENwV3ih3AaoERYo2km/otsWoQEB/cySKF9NirqQm2kmZVguHV24yzJXWhZAXJPjtqTEx1fg9EZrBvFODNntaPdpBsQoWQ9poc34VMktb8ry4UfMZOqe/9WUjwZSCpXB9oFfDs2ZftWxfbEURGw5r3Y7IWT5+E=";
     * @param keySize
     * @return
     * @UTF32
     */


    public static String decryptRSAWithKeyLength(String strEnc, String delement, String module, int keySize) {
        try {
            int dwKeySize = 1024;
            int base64BlockSize = ((dwKeySize / 8) % 3 != 0) ? (((dwKeySize / 8) / 3) * 4) + 4
                : ((dwKeySize / 8) / 3) * 4;

            int iterations = strEnc.length() / base64BlockSize;
            byte[] byteResult = new byte[0];
            Cipher cipher = createCipherDecrypt(delement, module);
            for (int i = 0; i < iterations; i++) {
                byte[] encryptedBytes = DatatypeConverter
                    .parseBase64Binary(strEnc.substring(base64BlockSize
                        * i, base64BlockSize * i + base64BlockSize));
                reverse(encryptedBytes);
                byteResult = append(byteResult, cipher.doFinal(encryptedBytes));
            }
            return new String(byteResult, Charset.forName("UTF-32LE"));
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }


    public static String encryptRSAWithKeyLength(String inputString, String module, int keySize) {
        try {
            byte[] bytes = inputString.getBytes(Charset.forName("UTF-32LE"));

            int maxLength = keySize / 8 - 42;
            int dataLength = bytes.length;
            int iterations = dataLength / maxLength;
            StringBuilder stringBuilder = new StringBuilder();
            Cipher cipher = createCipherEncrypt(module);
            for (int i = 0; i <= iterations; i++) {
                byte[] tempBytes = new byte[(dataLength - maxLength * i > maxLength) ? maxLength
                    : dataLength - maxLength * i];
                tempBytes = Arrays.copyOfRange(bytes, maxLength * i, maxLength * i
                    + tempBytes.length);
                byte[] encryptedBytes = cipher.doFinal(tempBytes);
                reverse(encryptedBytes);
                stringBuilder.append(new String(new RSAUtil().encodeBase64(encryptedBytes)));
            }
            return stringBuilder.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    public static String decryptData(String dataDecrypt, String privateKeyXml) {
        try {
            RSABean objRsa = new RSAUtil().readKeyXml(privateKeyXml);
            byte[] modBytes = new RSAUtil().decodeBase64(objRsa.getModulus());
            byte[] dBytes = new RSAUtil().decodeBase64(objRsa.getDelement());

            BigInteger modules = new BigInteger(1, modBytes);
            BigInteger d = new BigInteger(1, dBytes);

            KeyFactory factory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance(RSAConstants.RSA_INSTANCE);

            RSAPrivateKeySpec privSpec = new RSAPrivateKeySpec(modules, d);
            PrivateKey privKey = factory.generatePrivate(privSpec);
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            byte[] decrypted = cipher.doFinal(new RSAUtil().decodeBase64(dataDecrypt));
            String decrypt = new String(decrypted);
            return decrypt;
        } catch (Exception ex) {
            ex.printStackTrace();
            //Utilities.writeLog("Exception Decrypt: " + ex.getMessage());
            return "ERROR";
        }
    }

    public static String encryptData(String dataEncrypt, String publicKey) {
        try {
            RSABean objRsa = new RSAUtil().readKeyXml(publicKey);

            byte[] modulusBytes = new RSAUtil().decodeBase64(objRsa.getModulus());
            byte[] exponentBytes = new RSAUtil().decodeBase64(RSAConstants.EXPONENT_STRING);

            BigInteger modulus = new BigInteger(1, modulusBytes);
            BigInteger exponent = new BigInteger(1, exponentBytes);

            RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PublicKey pubKey = fact.generatePublic(rsaPubKey);

            Cipher cipher = Cipher.getInstance(RSAConstants.RSA_INSTANCE);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            byte[] encryptByte = cipher.doFinal(dataEncrypt.getBytes());

            return new RSAUtil().encodeBase64(encryptByte);
        } catch (Exception ex) {
            return "";
        }
    }

    private static Cipher createCipherEncrypt(String module) throws Exception {
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

    /**
     * INPUT KEY
     *
     * @param delement
     * @param module
     * @return
     * @throws Exception
     */
    private static Cipher createCipherDecrypt(String delement, String module) throws Exception {
        byte[] expBytes = com.sun.org.apache.xml.internal.security.utils.Base64
            .decode(delement);
        byte[] modBytes = com.sun.org.apache.xml.internal.security.utils.Base64
            .decode(module);

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

    /**
     * PRIVATE METHOD
     *
     * @param dataToDecode
     * @return
     * @throws Exception
     */
    private byte[] decodeBase64(String dataToDecode) throws Exception {
        byte[] dataDecoded = null;
        return dataDecoded;
    }

    public static String encodeBase64(byte[] byteForEncode) {
        return null;
    }

    private RSABean readKeyXml(String keyXml) throws Exception {
        RSABean rsaObj = new RSABean();
        InputSource source = new InputSource(new StringReader(keyXml));

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(source);

        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();


        rsaObj.setModulus(xpath.evaluate("/RSAKeyValue/Modulus", document));
        rsaObj.setDelement(xpath.evaluate("/RSAKeyValue/D", document));
        return rsaObj;
    }


}
