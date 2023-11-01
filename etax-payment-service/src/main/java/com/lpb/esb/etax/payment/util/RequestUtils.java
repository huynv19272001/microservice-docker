/**
 * @author Trung.Nguyen
 * @date 28-Apr-2022
 * */
package com.lpb.esb.etax.payment.util;

import com.lpb.esb.etax.payment.model.data.TaxPaymentInfo;
import org.springframework.stereotype.Component;

import com.lpb.esb.etax.payment.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.payment.model.config.GeneralTransConfig;
import com.lpb.esb.etax.payment.model.data.TransConfirmation;
import com.lpb.esb.etax.payment.model.request.EtaxEncryptedRequest;
import com.lpb.esb.etax.payment.model.response.DefaultResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public DefaultResponse validateTransConfirmation(TransConfirmation transConfirmation) {
		DefaultResponse response = new DefaultResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);

		// Check object request
		if (transConfirmation == null)
			return response;

		// Check message id
		if ((transConfirmation.getTransId() == null) || (transConfirmation.getTransId().trim().equals("")))
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

    /**
     * @author: Trung.Nguyen
     * @since: 27-Sep-2022
     * */
    public DefaultResponse validateTaxPaymentInfo(TaxPaymentInfo taxPaymentInfo) {
        DefaultResponse response = new DefaultResponse();
        response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
        response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
        String regex = "";

        // Check object request
        if (taxPaymentInfo == null)
            return response;

        if (taxPaymentInfo.getTaxReceipt() == null)
            return response;

        if (taxPaymentInfo.getTaxReceipt().getTaxFees() == null)
            return response;

        if (taxPaymentInfo.getTaxReceipt().getTaxReceiptDetails() == null)
            return response;

        // Check reference number
        regex = "[0-9a-zA-Z]{1,20}$";
        if(!this.validateCharacter(taxPaymentInfo.getReferenceNumber(), regex)) {
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
            return response;
        }

        // Check total mount for payment
        regex = "[0-9]{1,40}$";
        if(!this.validateCharacter(taxPaymentInfo.getAmount(), regex)) {
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
            return response;
        }

        // Check narration
        if((taxPaymentInfo.getNarration() == null) || (taxPaymentInfo.getNarration().trim().length() > 210)) {
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
            return response;
        }

        // Check token key
        regex = "[0-9a-zA-Z]{1,64}$";
        if(!this.validateCharacter(taxPaymentInfo.getEwalletToken(), regex)) {
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
            return response;
        }

        // Check benefit account
        regex = "[0-9]{1,19}$";
        if(!this.validateCharacter(taxPaymentInfo.getTaxReceipt().getBenefitAccountNumber(), regex)) {
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
            return response;
        }

        // Check local treasury code
        regex = "[0-9]{1,8}$";
        if(!this.validateCharacter(taxPaymentInfo.getTaxReceipt().getLocalTreasuryCode(), regex)) {
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
            return response;
        }

        // Check name of payer
        regex = "[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{1,255}$";
        if(!this.validateCharacter(taxPaymentInfo.getTaxReceipt().getTaxPayerName(), regex)
            && !this.validateCharacter(taxPaymentInfo.getTaxReceipt().getTaxPayerName2(), regex)) {
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
            return response;
        }

        // return
        response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
        response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
        return response;
    }

    private boolean validateCharacter(String data, String regex) {
        if((data == null) || (data.trim().equals("")))
            return false;
        if((regex == null) || (regex.trim().equals("")))
            return false;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(data);
        return m.matches();
    }

}
