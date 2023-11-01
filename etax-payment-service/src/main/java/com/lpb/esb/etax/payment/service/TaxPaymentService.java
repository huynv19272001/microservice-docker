package com.lpb.esb.etax.payment.service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.lpb.esb.etax.payment.model.data.*;
import com.lpb.esb.etax.payment.model.entity.*;
import com.lpb.esb.etax.payment.model.response.DefaultResponse;
import com.lpb.esb.etax.payment.process.CardInfoProcess;
import com.lpb.esb.etax.payment.process.CustomerInfoProcess;
import com.lpb.esb.etax.payment.util.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.etax.payment.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.payment.model.config.GeneralTransConfig;
import com.lpb.esb.etax.payment.model.config.UploadTransConfig;
import com.lpb.esb.etax.payment.model.request.EReceiptRequest;
import com.lpb.esb.etax.payment.model.response.ConfrmTransResponse;
import com.lpb.esb.etax.payment.model.response.EReceiptResponse;
import com.lpb.esb.etax.payment.model.response.InitTaxPaymentResponse;
import com.lpb.esb.etax.payment.process.PostAccountProcess;
import com.lpb.esb.etax.payment.process.TaxPaymentProcess;
import com.lpb.esb.etax.payment.repository.EsbEtaxTokenRepository;
import com.lpb.esb.etax.payment.repository.EsbServiceRepository;
import com.lpb.esb.etax.payment.repository.EtaxPaymentInfoRepository;
import com.lpb.esb.etax.payment.repository.EtaxReceiptInfoRepository;
import com.lpb.esb.etax.payment.repository.TreasuryAccountRepository;
import com.lpb.esb.etax.payment.repository.TreasuryCitadCodeRepository;
import com.lpb.esb.etax.payment.tokenization.TokenVault;
import com.lpb.esb.service.common.model.response.ResponseModel;

@Service
@Log4j2
public class TaxPaymentService {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private TaxPaymentProcess taxPaymentProcess;
	@Autowired
	private PostAccountProcess postAccountProcess;
    @Autowired
    private CardInfoProcess cardInfoProcess;
    @Autowired
    private CustomerInfoProcess customerInfoProcess;
	@Autowired
	private FlexCubsUtils flexMsgUtils;
    @Autowired
    private RequestUtils requestUtils;
	@Autowired
    private DocxTemplateUtils docxTemplateUtils;
    @Autowired
    private RestClientUtils restClientUtils;
    @Autowired
    private LogicUtils logicUtils;
	@Autowired
	private EsbServiceRepository esbServiceRepository;
	@Autowired
	private TreasuryAccountRepository treasuryAccountRepository;
	@Autowired
	private TreasuryCitadCodeRepository treasuryCitadCodeRepository;
	@Autowired
	private EtaxPaymentInfoRepository etaxPaymentInfoRepository;
	@Autowired
	private EtaxReceiptInfoRepository etaxReceiptInfoRepository;
	@Autowired
	private EsbEtaxTokenRepository esbEtaxTokenRepository;
	@Autowired
	private TokenVault tokenVault;

