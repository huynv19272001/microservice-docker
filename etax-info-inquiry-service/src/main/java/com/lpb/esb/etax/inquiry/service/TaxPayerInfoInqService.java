/**
 * @author Trung.Nguyen
 * @date 19-Apr-2022
 * */
package com.lpb.esb.etax.inquiry.service;

import com.lpb.esb.etax.inquiry.model.data.LpbCustomer;
import com.lpb.esb.service.common.model.response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lpb.esb.etax.inquiry.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.inquiry.model.config.GeneralTransConfig;
import com.lpb.esb.etax.inquiry.model.config.TaxInfoConstants;
import com.lpb.esb.etax.inquiry.model.data.OtpInfo;
import com.lpb.esb.etax.inquiry.model.data.TaxPayerInfo;
import com.lpb.esb.etax.inquiry.model.entity.EsbEtaxToken;
import com.lpb.esb.etax.inquiry.model.response.AssignAcc2PayerResponse;
import com.lpb.esb.etax.inquiry.model.response.DefaultResponse;
import com.lpb.esb.etax.inquiry.process.CustomerInfoProcess;
import com.lpb.esb.etax.inquiry.process.OtpProcess;
import com.lpb.esb.etax.inquiry.process.TaxPayerInfoProcess;
import com.lpb.esb.etax.inquiry.repository.EsbEtaxTokenRepository;
import com.lpb.esb.etax.inquiry.util.FlexCubsUtils;
import com.lpb.esb.etax.inquiry.util.RequestUtils;

@Service
public class TaxPayerInfoInqService {

	@Autowired
	private TaxPayerInfoProcess taxPayerInfoProcess;
    @Autowired
    private CustomerInfoProcess customerInfoProcess;
	@Autowired
	private OtpProcess otpProcess;
	@Autowired
    private EsbEtaxTokenRepository esbEtaxTokenRepository;
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private FlexCubsUtils flexMsgUtils;

	public DefaultResponse verifyPayerInfo(TaxPayerInfo taxPayerInfo) {
		// Check request
        DefaultResponse response = requestUtils.validateTaxPayerInfo(taxPayerInfo);
		if (!EtaxErrorConfig.ERROR_CODE_00.equals(response.getResponseCode()))
			return response;

		// Process
		if (TaxInfoConstants.BANK_ACCOUNT.equalsIgnoreCase(taxPayerInfo.getRequireType())) {
			response = taxPayerInfoProcess.matchTaxPayerInfoByAcc(taxPayerInfo);
		} else if (TaxInfoConstants.BANK_CARD.equalsIgnoreCase(taxPayerInfo.getRequireType())) {
			response = taxPayerInfoProcess.matchTaxPayerInfoByCard(taxPayerInfo);
		}

		// return
		return response;
	}

