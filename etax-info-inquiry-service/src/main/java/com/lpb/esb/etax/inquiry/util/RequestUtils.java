/**
 * @author Trung.Nguyen
 * @date 26-Apr-2022
 * */
package com.lpb.esb.etax.inquiry.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.lpb.esb.etax.inquiry.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.inquiry.model.config.GeneralTransConfig;
import com.lpb.esb.etax.inquiry.model.config.TaxInfoConstants;
import com.lpb.esb.etax.inquiry.model.data.TaxPayerInfo;
import com.lpb.esb.etax.inquiry.model.data.TransConfirmation;
import com.lpb.esb.etax.inquiry.model.request.EtaxEncryptedRequest;
import com.lpb.esb.etax.inquiry.model.response.DefaultResponse;

@Component
public class RequestUtils {

	public boolean validEtaxEncryptedRequest(EtaxEncryptedRequest etaxEncryptedRequest) {
		boolean rsValid = false;

		// Check object
		if (etaxEncryptedRequest == null)
			return rsValid;

		// Check signature object
		if (etaxEncryptedRequest.getESign() == null)
			return rsValid;

		// Check signature value
		if ((etaxEncryptedRequest.getESign().getValue() == null)
				|| (etaxEncryptedRequest.getESign().getValue().trim().equals("")))
			return rsValid;

		// Check signature certificate
		if ((etaxEncryptedRequest.getESign().getCertificates() == null)
				|| (etaxEncryptedRequest.getESign().getCertificates().trim().equals("")))
			return rsValid;

		// Check base64 data
		if ((etaxEncryptedRequest.getDataBase64() == null)
				|| (etaxEncryptedRequest.getDataBase64().trim().equals("")))
			return rsValid;

		return !rsValid;
	}

	public DefaultResponse validateTaxPayerInfo(TaxPayerInfo taxPayerInfo) {
		DefaultResponse response = new DefaultResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);

		// Check object
		if (taxPayerInfo == null) {
			return response;
		}

        // Check partner code (partner tax code)
        String regex = "[0-9a-zA-Z]{1,14}$";
        if(!this.validateCharacter(taxPayerInfo.getPartnerCode(), regex)) {
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
            return response;
        }

        // Check tax code
        if(!this.validateCharacter(taxPayerInfo.getTaxCode(), regex)) {
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
            return response;
        }

		// Check action code: Open & Close
		if ((taxPayerInfo.getActionCode() != null)
				&& !taxPayerInfo.getActionCode().equalsIgnoreCase(TaxInfoConstants.LINK_ETAX_ACCOUNT)
				&& !taxPayerInfo.getActionCode().equalsIgnoreCase(TaxInfoConstants.UNLINK_ETAX_ACCOUNT))
			return response;

		// Check partner code (partner tax code)
//		if ((taxPayerInfo.getPartnerCode() == null) || taxPayerInfo.getPartnerCode().trim().equals("")) {
//			response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
//			response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
//			return response;
//		}

		// Check tax code
//		if ((taxPayerInfo.getTaxCode() == null) || taxPayerInfo.getTaxCode().trim().equals(""))
//			return response;

		// Check tax payer name
//		if ((taxPayerInfo.getPayerFullName() == null) || taxPayerInfo.getPayerFullName().trim().equals("")) {
//			response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
//			response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
//			return response;
//		}

