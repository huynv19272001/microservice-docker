/**
 * @author Trung.Nguyen
 * @date 28-Apr-2022
 * */
package com.lpb.esb.etax.payment.util.sign;

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
