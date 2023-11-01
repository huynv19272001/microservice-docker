/**
 * @author Trung.Nguyen
 * @date 05-Jul-2022
 * */
package com.lpb.esb.etax.payment.tokenization;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class LpbCardVault {
	
	/**
	 * Debit card of LPB.
	 * <p>
	 * Description: <blockquote>
	 * 
	 * <pre>
	 * IIN Ranges: 970449
	 * Length: 16
	 * </pre>
	 * 
	 * </blockquote>
	 */
	private final String LPB_DEBIT_CARD = "^970449[0-9]{10}$";
	
	/**
	 * Attempts to check card number by the Luhn Algorithm.
	 * <p>
	 * 
	 * @param cardNumber
	 * @return true if, and only if, the issuer institution used the Luhn Algorithm.
	 * @throws NumberFormatException if the string does not contain parsable integer.
	 */
	private boolean checksum(String cardNumber) {
		if ((cardNumber == null) || (cardNumber.trim().equals("")))
			return false;
		
		int sum = 0;
		boolean alternate = false;
		for (int i = cardNumber.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(cardNumber.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}

		return (sum % 10 == 0);
	}
	
	/**
	 * @param digitsThatMightCardNumber
	 * @param regex
	 * @return
	 */
	private boolean checkDebitCardType(String digitsThatMightCardNumber, String regex) {

		if ((digitsThatMightCardNumber == null) || (digitsThatMightCardNumber.trim().equals("")))
			return false;

		if ((regex == null) || (regex.trim().equals("")))
			return false;

		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(digitsThatMightCardNumber);

		return m.matches();
	}
	
	/**
	 * @param digitsThatMightCardNumber
	 * @return
	 */
	public boolean isLocalDebitCard(String digitsThatMightCardNumber) {

		boolean chkDCType = checkDebitCardType(digitsThatMightCardNumber, LPB_DEBIT_CARD);

		if (chkDCType) {
//			return this.checksum(digitsThatMightCardNumber);
		}

		return chkDCType;
	}
	
	/**
	 * Reverse data
	 * */
	private String reverse(String cardNumber) {
		StringBuilder sb = new StringBuilder();
		for (int i = cardNumber.length() - 1; i >= 0; i--) {
			sb.append(cardNumber.substring(i, i + 1));
		}
		return sb.toString();
	}
	
	/**
	 * Mess data
	 * */
	public String mess(String cardNumber) {
		return reverse(cardNumber);
	}
	
	
	
	
	
	public static void main(String[] agrs) {
		LpbCardVault lpbCardVault = new LpbCardVault();
		String cardNumber = "9704490045245115";		// 9704490300000006262
		String messData = lpbCardVault.mess(cardNumber);
		System.out.println(messData);
	}

}
