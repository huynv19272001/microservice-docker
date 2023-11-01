/**
 * @author Trung.Nguyen
 * @date 06-Jun-2022
 * */
package com.lpb.esb.etax.inquiry.process;

import com.lpb.esb.etax.inquiry.model.data.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lpb.esb.etax.inquiry.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.inquiry.model.config.TaxInfoConstants;
import com.lpb.esb.etax.inquiry.model.response.DefaultResponse;
import com.lpb.esb.service.common.model.response.ResponseModel;

@Component
@Log4j2
public class TaxPayerInfoProcess {

	@Autowired
	private AccountInfoProcess accountInfoProcess;
	@Autowired
	private CustomerInfoProcess customerInfoProcess;
	@Autowired
	private CardInfoProcess cardInfoProcess;

	public DefaultResponse matchTaxPayerInfoByAcc(TaxPayerInfo taxPayerInfo) {
		DefaultResponse response = new DefaultResponse();
        response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
        response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
        try {
        	/**
        	 * find by account number
        	 * */
        	ResponseModel<LpbAccountDetail> accountRes = accountInfoProcess.inquiryAccountDetail(taxPayerInfo.getRequireNumber());
        	if (!EtaxErrorConfig.ERROR_CODE_00.equals(accountRes.getResCode().getErrorCode())) {
        		response.setResponseCode(accountRes.getResCode().getErrorCode());
        		response.setResponseDesc(accountRes.getResCode().getErrorDesc());
        		if ("01".equals(accountRes.getResCode().getErrorCode())) {
        			response.setResponseCode(EtaxErrorConfig.ERROR_CODE_056);
                    response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_056);
        		}
				return response;
        	}
        	LpbAccountDetail account = (LpbAccountDetail) accountRes.getData();
        	String cifNo = account.getCifNo();
        	String identificationNumber = taxPayerInfo.getIdentificationNumber();
        	/**
        	 * find customer by CIF and identification number (unique value)
        	 * */
        	ResponseModel<LpbCustomer> customerRes = customerInfoProcess.inquiryCustomer(cifNo, "");
        	if (!EtaxErrorConfig.ERROR_CODE_00.equals(customerRes.getResCode().getErrorCode())) {
        		response.setResponseCode(customerRes.getResCode().getErrorCode());
        		response.setResponseDesc(customerRes.getResCode().getErrorDesc());
        		return response;
        	}
        	LpbCustomer customer = (LpbCustomer)customerRes.getData();
        	// match unique type
        	if (TaxInfoConstants.CORE_IDENTIFICATION.equalsIgnoreCase(customer.getUniqueIdName())
                    && (!TaxInfoConstants.IDENTIFICATION.equalsIgnoreCase(taxPayerInfo.getIdentificationType())
        			    || !identificationNumber.equalsIgnoreCase(customer.getUniqueIdValue()))) {
                log.info("matchTaxPayerInfoByAcc_debug: "+ customer.getUniqueIdName() +", "+ taxPayerInfo.getIdentificationType() +", "+ customer.getUniqueIdValue());
                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_052);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_052);
                return response;
        	} else if (TaxInfoConstants.CORE_CITIZEN_IDENTITY.equalsIgnoreCase(customer.getUniqueIdName())
                    && (!TaxInfoConstants.CITIZEN_IDENTITY.equalsIgnoreCase(taxPayerInfo.getIdentificationType())
        			    || !identificationNumber.equalsIgnoreCase(customer.getUniqueIdValue()))) {
                log.info("matchTaxPayerInfoByAcc_debug: "+ customer.getUniqueIdName() +", "+ taxPayerInfo.getIdentificationType() +", "+ customer.getUniqueIdValue());
        		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_053);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_053);
                return response;
        	} else if (TaxInfoConstants.CORE_PASSPORT.equalsIgnoreCase(customer.getUniqueIdName())
                    && (!TaxInfoConstants.PASSPORT.equalsIgnoreCase(taxPayerInfo.getIdentificationType())
        			    || !identificationNumber.equalsIgnoreCase(customer.getUniqueIdValue()))) {
                log.info("matchTaxPayerInfoByAcc_debug: "+ customer.getUniqueIdName() +", "+ taxPayerInfo.getIdentificationType() +", "+ customer.getUniqueIdValue());
        		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_054);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_054);
                return response;
        	}
        	// match fullname
