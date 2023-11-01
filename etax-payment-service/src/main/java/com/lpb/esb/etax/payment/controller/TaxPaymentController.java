package com.lpb.esb.etax.payment.controller;

import java.util.Base64;
import java.util.UUID;

import com.lpb.esb.etax.payment.util.ShaUtils;
import com.lpb.esb.service.common.utils.CacheUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.etax.payment.config.SimulatorConfig;
import com.lpb.esb.etax.payment.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.payment.model.config.GeneralTransConfig;
import com.lpb.esb.etax.payment.model.data.TaxPaymentInfo;
import com.lpb.esb.etax.payment.model.data.TransConfirmation;
import com.lpb.esb.etax.payment.model.data.TransFeedback;
import com.lpb.esb.etax.payment.model.request.EReceiptRequest;
import com.lpb.esb.etax.payment.model.request.EtaxEncryptedRequest;
import com.lpb.esb.etax.payment.model.response.ConfrmTransResponse;
import com.lpb.esb.etax.payment.model.response.DefaultResponse;
import com.lpb.esb.etax.payment.model.response.EReceiptResponse;
import com.lpb.esb.etax.payment.service.OtpInfoService;
import com.lpb.esb.etax.payment.service.TaxPaymentService;
import com.lpb.esb.etax.payment.util.RequestUtils;
import com.lpb.esb.etax.payment.util.sign.ESignatureAuth;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/api/v1")
@Log4j2
public class TaxPaymentController {

    @Autowired
    private RestTemplate restTemplate;
	@Autowired
    ObjectMapper objectMapper;
	@Autowired
	private TaxPaymentService taxPaymentService;
	@Autowired
	private OtpInfoService otpInfoService;
	@Autowired
	private RequestUtils requestUtils;
    @Autowired
    private ShaUtils shaUtils;
	@Autowired
	private SimulatorConfig simulatorConfig;
    @Value("${resource.receipt-url}")
    private String receiptUrl;
    @Value("${resource.check-code}")
    private String checkCode;

	@PostMapping(value = "/init-payment", produces = "application/json")
	public DefaultResponse initTaxPayment(@RequestBody EtaxEncryptedRequest etaxEncryptedRequest) {
		DefaultResponse response = new DefaultResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
		try {
			// Validate request
			if (!requestUtils.validEtaxEncryptedRequest(etaxEncryptedRequest))
				return response;

			/**
			 * Check eSignature
			 * */
			if (!GeneralTransConfig.ENV_TEST.equals(simulatorConfig.getEnvDo())) {
				String dataSignBase = etaxEncryptedRequest.getDataBase64();
				String eSignBase64 = etaxEncryptedRequest.getESign().getValue();
				String certChainBase64Encoded = etaxEncryptedRequest.getESign().getCertificates();
				boolean verifyESign = ESignatureAuth.verify(dataSignBase, eSignBase64, certChainBase64Encoded);
				if (!verifyESign) {
					response.setResponseCode(EtaxErrorConfig.ERROR_CODE_07);
					response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_07);
					return response;
				}
			}
			// Create base simple decoder object
			Base64.Decoder b64Decoder = Base64.getDecoder();
			// Decoding the encoded string using decoder
			String taxPaymentData = new String(b64Decoder.decode(etaxEncryptedRequest.getDataBase64().getBytes()));
			// Parse JSON string to object
			objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			TaxPaymentInfo taxPaymentInfo = objectMapper.readValue(taxPaymentData, TaxPaymentInfo.class);
			// Processing
			response = taxPaymentService.initTaxPayment(taxPaymentInfo);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		return response;
	}

	@PostMapping(value = "/confirm-transaction", produces = "application/json")
	public DefaultResponse confirmTransaction(@RequestBody EtaxEncryptedRequest etaxEncryptedRequest) {
		ConfrmTransResponse response = new ConfrmTransResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
		try {
			// Validate request
			if (!requestUtils.validEtaxEncryptedRequest(etaxEncryptedRequest))
				return response;

			/**
			 * Check eSignature
			 * */
			if (!GeneralTransConfig.ENV_TEST.equals(simulatorConfig.getEnvDo())) {
				String dataSignBase = etaxEncryptedRequest.getDataBase64();
				String eSignBase64 = etaxEncryptedRequest.getESign().getValue();
				String certChainBase64Encoded = etaxEncryptedRequest.getESign().getCertificates();
				boolean verifyESign = ESignatureAuth.verify(dataSignBase, eSignBase64, certChainBase64Encoded);
				if (!verifyESign) {
					response.setResponseCode(EtaxErrorConfig.ERROR_CODE_07);
					response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_07);
					return response;
				}
			}
			// Create base simple decoder object
			Base64.Decoder b64Decoder = Base64.getDecoder();
			// Decoding the encoded string using decoder
			String transConfirmData = new String(b64Decoder.decode(etaxEncryptedRequest.getDataBase64().getBytes()));
			// Parse JSON string to object
			objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			TransConfirmation transConfirmation = objectMapper.readValue(transConfirmData, TransConfirmation.class);
			// verify OTP
			DefaultResponse result = otpInfoService.verifyOtp(transConfirmation);
			response.setResponseCode(result.getResponseCode());
			response.setResponseDesc(result.getResponseDesc());
			if (!EtaxErrorConfig.ERROR_CODE_00.equals(result.getResponseCode()))
				return response;
			/**
			 * Return immediately after otp verification for the link/unlink taxpayer info transactions
			 * */
			if (transConfirmation.getTransId().length() > 14) {
				response = taxPaymentService.confirmAssignAccount(transConfirmation.getTransId());
				return response;
			}
			/**
			 * Process payment
			 * */
			response = taxPaymentService.confirmTaxPayment(transConfirmation.getTransId());
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		return response;
	}

//	@PostMapping(value = "/feedback-transaction", produces = "application/json")
//	public DefaultResponse feedbackTransaction(@RequestBody TransFeedback transFeedback) {
//		DefaultResponse response = new DefaultResponse();
//		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
//		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
//		return response;
//	}