	public InitTaxPaymentResponse initTaxPayment(TaxPaymentInfo taxPaymentInfo) {
		InitTaxPaymentResponse response = new InitTaxPaymentResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
		boolean citadFlag = false;
		try {
            DefaultResponse result = requestUtils.validateTaxPaymentInfo(taxPaymentInfo);
            if (!EtaxErrorConfig.ERROR_CODE_00.equals(result.getResponseCode())) {
                return response;
            }

			// Declare
			String etaxRefNo = taxPaymentInfo.getReferenceNumber();
			String etaxTotalAmount = taxPaymentInfo.getAmount();
			String etaxNarration = taxPaymentInfo.getNarration().trim();
			String paymentMethod = taxPaymentInfo.getPaymentMethod().toUpperCase().trim();
			String debitAccountNumber = taxPaymentInfo.getDebitAccountNumber().trim();		// The input value is either an account number or a card number
			String tokenData = taxPaymentInfo.getEwalletToken().trim();

			String etaxLocalTreasuryCode = taxPaymentInfo.getTaxReceipt().getLocalTreasuryCode().trim();

			TaxReceipt taxReceipt = taxPaymentInfo.getTaxReceipt();
			String taxFees = objectMapper.writeValueAsString(taxReceipt.getTaxFees());
			String taxReceiptDetails = objectMapper.writeValueAsString(taxReceipt.getTaxReceiptDetails());

			/**
			 * Write the tax payment info and the tax receipt into database
			 * */
			// etax payment info
			EtaxPaymentInfo etaxPaymentInfo = EtaxPaymentInfo.builder()
												.referenceNumber(etaxRefNo)
												.partnerCode(taxPaymentInfo.getPartnerCode())
												.amount(taxPaymentInfo.getAmount())
												.deliveryFee(taxPaymentInfo.getDeliveryFee())
												.language(taxPaymentInfo.getLanguage())
												.currency(taxPaymentInfo.getCurrency())
												.narration(taxPaymentInfo.getNarration())
												.transTime(taxPaymentInfo.getTransTime())
												.bankCode(taxPaymentInfo.getBankCode())
												.paymentMethod(taxPaymentInfo.getPaymentMethod())
												.debitAccountNumber(taxPaymentInfo.getDebitAccountNumber())
												.ewalletToken(taxPaymentInfo.getEwalletToken())
												.transId("")
												.transProgress("")
												.transStatus("").build();
			etaxPaymentInfoRepository.save(etaxPaymentInfo);
			// etax receipt info
			EtaxReceiptInfo etaxReceiptInfo = EtaxReceiptInfo.builder()
												.referenceNumber(etaxRefNo)
												.transId("")
												.serviceCode(taxReceipt.getServiceCode())
												.taxReceiptCode(taxReceipt.getTaxReceiptCode())
												.benefitAccountNumber(taxReceipt.getBenefitAccountNumber())
												.channelCode(taxReceipt.getChannelCode())
												.taxCategoryCode(taxReceipt.getTaxCategoryCode())
												.taxCategoryName(taxReceipt.getTaxCategoryName())
												.taxCode(taxReceipt.getTaxCode())
												.taxPayerName(taxReceipt.getTaxPayerName())
												.taxPayerIdentification(taxReceipt.getTaxPayerIdentification())
												.taxPayerAddress(taxReceipt.getTaxPayerAddress())
												.taxPayerDistrict(taxReceipt.getTaxPayerDistrict())
												.taxPayerProvince(taxReceipt.getTaxPayerProvince())
                                                .taxCode2(taxReceipt.getTaxCode2())
                                                .taxPayerName2(taxReceipt.getTaxPayerName2())
                                                .taxPayerAddress2(taxReceipt.getTaxPayerAddress2())
                                                .taxPayerDistrict2(taxReceipt.getTaxPayerDistrict2())
                                                .taxPayerProvince2(taxReceipt.getTaxPayerProvince2())
												.taxInstitutionCode(taxReceipt.getTaxInstitutionCode())
												.taxInstitutionName(taxReceipt.getTaxInstitutionName())
												.localTreasuryCode(taxReceipt.getLocalTreasuryCode())
												.taxDate(taxReceipt.getTaxDate())
												.taxNumber(taxReceipt.getTaxNumber())
												.taxFees(taxFees)
												.taxReceiptDetails(taxReceiptDetails).build();
			etaxReceiptInfoRepository.save(etaxReceiptInfo);

			/**
			 * Find debit account/card by token
			 * */
			EsbEtaxToken esbEtaxToken = esbEtaxTokenRepository.findTokenData(tokenData);
			if(esbEtaxToken == null) {
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_118);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_118);
				return response;
			} else {
				if((esbEtaxToken.getOriginalData() == null) || (esbEtaxToken.getOriginalType() == null)) {
					response.setResponseCode(EtaxErrorConfig.ERROR_CODE_118);
					response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_118);
					return response;
				}
				// setting method payment and debit account/card
				paymentMethod = esbEtaxToken.getOriginalType();
				debitAccountNumber = esbEtaxToken.getOriginalData();
			}

			/**
			 * Inquiry debit account info
			 * */
			LpbAccountDetail drAccount = null;
			if (UploadTransConfig.BANK_ACCOUNT.equals(paymentMethod)) {
				ResponseModel<LpbAccountDetail> accountInqRes = postAccountProcess.inquiryAccountDetail(debitAccountNumber);
				if (!EtaxErrorConfig.ERROR_CODE_00.equals(accountInqRes.getResCode().getErrorCode())) {
					response.setResponseCode(EtaxErrorConfig.ERROR_CODE_01);
					response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_01);
	                return response;
	            }
				drAccount = (LpbAccountDetail) accountInqRes.getData();
			} else if (UploadTransConfig.BANK_CARD.equals(paymentMethod)) {
                ResponseModel<LpbCard> cardInqRes = cardInfoProcess.inquiryCardInfo(debitAccountNumber);
                if (!EtaxErrorConfig.ERROR_CODE_00.equals(cardInqRes.getResCode().getErrorCode())) {
                    response.setResponseCode(cardInqRes.getResCode().getErrorCode());
                    response.setResponseDesc(cardInqRes.getResCode().getErrorDesc());
                    return response;
                }
                // todo
                LpbCard card = (LpbCard)cardInqRes.getData();
                ResponseModel<LpbAccountDetail> accountInqRes = postAccountProcess.inquiryAccountDetail(card.getAccountNumber());
                if (!EtaxErrorConfig.ERROR_CODE_00.equals(accountInqRes.getResCode().getErrorCode())) {
                    response.setResponseCode(EtaxErrorConfig.ERROR_CODE_01);
                    response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_01);
                    return response;
                }
                drAccount = (LpbAccountDetail) accountInqRes.getData();
			} else {
				return response;
			}

            /**
             * Inquiry debit customer info
             * */
            OtpInfo otpInfo = new OtpInfo();
            if ((drAccount != null)) {
                ResponseModel<LpbCustomer> customerRes = customerInfoProcess.inquiryCustomer(drAccount.getCifNo(), "");
                if (!EtaxErrorConfig.ERROR_CODE_00.equals(customerRes.getResCode().getErrorCode())) {
                    response.setResponseCode(customerRes.getResCode().getErrorCode());
                    response.setResponseDesc(customerRes.getResCode().getErrorDesc());
                    return response;
                }
                LpbCustomer customer = (LpbCustomer)customerRes.getData();
                // Setting the customer's phone number, which received otp
                otpInfo.setMobileNo(customer.getCustomerMobile());
            }

            /**
             * Check available balance of debit account
             * */
            if ((drAccount != null)) {
                double avlBal = Double.parseDouble(drAccount.getOpeningBalance() != null ? drAccount.getOpeningBalance() : "0");
                double minBal = Double.parseDouble(drAccount.getMinBalance() != null ? drAccount.getMinBalance() : "0");
                double paymentAmt = Double.parseDouble(etaxTotalAmount != null ? etaxTotalAmount : "0");
                if ((avlBal - minBal) < paymentAmt) {
                    response.setResponseCode(EtaxErrorConfig.ERROR_CODE_123);
                    response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_123);
                    return response;
                }
            }

			/**
			 * Get service configuration
			 * */
			EsbService esbService = esbServiceRepository.findByServiceId(UploadTransConfig.ETAX_CITAD_TRANSFER_SERVICEID);
			/**
			 * Inquiry credit account info
			 * */
			TreasuryAccount treasuryAccount = treasuryAccountRepository.findAccountByTreasury4Code(etaxLocalTreasuryCode);
			if(treasuryAccount == null) {
                citadFlag = true;
                /**
                 * Rebuild transaction narration for Citad
                 * */
                String transTime = taxPaymentInfo.getTransTime();                       // ND2
                String taxCode = taxReceipt.getTaxCode();                               // ND3
                String locationCode = "";                                               // ND4
                String benefitAccountNumber = taxReceipt.getBenefitAccountNumber();     // ND5
                String taxInstitutionCode = taxReceipt.getTaxInstitutionCode();         // ND6
                String taxCategoryCode = taxReceipt.getTaxCategoryCode();               // ND7
                String taxNumber = taxReceipt.getTaxNumber();                           // ND8
                String taxDate = taxReceipt.getTaxDate();                               // ND9
                String taxDesc = "";
                for(TaxFee taxFee : taxReceipt.getTaxFees()) {
                    String[] feeCategory = new String[0];
                    String[] feeCategoryCode = new String[0];
                    String arrDesc = "+[STB:%s+NTB:%s+SK:%s-SM:%s(C:%s-TM:%s-KT:%s-ST:%s-GChu:%s)]";
                    String SK = "", SM = "", C = "", TM = "";
                    if ((taxFee.getFeeCategory() != null) && taxFee.getFeeCategory().contains("+")) {
                        feeCategory = taxFee.getFeeCategory().split("\\+");
                        if (feeCategory.length > 2) {
                            SK = feeCategory[2];
                        }
                        if (feeCategory.length > 3) {
                            SM = feeCategory[3];
                        }
                    }
                    if ((taxFee.getFeeCategoryCode() != null) && taxFee.getFeeCategoryCode().contains("+")) {
                        feeCategoryCode = taxFee.getFeeCategoryCode().split("\\+");
                        if (feeCategoryCode.length > 1) {
                            C = feeCategoryCode[1];
                        }
                        if (feeCategoryCode.length > 2) {
                            TM = feeCategoryCode[2];
                        }
                        if (feeCategoryCode.length > 3) {
                            locationCode = feeCategoryCode[3];
                        }
                    }

                    taxDesc = taxDesc + String.format(arrDesc, taxNumber, taxDate, SK, SM, C, TM, flexMsgUtils.getTransDate("MM/yyyy"), taxFee.getFeeAmount(), ((taxReceipt.getTaxPayerName2() == null) || taxReceipt.getTaxPayerName2().equals("")) ? "" : "NT");
                }
                etaxNarration = "NTDTCN+KB:"+ etaxLocalTreasuryCode
                              + "+NgayNT:"+ flexMsgUtils.getTransDate("ddMMyyyy")
                              + "+MST:"+ taxCode
                              + "+DBHC:"+ locationCode
                              + "+TKNS:"+ benefitAccountNumber
                              + "+CQT:"+ taxInstitutionCode
                              + "+LThue:"+ taxCategoryCode
                              + taxDesc;

//                etaxNarration = "NTDTCN+KB:"+ etaxLocalTreasuryCode
//                              + "+NgayNT:"+ transTime
//                              + "+MST:"+ taxCode
//                              + "+DBHC:"+ locationCode
//                              + "+TKNS:"+ benefitAccountNumber
//                              + "+CQT:"+ taxInstitutionCode
//                              + "+LThue:"+ taxCategoryCode
//                              + "+STB:"+ taxNumber
//                              + "+NTB:"+ taxDate
//                              + "+SK:ND10-SM:ND11 (C:ND12-TM:ND13-KT:ND14-ST:"+ etaxTotalAmount +"-GChu:ND16)";
                /**
                 * Check cut-off time
                 * */
                String cotResult = flexMsgUtils.checkCutOffTime(UploadTransConfig.ETAX_CITAD_TRANSFER_SERVICEID);
                if ("Y".equalsIgnoreCase(cotResult)) {
                    response.setResponseCode(EtaxErrorConfig.ERROR_CODE_142);
                    response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_142);
                    return response;
                } else if ("N".equalsIgnoreCase(cotResult)) {
                    // todo
                } else {
                    // error
                    response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
                    response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
                    return response;
                }
            }

			// Get transaction id from sequence
			String transId = citadFlag? String.valueOf(treasuryCitadCodeRepository.getTransId()) : String.valueOf(treasuryAccountRepository.getTransId());
			etaxPaymentInfo.setTransId(transId);
			etaxReceiptInfo.setTransId(transId);

			// Set transaction info
			TransactionInfo transactionInfo = TransactionInfo.builder()
													.transId(transId)
													.trnDesc(etaxNarration)
													.customerNo(drAccount.getCifNo())
													.userId(UploadTransConfig.ETAX_USERID).build();
			// Set service info
			ServiceInfo serviceInfo = ServiceInfo.builder()
											.serviceId(citadFlag? UploadTransConfig.ETAX_CITAD_TRANSFER_SERVICEID : UploadTransConfig.ETAX_INTER_TRANSFER_SERVICEID)
											.productCode(UploadTransConfig.ETAX_PRODUCT_CODE)
											.merchantId(etaxLocalTreasuryCode).build();
			// Set partner info
			PartnerInfo partnerInfo = PartnerInfo.builder()
											.txnCode(etaxRefNo)
											.txnDatetime(flexMsgUtils.getTransDate("yyyyMMdd"))
											.chanel(UploadTransConfig.ETAX_CHANNEL)
											.txnRefNo(etaxRefNo).build();
			/**
			 * Set post info: debit account & credit account
			 * */
			List<PostAccountInfo> postAccounts = new ArrayList<PostAccountInfo>();
			// Debit account
			PostAccountInfo debitAccount = PostAccountInfo.builder()
												.acNo(drAccount.getAccountNo())
												.branchCode(drAccount.getBranchCode())
												.customerNo(drAccount.getCifNo())
												.ccy(UploadTransConfig.CURRENCY_VND)
												.lcyAmount(etaxTotalAmount)
												.drcrInd(UploadTransConfig.DR_INDICATOR)
												.cardNo("")
												.serviceType(UploadTransConfig.SERVICE_TYPE_DEPOSIT).build();
			// Credit account
			PostAccountInfo creditAccount = PostAccountInfo.builder()
												.acNo(citadFlag? esbService.getCrHoldAcNo() : treasuryAccount.getAccountNumber())
												.branchCode(citadFlag? esbService.getCrHoldBrn() : treasuryAccount.getBranchCode())
												.customerNo(citadFlag? esbService.getCrHoldAcNo().substring(0,8) : treasuryAccount.getAccountNumber().substring(0,8))
												.ccy(UploadTransConfig.CURRENCY_VND)
												.lcyAmount(etaxTotalAmount)
												.drcrInd(UploadTransConfig.CR_INDICATOR)
												.cardNo("")
												.serviceType(UploadTransConfig.SERVICE_TYPE_DEPOSIT).build();

			// Appended to the account list
			postAccounts.add(debitAccount);
			postAccounts.add(creditAccount);
			// Set transaction request
			InitTransaction initTransaction = InitTransaction.builder()
													.transactionInfo(transactionInfo)
													.serviceInfo(serviceInfo)
													.partnerInfo(partnerInfo)
													.postInfo(postAccounts).build();
			/**
             * Initialize transaction of tax payment
             * */
			response = taxPaymentProcess.initTaxPayment(initTransaction, otpInfo);

			/**
			 * Update etax payment and etax receipt info
			 * */
			if (EtaxErrorConfig.ERROR_CODE_00.equals(response.getResponseCode())) {
                etaxPaymentInfo.setDebitAccountNumber(drAccount.getAccountNo());
				etaxPaymentInfo.setTransProgress("I");
				etaxPaymentInfo.setTransStatus("S");
			} else {
                etaxPaymentInfo.setDebitAccountNumber(drAccount.getAccountNo());
				etaxPaymentInfo.setTransProgress("I");
				etaxPaymentInfo.setTransStatus("F");
			}
			etaxPaymentInfoRepository.save(etaxPaymentInfo);
			etaxReceiptInfoRepository.save(etaxReceiptInfo);
		} catch (Exception ex) {
            log.error("initTaxPayment, exception: "+ ex);
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_99);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_99);
		}
		return response;
	}

	public ConfrmTransResponse confirmTaxPayment(String transId) {
		ConfrmTransResponse response = new ConfrmTransResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_99);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_99);
        boolean citadFlag = false;
        String drAccountNo = "";
        String drBranchCode = "";
		List<TransAccountInfo> accountList = taxPaymentProcess.getAccount4UploadTransferJrn(transId);
		// If the transaction is not found, immediately return.
		if ((accountList == null) || (accountList.size() == 0)) {
			response.setResponseCode(EtaxErrorConfig.ERROR_CODE_104);
			response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_104);
			return response;
		} else {
            for (TransAccountInfo account : accountList) {
                /**
                 * Check Citad transaction
                 * */
                if (UploadTransConfig.ETAX_CITAD_TRANSFER_NOSTRO.equals(account.getAccNo())
                    && UploadTransConfig.CR_INDICATOR.equals(account.getDrcrInd())) {
                    citadFlag = true;
                } else {
                    drAccountNo = account.getAccNo();
                    drBranchCode = account.getAccBranch();
                }
            }
        }
        /**
         * Get etax payment and etax receipt info
         * */
        EtaxPaymentInfo etaxPaymentInfo = etaxPaymentInfoRepository.findByTransId(transId);
        EtaxReceiptInfo etaxReceiptInfo = etaxReceiptInfoRepository.findByTransId(transId);
        if ((etaxPaymentInfo == null) || (etaxReceiptInfo == null)) {
            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_118);
            response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_118);
            return response;
        }
		/**
		 * Check cut-off time
		 * */
        if (citadFlag) {
            String cotResult = flexMsgUtils.checkCutOffTime(UploadTransConfig.ETAX_CITAD_TRANSFER_SERVICEID);
            if ("Y".equalsIgnoreCase(cotResult)) {
                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_142);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_142);
                return response;
            } else if ("N".equalsIgnoreCase(cotResult)) {
                // Get Citad info
                TreasuryCitadCode treasuryCitadCode = treasuryCitadCodeRepository.findByTreasuryCode(etaxReceiptInfo.getLocalTreasuryCode());
                if (treasuryCitadCode == null) {
                    response.setResponseCode(EtaxErrorConfig.ERROR_CODE_118);
                    response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_118);
                    return response;
                }
                /**
                 * Route Citad transactions by channel
                 * */
                String ibpsChanel = Integer.parseInt(drBranchCode) < 500 ? "IBPS_O_HO" : "IBPS_O_HCM";
                // Setting info
                String senderInstAccount = ibpsChanel +"##"+ treasuryCitadCode.getDirectCode();
                String receiverName = VNCharacterUtils.removeAccent(etaxReceiptInfo.getTaxInstitutionName());
                String receiverAddress = VNCharacterUtils.removeAccent(treasuryCitadCode.getTreasuryName());
                String receiverAccount = etaxReceiptInfo.getBenefitAccountNumber();
                String taxPayerName = etaxReceiptInfo.getTaxPayerName() != null ? etaxReceiptInfo.getTaxPayerName() : etaxReceiptInfo.getTaxPayerName2();
                taxPayerName = VNCharacterUtils.removeAccent(taxPayerName);
                String receiverCode = treasuryCitadCode.getDirectCode() +"##"+ drBranchCode + drAccountNo +"##"+ taxPayerName;
                for (TransAccountInfo account : accountList) {
                    account.setSenderInstAccount(senderInstAccount);
                    account.setReceiverName(receiverName);
                    account.setReceiverAddress(receiverAddress);
                    account.setReceiverAccount(receiverAccount);
                    account.setReceiverCode(receiverCode);
                }
                // Do the uploading Citad transfer transaction
                UploadTransferInfo uploadTransferInfo = taxPaymentProcess.buildUploadTransferReq(transId, accountList);
                response = taxPaymentProcess.uploadTransferCitad(uploadTransferInfo);
                // Update receipt tax info
                etaxReceiptInfo.setBenefitBankName(treasuryCitadCode.getDirectName());
                etaxReceiptInfo.setProvince(treasuryCitadCode.getProvince());
                etaxReceiptInfoRepository.save(etaxReceiptInfo);
            } else {
                response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
                response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
                return response;
            }
        } else {
            // Do the uploading transfer transaction
            UploadTransferInfo uploadTransferInfo = taxPaymentProcess.buildUploadTransferReq(transId, accountList);
            response = taxPaymentProcess.confirmTaxPayment(uploadTransferInfo);
            // Update receipt tax info
            etaxReceiptInfo.setBenefitBankName("NH Buu Dien Lien Viet");
            etaxReceiptInfo.setProvince("");
            etaxReceiptInfoRepository.save(etaxReceiptInfo);
        }
		// Update etax payment info
		if (EtaxErrorConfig.ERROR_CODE_00.equals(response.getResponseCode())) {
			etaxPaymentInfo.setTransProgress("C");
			etaxPaymentInfo.setTransStatus("S");
		} else {
			etaxPaymentInfo.setTransProgress("C");
			etaxPaymentInfo.setTransStatus("F");
		}
		etaxPaymentInfoRepository.save(etaxPaymentInfo);
		return response;
	}

	public ConfrmTransResponse confirmAssignAccount(String requestId) {
		ConfrmTransResponse response = new ConfrmTransResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_99);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_99);
		String tokenData = null;
		try {
			/**
			 * Link account/card
			 * */
			EsbEtaxToken esbEtaxToken = esbEtaxTokenRepository.findNewRequest2Token(requestId);
			if (esbEtaxToken != null) {
				if (GeneralTransConfig.BANK_ACCOUNT.equalsIgnoreCase(esbEtaxToken.getOriginalType())) {
					tokenData = tokenVault.tokenizeAccount(esbEtaxToken.getOriginalData());
				} else if (GeneralTransConfig.BANK_CARD.equalsIgnoreCase(esbEtaxToken.getOriginalType())) {
					tokenData = tokenVault.tokenizeCard(esbEtaxToken.getOriginalData());
				}
				// save into ESB_ETAX_TOKEN
				esbEtaxToken.setTokenData(tokenData);
				esbEtaxToken.setStatus("O");
				esbEtaxTokenRepository.save(esbEtaxToken);
				// set response
				response.setEwalletToken(tokenData);
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
				return response;
			}
			/**
			 * UnLink account/card
			 * */
			esbEtaxToken = esbEtaxTokenRepository.findTokenByUnLinkReq(requestId);
			if (esbEtaxToken != null) {
				// save into ESB_ETAX_TOKEN
				esbEtaxToken.setModifiedDate(flexMsgUtils.getTransDate("dd-MMM-yyyy"));
				esbEtaxToken.setStatus("C");
				esbEtaxTokenRepository.save(esbEtaxToken);
				// set response
				response.setEwalletToken(esbEtaxToken.getTokenData());
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
				return response;
			} else {
				response.setResponseCode(EtaxErrorConfig.ERROR_CODE_104);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_104);
				return response;
			}
		} catch(Exception ex) {
            log.error("confirmAssignAccount, exception: "+ ex);
			response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
			response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
		}
		// return
		return response;
	}

	public EReceiptResponse printEReceipt(String refNo) {
		EReceiptResponse response = new EReceiptResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_99);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_99);
		try {
            EtaxPaymentInfo etaxPaymentInfo = etaxPaymentInfoRepository.findByRefNo(refNo);
            EtaxReceiptInfo etaxReceiptInfo = etaxReceiptInfoRepository.findByRefNo(refNo);
//			EtaxReceiptInfo etaxReceiptInfo = etaxReceiptInfoRepository.findById(refNo).get();
//		    EtaxPaymentInfo etaxPaymentInfo = etaxPaymentInfoRepository.findById(refNo).get();
		    if (etaxPaymentInfo != null && etaxReceiptInfo != null) {
	            response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
	    		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
	        } else {
	        	response.setResponseCode(EtaxErrorConfig.ERROR_CODE_104);
				response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_104);
	        }
		} catch(Exception ex) {
            log.error("printEReceipt, exception: "+ ex);
			response.setResponseCode(EtaxErrorConfig.ERROR_CODE_06);
    		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_06);
		}
		return response;
	}

    public ResponseEntity getEReceipt(String refNo) {
        try {
            EtaxPaymentInfo etaxPaymentInfo = etaxPaymentInfoRepository.findByRefNo(refNo);
            EtaxReceiptInfo etaxReceiptInfo = etaxReceiptInfoRepository.findByRefNo(refNo);
//            EtaxReceiptInfo etaxReceiptInfo = etaxReceiptInfoRepository.findById(refNo).get();
//            EtaxPaymentInfo etaxPaymentInfo = etaxPaymentInfoRepository.findById(refNo).get();
            if (etaxPaymentInfo != null && etaxReceiptInfo != null) {
                String receiptTemp = "etax-payment-service/file/templates/receipt.docx";
                String inputName = "etax-payment-service/file/docx/" + refNo + ".docx";
                String outputDir = "etax-payment-service/file/pdf/";
                docxTemplateUtils.fillTemplateReceipt(etaxReceiptInfo, etaxPaymentInfo, receiptTemp, inputName);
                String fileInputName = refNo + ".docx";
                Resource resource = logicUtils.load("etax-payment-service/file/docx/", fileInputName);
                Resource pdf =  restClientUtils.sendFileConvertBody(resource, outputDir);
                // return
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; attachment; filename=" + URLDecoder.decode(pdf.getFilename(), String.valueOf(StandardCharsets.ISO_8859_1)))
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdf);
            } else {
                return ResponseEntity.badRequest().body("Khong tim thay ma tham chieu");
            }
        } catch(Exception ex) {
            log.error("getEReceipt, exception: "+ ex);
            return ResponseEntity.badRequest().body("Loi khong xac dinh");
        }
    }

}
