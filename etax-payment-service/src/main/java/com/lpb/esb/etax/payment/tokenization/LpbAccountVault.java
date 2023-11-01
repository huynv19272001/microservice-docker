/**
 * @author Trung.Nguyen
 * @date 05-Jul-2022
 * */
package com.lpb.esb.etax.payment.tokenization;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class LpbAccountVault {
	
	/**
	 * FlexCubs account of LPB.
	 * <p>
	 * Description: <blockquote>
	 * 
	 * <pre>
	 * IIN Ranges: 
	 * Length: 12
	 * </pre>
	 * 
	 * </blockquote>
	 */
	private final String LPB_ACCOUNT = "^[0-9]{12}$";
		
	/**
	 * @param digitsThatMightAccountNumber
	 * @param regex
	 * @return
	 */
	private boolean checkFCubsAccountType(String digitsThatMightAccountNumber, String regex) {

		if ((digitsThatMightAccountNumber == null) || (digitsThatMightAccountNumber.trim().equals("")))
			return false;

		if ((regex == null) || (regex.trim().equals("")))
			return false;

		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(digitsThatMightAccountNumber);

		return m.matches();
	}
	
	/**
	 * @param digitsThatMightAccountNumber
	 * @return
	 */
	public boolean isFCubsAccount(String digitsThatMightAccountNumber) {

		boolean chkFAccType = checkFCubsAccountType(digitsThatMightAccountNumber, LPB_ACCOUNT);

		return chkFAccType;
	}
	
	/**
	 * Reverse data
	 * */
	private String reverse(String accountNumber) {
		StringBuilder sb = new StringBuilder();
		for (int i = accountNumber.length() - 1; i >= 0; i--) {
			sb.append(accountNumber.substring(i, i + 1));
		}
		return sb.toString();
	}
	
	/**
	 * Mess data
	 * */
	public String mess(String accountNumber) {
		return reverse(accountNumber);
	}

}
