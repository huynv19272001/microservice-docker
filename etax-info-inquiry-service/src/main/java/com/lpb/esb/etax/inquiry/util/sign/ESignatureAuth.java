/**
 * @author Trung.Nguyen
 * @date 19-Apr-2022
 * */
package com.lpb.esb.etax.inquiry.util.sign;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import java.util.Base64;
import java.util.List;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;

public class ESignatureAuth {

//	private static String loadPrivateKey() {
//		return null;
//	}

	public static boolean verify(String dataSignBase, String eSignBase64, String certChainBase64Encoded) {
		try {
			byte[] eSign = Base64.getDecoder().decode(eSignBase64);

			byte[] data = dataSignBase.getBytes();

			return executeVerifyByte(data, eSign, certChainBase64Encoded);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return false;
		}
	}

//	public static void main(String[] args) throws IOException, Exception {
//
//		// CertPath
//		String certChainBase64Encoded = "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";
//
//		String ckyDTuBase64 = "aHzDQX0mpw2K94vIxVE/LLDunppLNlwDEphE64nO0NE8tTwZlwukcn/a1yp7f0p2Y/J6xBvllxeMWUfZcL0wegETfwjO9iCyiHQOENVKuCDC02MucAOYtvdvmSL13Znx5FS+5XWYe+XGe2fyJcc8dSVUwrGNZFP/YOc05q2bdFaHzGIi/RNjWN03JPzGXd1Hblsroj8gvW6g822PcpGYMsperYYimnvl+pFyF45qiNvdYjxMESBBtlgkBHIJPkaYSXCUYcteSueiukeCh05+ovUssjODVJZzT64yYJuVmTOZ7fxoVnOkljT7YI2OF3bh3MpicyjVde+bFxhlkB+ylA==";
//
//		String dataSignBase = "ew0KICAgICJtYURvaVRhYyI6IjAxMDAxNTA2MSIsDQogICAgIm1zdCI6IjAxMDAyMzEyMjYtOTk5IiwNCgkidGVuX25udCI6Ik5ndXnhu4VuIELDrG5oIEFuIiwNCgkic29HaWF5VG8iOiIxMzIxNTY2NjY4ODg4IiwNCgkiZGllbnRob2FpIjoiMDk2MzUzNTY2IiwNCgkic29UYWlLaG9hbiI6IjEwMDM1MzUzMTE2IiwNCgkiZW1haWwiOiJ0ZXN0QHNlYXRlY2hpdC5jb20udm4iDQp9";
//
//		byte[] ckyDTu = Base64.getDecoder().decode(ckyDTuBase64);
//
//		byte[] dlieuByte = dataSignBase.getBytes();
//
//		boolean kquaXThucByte = executeVerifyByte(dlieuByte, ckyDTu, certChainBase64Encoded);
//
//		System.out.println("Kết quả xác thực kiểu dữ liệu Byte: " + kquaXThucByte);
//	}

	private static boolean executeVerifyByte(byte[] data, byte[] eSignature, String certChainBase64Encoded) {
		boolean verifyRs = false;
		// X509Certificate x509Cert = null;
		try {
			// neu dung CertPath doan nay
			CertPath certPath = loadCertPathFromBase64String(certChainBase64Encoded);
			List certsInChain = certPath.getCertificates();
			X509Certificate[] certChain = (X509Certificate[]) certsInChain.toArray(new X509Certificate[0]);
			 // get public key TCT
			X509Certificate x509Cert = certChain[0];

			verifyRs = getSignatureVerify(data, eSignature, x509Cert);
		} catch (Exception ex) {
			ex.printStackTrace();
			return verifyRs;
		}
		return verifyRs;
	}

	private static boolean getSignatureVerify(byte[] contentFile, byte[] eSignature, X509Certificate x509Cert)
			throws Exception {
		String sigAlgName = "SHA256withRSA";
		if (x509Cert.getSigAlgName().equals("SHA1withRSA")) {
			sigAlgName = "SHA1withRSA";
		}

		boolean result = true;
		try {
			PublicKey publicKey = x509Cert.getPublicKey();
			Signature verify = Signature.getInstance(sigAlgName);
			verify.initVerify(publicKey);
			verify.update(contentFile);
			result = verify.verify(eSignature);
		} catch (NoSuchAlgorithmException e) {
			result = false;
			e.getLocalizedMessage();
		} catch (InvalidKeyException e) {
			result = false;
			e.getLocalizedMessage();
		} catch (SignatureException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Loads a certification chain from given Base64-encoded string, containing
	 * ASN.1 DER formatted chain, stored with PkiPath encoding.
	 */
	private static CertPath loadCertPathFromBase64String(String aCertChainBase64Encoded)
			throws CertificateException, IOException {
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
