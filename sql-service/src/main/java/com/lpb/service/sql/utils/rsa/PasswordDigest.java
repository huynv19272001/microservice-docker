package com.lpb.service.sql.utils.rsa;

import com.lpb.service.sql.model.PassCore;
import com.lpb.service.sql.utils.StringUtils;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class PasswordDigest {
	public static byte[] getDigest(byte[] password, byte[] salt, int iterationNo) throws Exception {
		byte[] hash = null;

		MessageDigest digester = MessageDigest.getInstance("SHA-512");
		for (int i = 0; i < iterationNo; i++) {
			digester.reset();
			digester.update(password);
			digester.update(salt);

			hash = digester.digest();
		}
		return hash;
	}

	public static byte[] generateSalt() throws Exception {
		byte[] salt = null;

		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		salt = new byte[16];
		random.nextBytes(salt);

		return salt;
	}

	public static void generatePass(PassCore passCore) {
		try {
			byte[] saltBytes = null;
			String salt = null;
			int itrNo = 10000;

			String PWD_ALG_NAME = "SHA-512";
			String newPasswordEcr = "";

			salt = StringUtils.byteToHex(PasswordDigest.generateSalt());
			saltBytes = StringUtils.hexToByte(salt);

			newPasswordEcr = StringUtils
					.byteToHex(PasswordDigest.getDigest(passCore.getPASSWORD().getBytes(), saltBytes, itrNo));

			System.out.println(PWD_ALG_NAME + "!" + itrNo + "!" + salt + "!" + newPasswordEcr);
			passCore.setSALT(salt);
			passCore.setGENERATE_PASSWORD(PWD_ALG_NAME + "!" + itrNo + "!" + newPasswordEcr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