//        	if (!customer.getFullName().trim().equalsIgnoreCase(taxPayerInfo.getPayerFullName().trim())) {
//                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_051);
//                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_051);
//                return response;
//            }
        	// match mobile phone number
        	if (!customer.getCustomerMobile().trim().equals(taxPayerInfo.getPhoneNumber().trim())) {
                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_058);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_058);
                return response;
            }
            // return
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
            return response;
        } catch(Exception ex) {
        	// todo
        }
		return response;
	}

    public DefaultResponse matchTaxPayerInfoByCard(TaxPayerInfo taxPayerInfo) {
        DefaultResponse response = new DefaultResponse();
        response.setResponseCode(EtaxErrorConfig.ERROR_CODE_96);
        response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_96);
        try {
            /**
             * find by card number
             * */
            ResponseModel<LpbCard> cardRes = cardInfoProcess.inquiryCardInfo(taxPayerInfo.getRequireNumber());
            if (!EtaxErrorConfig.ERROR_CODE_00.equals(cardRes.getResCode().getErrorCode())) {
                response.setResponseCode(cardRes.getResCode().getErrorCode());
                response.setResponseDesc(cardRes.getResCode().getErrorDesc());
                if ("01".equals(cardRes.getResCode().getErrorCode())) {
                    response.setResponseCode(EtaxErrorConfig.ERROR_CODE_057);
                    response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_057);
                }
                return response;
            }
            LpbCard card = (LpbCard)cardRes.getData();
            String cifNo = card.getCustomerNumber();
            String identificationNumber = taxPayerInfo.getIdentificationNumber();
            String identificationType = taxPayerInfo.getIdentificationType();
            // todo
            ResponseModel<LpbDebitCard> debitCardRes = cardInfoProcess.inquiryDebitCard(taxPayerInfo.getRequireNumber());
        	if (!EtaxErrorConfig.ERROR_CODE_00.equals(debitCardRes.getResCode().getErrorCode())) {
        		response.setResponseCode(debitCardRes.getResCode().getErrorCode());
        		response.setResponseDesc(debitCardRes.getResCode().getErrorDesc());
				return response;
        	}
        	LpbDebitCard debitCard = (LpbDebitCard)debitCardRes.getData();
            /**
             * find customer by CIF and identification number (unique value)
             * */
            ResponseModel<LpbCustomer> customerRes = customerInfoProcess.inquiryCustomer(cifNo, "");
            if (!EtaxErrorConfig.ERROR_CODE_00.equals(customerRes.getResCode().getErrorCode())) {
                response.setResponseCode(customerRes.getResCode().getErrorCode());
                response.setResponseDesc(customerRes.getResCode().getErrorDesc());
                return response;
            }
            LpbCustomer customer = (LpbCustomer)customerRes.getData();
            // match issue date of the debit card
            if (!debitCard.getIssueDate().equalsIgnoreCase(taxPayerInfo.getIssueDate().trim())) {
                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_061);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_061);
                return response;
            }
            // match unique type
            if (TaxInfoConstants.CORE_IDENTIFICATION.equalsIgnoreCase(customer.getUniqueIdName())
                && (!TaxInfoConstants.IDENTIFICATION.equalsIgnoreCase(identificationType)
                    || !identificationNumber.equalsIgnoreCase(customer.getUniqueIdValue()))) {
                log.info("matchTaxPayerInfoByCard_debug: "+ customer.getUniqueIdName() +", "+ taxPayerInfo.getIdentificationType() +", "+ customer.getUniqueIdValue());
                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_052);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_052);
                return response;
            } else if (TaxInfoConstants.CORE_CITIZEN_IDENTITY.equalsIgnoreCase(customer.getUniqueIdName())
                    && (!TaxInfoConstants.CITIZEN_IDENTITY.equalsIgnoreCase(identificationType)
                        || !identificationNumber.equalsIgnoreCase(customer.getUniqueIdValue()))) {
                log.info("matchTaxPayerInfoByCard_debug: "+ customer.getUniqueIdName() +", "+ taxPayerInfo.getIdentificationType() +", "+ customer.getUniqueIdValue());
                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_053);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_053);
                return response;
            } else if (TaxInfoConstants.CORE_PASSPORT.equalsIgnoreCase(customer.getUniqueIdName())
                    && (!TaxInfoConstants.PASSPORT.equalsIgnoreCase(identificationType)
                        || !identificationNumber.equalsIgnoreCase(customer.getUniqueIdValue()))) {
                log.info("matchTaxPayerInfoByCard_debug: "+ customer.getUniqueIdName() +", "+ taxPayerInfo.getIdentificationType() +", "+ customer.getUniqueIdValue());
                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_054);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_054);
                return response;
            }
            // match fullname
//        	if (!customer.getFullName().trim().equalsIgnoreCase(taxPayerInfo.getPayerFullName().trim())) {
//                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_051);
//                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_051);
//                return response;
//            }
            // match mobile phone number
            if (!customer.getCustomerMobile().trim().equals(taxPayerInfo.getPhoneNumber().trim())) {
                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_058);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_058);
                return response;
            }
            // return
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
            return response;
        } catch(Exception ex) {
            // todo
        }
        return response;
    }

}