	public AssignAcc2PayerResponse assignAccount2TaxPayer(TaxPayerInfo taxPayerInfo) {
		AssignAcc2PayerResponse response = new AssignAcc2PayerResponse();
		DefaultResponse result = null;
		String msgId = "";
		String originalType = "";
		String originalClass = "";
		String originalData = "";
		String originalSystem = "";
		String currentDate = "";

		// Check request
		result = requestUtils.validateTaxPayerInfo(taxPayerInfo);
		if (!EtaxErrorConfig.ERROR_CODE_00.equals(result.getResponseCode())) {
			response.setResponseCode(result.getResponseCode());
			response.setResponseDesc(result.getResponseDesc());
			return response;
		}

		// Check action code: Open & Close
		if (!TaxInfoConstants.LINK_ETAX_ACCOUNT.equalsIgnoreCase(taxPayerInfo.getActionCode()) &&
				!TaxInfoConstants.UNLINK_ETAX_ACCOUNT.equalsIgnoreCase(taxPayerInfo.getActionCode()))
			return response;

		// Process
		if (TaxInfoConstants.LINK_ETAX_ACCOUNT.equalsIgnoreCase(taxPayerInfo.getActionCode())) {
			// Check existed token
			EsbEtaxToken esbEtaxToken = esbEtaxTokenRepository.findOriginalData(taxPayerInfo.getRequireNumber());
			if(esbEtaxToken != null) {
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_125);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_125);
				return response;
			}
			if (TaxInfoConstants.BANK_ACCOUNT.equalsIgnoreCase(taxPayerInfo.getRequireType())) {
				result = taxPayerInfoProcess.matchTaxPayerInfoByAcc(taxPayerInfo);
				if (!EtaxErrorConfig.ERROR_CODE_00.equals(result.getResponseCode())) {
					response.setResponseCode(result.getResponseCode());
					response.setResponseDesc(result.getResponseDesc());
					return response;
				}
				// Setting variable
				originalType = TaxInfoConstants.BANK_ACCOUNT;
				originalClass = "CURRENT";
				originalData = taxPayerInfo.getRequireNumber();
				originalSystem = "CBS";
			} else if (TaxInfoConstants.BANK_CARD.equalsIgnoreCase(taxPayerInfo.getRequireType())) {
                result = taxPayerInfoProcess.matchTaxPayerInfoByCard(taxPayerInfo);
                if (!EtaxErrorConfig.ERROR_CODE_00.equals(result.getResponseCode())) {
                    response.setResponseCode(result.getResponseCode());
                    response.setResponseDesc(result.getResponseDesc());
                    return response;
                }
				// Setting variable
				originalType = TaxInfoConstants.BANK_CARD;
				originalClass = "LOCAL_DEBIT";
				originalData = taxPayerInfo.getRequireNumber();
				originalSystem = "CMS";
			}
			/**
			 * Save into ESB_ETAX_TOKEN when linking account/card
			 * */
			msgId = flexMsgUtils.getMsgIdByDate();
			currentDate = flexMsgUtils.getCurrentDateTime("dd-MMM-yyyy");
			try {
				esbEtaxToken = EsbEtaxToken.builder()
									.linkReqId(msgId)
//									.tokenData("")
									.originalType(originalType)
									.originalClass(originalClass)
									.originalData(originalData)
									.originalSystem(originalSystem)
									.taxCode(taxPayerInfo.getTaxCode())
									.fullName(taxPayerInfo.getPayerFullName())
									.identificationType(taxPayerInfo.getIdentificationType())
									.identificationNumber(taxPayerInfo.getIdentificationNumber())
									.createdDate(currentDate)
//									.modifiedDate(currentDate)
									.status("I").build();
				esbEtaxTokenRepository.save(esbEtaxToken);
			} catch(Exception ex) {
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_99);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_99);
				return response;
			}
		} else if (TaxInfoConstants.UNLINK_ETAX_ACCOUNT.equalsIgnoreCase(taxPayerInfo.getActionCode())) {
			EsbEtaxToken esbEtaxToken = esbEtaxTokenRepository.findTokenData(taxPayerInfo.getEwalletToken());
			if(esbEtaxToken == null) {
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_132);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_132);
				return response;
			}
            /**
             * find customer by identification number (unique value)
             * */
            String uniqueValue = esbEtaxToken.getIdentificationNumber();
            ResponseModel<LpbCustomer> customerRes = customerInfoProcess.inquiryCustomer("", uniqueValue);
            if (!EtaxErrorConfig.ERROR_CODE_00.equals(customerRes.getResCode().getErrorCode())) {
                response.setResponseCode(customerRes.getResCode().getErrorCode());
                response.setResponseDesc(customerRes.getResCode().getErrorDesc());
                return response;
            }
            LpbCustomer customer = (LpbCustomer)customerRes.getData();
            taxPayerInfo.setPhoneNumber(customer.getCustomerMobile());
			/**
			 * Update into ESB_ETAX_TOKEN when unlinking account/card
			 * */
			msgId = flexMsgUtils.getMsgIdByDate();
			currentDate = flexMsgUtils.getCurrentDateTime("dd-MMM-yyyy");
			try {
				esbEtaxToken.setUnlinkReqId(msgId);
                esbEtaxToken.setModifiedDate(currentDate);
				esbEtaxTokenRepository.save(esbEtaxToken);
			} catch(Exception ex) {
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_99);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_99);
				return response;
			}
		}
		/**
		 * Generate OTP and send OTP to mobile SMS
		 * */
		OtpInfo otpInfo = OtpInfo.builder()
							.userId(GeneralTransConfig.ETAX_USERID)
							.appMsgId(msgId)
							.mobileNo(taxPayerInfo.getPhoneNumber()).build();
        result = otpProcess.createOtp(otpInfo);
        if (EtaxErrorConfig.ERROR_CODE_00.equals(result.getResponseCode())) {
        	response.setAppMsgId(msgId);
        	response.setVerificationType(GeneralTransConfig.VERIFICATION_SMS_OTP);
        	response.setSmartOTPUnlockCode("");
        }
        response.setResponseCode(result.getResponseCode());
		response.setResponseDesc(result.getResponseDesc());

		// return
		return response;
	}

}