	@PostMapping(value = "/feedback-transaction", produces = "application/json")
	public DefaultResponse feedbackTransaction(@RequestBody EtaxEncryptedRequest etaxEncryptedRequest) {
		DefaultResponse response = new DefaultResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
		try {
			// Validate request
			if (!requestUtils.validEtaxEncryptedRequest(etaxEncryptedRequest))
				return response;

			/**
			 * Check eSignature
			 * */
			if (!GeneralTransConfig.ENV_TEST.equals(simulatorConfig.getEnvDo())) {
				String dataSignBase = etaxEncryptedRequest.getDataBase64();
				String eSignBase64 = etaxEncryptedRequest.getESign().getValue();
				String certChainBase64Encoded = etaxEncryptedRequest.getESign().getCertificates();
				boolean verifyESign = ESignatureAuth.verify(dataSignBase, eSignBase64, certChainBase64Encoded);
				if (!verifyESign) {
					response.setResponseCode(EtaxErrorConfig.ERROR_CODE_07);
					response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_07);
					return response;
				}
			}
			// Create base simple decoder object
			Base64.Decoder b64Decoder = Base64.getDecoder();
			// Decoding the encoded string using decoder
			String transFeedbackData = new String(b64Decoder.decode(etaxEncryptedRequest.getDataBase64().getBytes()));
			// Parse JSON string to object
			objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			TransFeedback transFeedback = objectMapper.readValue(transFeedbackData, TransFeedback.class);
			// process feedback
			response.setResponseCode(EtaxErrorConfig.ERROR_CODE_00);
			response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_00);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		return response;
	}

	@PostMapping(value = "/print-receipt", produces = "application/json")
	public EReceiptResponse printEReceipt(@RequestBody EReceiptRequest eReceiptRequest) {
        EReceiptResponse response = new EReceiptResponse();
        response.setResponseCode(EtaxErrorConfig.ERROR_CODE_99);
        response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_99);
        try {
            String partnerCode = eReceiptRequest.getPartnerCode();
            String refNo = eReceiptRequest.getReferenceNumber();
            String transTime = eReceiptRequest.getTransTime();
            /**
             * Validate checksum data
             * */
            if (!GeneralTransConfig.ENV_TEST.equals(simulatorConfig.getEnvDo())) {
                String checkData = partnerCode +"|"+ refNo +"|"+ transTime +"|"+ checkCode;
                log.info("CheckData: "+ checkData);
                String checksum = shaUtils.hash256(checkData);
                log.info("Checksum: "+ checksum);
                if (!checksum.equals(eReceiptRequest.getChecksum())) {
                    response.setResponseCode(EtaxErrorConfig.ERROR_CODE_07);
                    response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_07);
                    return response;
                }
            }
            response = taxPaymentService.printEReceipt(refNo);
            if (EtaxErrorConfig.ERROR_CODE_00.equals(response.getResponseCode())) {
                /**
                 * Using SHA-256 to generate the cache key based on the factors: refNo, randomId and checkCode
                 * */
                String refData = refNo +"|"+ UUID.randomUUID() +"|"+ checkCode;
                log.info("RefData: "+ refData);
                String refCache = shaUtils.hash256(refData);
                /**
                 * The refCache will be valid in 5 minutes
                 * */
                CacheUtils.putDataToCache(restTemplate, "ETAX_PAYMENT", refCache, refNo, new Long(300));
                response.setEReceiptUrl(receiptUrl + refCache);
            };
        } catch(Exception ex) {
            log.error("printEReceipt, exception: "+ ex);
        }
		return response;
	}

    @GetMapping(value = "/get-receipt/{refCache}", produces = "application/json")
    public ResponseEntity getEReceipt(@PathVariable String refCache) {
        try {
            String refNo = CacheUtils.getCacheValue(restTemplate, "ETAX_PAYMENT", refCache);
            log.info("Get refNo from cache: "+ refNo);
            if ((refNo == null) || (refNo.isEmpty())) {
                return ResponseEntity.badRequest().body("Link da het han");
            }
            return taxPaymentService.getEReceipt(refNo);
        } catch(Exception ex) {
            log.error("getEReceipt, exception: "+ ex);
            return ResponseEntity.badRequest().body("Loi khong xac dinh");
        }
    }

}