		if ((taxPayerInfo.getActionCode() == null) || TaxInfoConstants.LINK_ETAX_ACCOUNT.equalsIgnoreCase(taxPayerInfo.getActionCode())) {
			// Check phone number
			if ((taxPayerInfo.getPhoneNumber() == null) || taxPayerInfo.getPhoneNumber().trim().equals("")) {
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_058);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_058);
				return response;
			}

			// Check identification type: CMND, CCCD, HC
			if (!TaxInfoConstants.IDENTIFICATION.equalsIgnoreCase(taxPayerInfo.getIdentificationType())
					&& !TaxInfoConstants.CITIZEN_IDENTITY.equalsIgnoreCase(taxPayerInfo.getIdentificationType())
					&& !TaxInfoConstants.PASSPORT.equalsIgnoreCase(taxPayerInfo.getIdentificationType())) {
				return response;
			} else {
				// Check identification number
				if ((taxPayerInfo.getIdentificationNumber() == null) || taxPayerInfo.getIdentificationNumber().trim().equals("")) {
					if (TaxInfoConstants.IDENTIFICATION.equalsIgnoreCase(taxPayerInfo.getIdentificationType())) {
						response.setResponseCode(EtaxErrorConfig.ERROR_CODE_052);
						response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_052);
					} else if (TaxInfoConstants.CITIZEN_IDENTITY.equalsIgnoreCase(taxPayerInfo.getIdentificationType())) {
						response.setResponseCode(EtaxErrorConfig.ERROR_CODE_053);
						response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_053);
					} else if (TaxInfoConstants.PASSPORT.equalsIgnoreCase(taxPayerInfo.getIdentificationType())) {
						response.setResponseCode(EtaxErrorConfig.ERROR_CODE_054);
						response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_054);
					}
					return response;
				}
			}

			// Check require type: ACCOUNT or CARD
			if (!TaxInfoConstants.BANK_ACCOUNT.equalsIgnoreCase(taxPayerInfo.getRequireType())
					&& !TaxInfoConstants.BANK_CARD.equalsIgnoreCase(taxPayerInfo.getRequireType())) {
				return response;
			} else {
				// Check require number
				if ((taxPayerInfo.getRequireNumber() == null) || taxPayerInfo.getRequireNumber().trim().equals("")) {
					return response;
				}
				// Check length and format
				if (TaxInfoConstants.BANK_ACCOUNT.equalsIgnoreCase(taxPayerInfo.getRequireType())) {
					if(!this.validateAccount(taxPayerInfo.getRequireNumber())) {
						response.setResponseCode(EtaxErrorConfig.ERROR_CODE_056);
						response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_056);
						return response;
					}
				} else if (TaxInfoConstants.BANK_CARD.equalsIgnoreCase(taxPayerInfo.getRequireType())) {
					if(!this.validateDebitCard(taxPayerInfo.getRequireNumber())) {
						response.setResponseCode(EtaxErrorConfig.ERROR_CODE_057);
						response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_057);
						return response;
					}
                    // Check issue date
                    if ((taxPayerInfo.getIssueDate() == null) || taxPayerInfo.getIssueDate().trim().equals("")) {
                        response.setResponseCode(EtaxErrorConfig.ERROR_CODE_061);
                        response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_061);
                        return response;
                    }
				}
			}
		} else if (TaxInfoConstants.UNLINK_ETAX_ACCOUNT.equalsIgnoreCase(taxPayerInfo.getActionCode())) {
			// Check token
			if ((taxPayerInfo.getEwalletToken() == null) || taxPayerInfo.getEwalletToken().trim().equals("")) {
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_132);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_132);
				return response;
			}
		}
		// return
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
		return response;
	}

	public DefaultResponse validateTransConfirmation(TransConfirmation transConfirmation) {
		DefaultResponse response = new DefaultResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);

		// Check object request
		if (transConfirmation == null)
			return response;

		// Check message id
		if ((transConfirmation.getAppMsgId() == null) || (transConfirmation.getAppMsgId().trim().equals("")))
			return response;

		// Check OTP code
		if ((transConfirmation.getOtpCode() == null) || (transConfirmation.getOtpCode().trim().equals("")))
			return response;

		// Check verification type
		if (!GeneralTransConfig.VERIFICATION_SMS_OTP.equalsIgnoreCase(transConfirmation.getVerificationType()))
			return response;
		// return
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
		return response;
	}

	private boolean validateAccount(String accountNumber) {
		String regex = "^[0-9]{12}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(accountNumber);
		return m.matches();
	}

	private boolean validateDebitCard(String cardNumber) {
		String regex = "^970449[0-9]{10}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(cardNumber);
		return m.matches();
	}

    private boolean validateCharacter(String data, String regex) {
        if ((data == null) || data.trim().equals("")) return false;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(data);
        return m.matches();
    }

}
