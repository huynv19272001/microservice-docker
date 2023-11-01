/**
 * @author Trung.Nguyen
 * @date 19-Apr-2022
 * */
package com.lpb.esb.etax.inquiry.controller;

import java.util.Base64;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.etax.inquiry.config.SimulatorConfig;
import com.lpb.esb.etax.inquiry.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.inquiry.model.config.GeneralTransConfig;
import com.lpb.esb.etax.inquiry.model.data.TaxPayerInfo;
import com.lpb.esb.etax.inquiry.model.request.EtaxEncryptedRequest;
import com.lpb.esb.etax.inquiry.model.response.DefaultResponse;
import com.lpb.esb.etax.inquiry.service.TaxPayerInfoInqService;
import com.lpb.esb.etax.inquiry.util.RequestUtils;
import com.lpb.esb.etax.inquiry.util.sign.ESignatureAuth;

@RestController
@RequestMapping(value = "/api/v1")
@Log4j2
public class TaxPayerInfoInqController {

	@Autowired
	private TaxPayerInfoInqService taxPayerInfoInqService;
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private SimulatorConfig simulatorConfig;

	@PostMapping(value = "/verify-payer-info", produces = "application/json")
	public DefaultResponse verifyPayerInfo(@RequestBody EtaxEncryptedRequest etaxEncryptedRequest) {
		DefaultResponse response = new DefaultResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
		ObjectMapper objectMapper = new ObjectMapper();
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
			String taxPayerInfoData = new String(b64Decoder.decode(etaxEncryptedRequest.getDataBase64().getBytes()));
			// Parse JSON string to object
			objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			TaxPayerInfo taxPayerInfo = objectMapper.readValue(taxPayerInfoData, TaxPayerInfo.class);
			// Processing
			response = taxPayerInfoInqService.verifyPayerInfo(taxPayerInfo);
		} catch (JsonProcessingException ex) {
            log.error("verify-payer-info, exception: "+ ex.getMessage());
			ex.printStackTrace();
			return response;
		} catch (IllegalArgumentException ex) {
            log.error("verify-payer-info, exception: "+ ex.getMessage());
			ex.printStackTrace();
			return response;
		} finally {
			// to do
		}
		return response;
	}

	@PostMapping(value = "/link-payer-info", produces = "application/json")
	public DefaultResponse assignAccount2TaxPayer(@RequestBody EtaxEncryptedRequest etaxEncryptedRequest) {
		DefaultResponse response = new DefaultResponse();
		response.setResponseCode(EtaxErrorConfig.ERROR_CODE_02);
		response.setResponseDesc(EtaxErrorConfig.ERROR_DESC_02);
		ObjectMapper objectMapper = new ObjectMapper();
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
			String taxPayerInfoData = new String(b64Decoder.decode(etaxEncryptedRequest.getDataBase64().getBytes()));
			// Parse JSON string to object
			objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
			TaxPayerInfo taxPayerInfo = objectMapper.readValue(taxPayerInfoData, TaxPayerInfo.class);
			// Processing
			response = taxPayerInfoInqService.assignAccount2TaxPayer(taxPayerInfo);
		} catch (JsonProcessingException ex) {
            log.error("link-payer-info, exception: "+ ex.getMessage());
			ex.printStackTrace();
			return response;
		} catch (IllegalArgumentException ex) {
            log.error("link-payer-info, exception: "+ ex.getMessage());
			ex.printStackTrace();
			return response;
		} finally {
			// to do
		}
		return response;
	}

}
