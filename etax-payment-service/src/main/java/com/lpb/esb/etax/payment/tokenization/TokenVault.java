/**
 * @author Trung.Nguyen
 * @date 05-Jul-2022
 * */
package com.lpb.esb.etax.payment.tokenization;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenVault {
	
	@Autowired
	private LpbAccountVault lpbAccountVault;
	@Autowired
	private LpbCardVault lpbCardVault;
	
	public String tokenizeAccount(String accountNumber) throws Exception {
		if (!lpbAccountVault.isFCubsAccount(accountNumber)) {
			throw new Exception("Account number is invalid");
		}
		String messData = lpbCardVault.mess(accountNumber);
		return this.hash512(messData);
	}
	
	public String tokenizeCard(String cardNumber) throws Exception {
		if (!lpbCardVault.isLocalDebitCard(cardNumber)) {
			throw new Exception("Card number is invalid");
		}
		String messData = lpbCardVault.mess(cardNumber);
		return this.hash512(messData);
	}
	
	private String hash512(String inputData) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-512");
        byte[] result = mDigest.digest(inputData.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 32).substring(1));
        }
        return sb.toString();
	}

}