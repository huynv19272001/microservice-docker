/**
 * @author Trung.Nguyen
 * @date 07-Jun-2022
 * */
package com.lpb.esb.etax.inquiry.process;

import java.util.Collections;
import java.util.List;

import com.lpb.esb.etax.inquiry.model.config.GeneralTransConfig;
import com.lpb.esb.etax.inquiry.model.data.LpbCard;
import com.lpb.esb.etax.inquiry.model.data.LpbCardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.etax.inquiry.config.ServiceApiConfig;
import com.lpb.esb.etax.inquiry.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.inquiry.model.data.LpbDebitCard;
import com.lpb.esb.etax.inquiry.util.FlexCubsUtils;
import com.lpb.esb.etax.inquiry.util.RestClientUtils;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CardInfoProcess {

	@Autowired
	private ServiceApiConfig serviceApiConfig;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private RestClientUtils restClientUtils;
	@Autowired
	private FlexCubsUtils flexMsgUtils;

	public ResponseModel<LpbDebitCard> inquiryDebitCard(String cardNumber) {
		ResponseModel<LpbDebitCard> response = new ResponseModel<LpbDebitCard>();
		LpbResCode resCode = new LpbResCode();
		resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_99);
    	resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_99);
    	response.setResCode(resCode);
		try {
			// Create JSON headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			// Build the rest request
			LpbCardInfo lpbDebitCardInfo = LpbCardInfo.builder()
													.cardNumber(cardNumber)
													.inputType("NO")
													.appId(flexMsgUtils.getMsgIdByDate()).build();
			HttpEntity<LpbCardInfo> restRequest = new HttpEntity<>(lpbDebitCardInfo, headers);
			ResponseEntity<String> restResponse = restClientUtils.doPost(serviceApiConfig.getFindDebitCardUrl(), restRequest);
			if ((restResponse == null)) {
                log.error("He thong khong nhan duoc response");
				resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_06);
		    	resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_06);
				return response;
			}
			// Convert JSON to object
			String body = restResponse.getBody();
			log.info("query-debitcard-info, response body: " + body);
			ResponseModel<LpbDebitCard> debitcardInqRes = objectMapper.readValue(body, new TypeReference<ResponseModel<LpbDebitCard>>() {});
			// Check response object
			if ((debitcardInqRes == null) || (debitcardInqRes.getResCode() == null)) {
                log.error("Response khong hop le");
				resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_06);
		    	resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_06);
				return response;
			}
			// Check response code
			if (!EtaxErrorConfig.ERROR_CODE_00.equals(debitcardInqRes.getResCode().getErrorCode())) {
				resCode.setErrorCode(debitcardInqRes.getResCode().getErrorCode());
				resCode.setErrorDesc(debitcardInqRes.getResCode().getErrorDesc());
				return response;
			}
			// Get debit card info
            LpbDebitCard debitCard = (LpbDebitCard) debitcardInqRes.getData();
            if (debitCard == null) {
                resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_057);
                resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_057);
                return response;
            }
            // Check card status
            if (!GeneralTransConfig.CARD_STATUS_CSTS0.equals(debitCard.getCardStatus())) {
                resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_120);
                resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_120);
                return response;
            }
            // return
            response.setData(debitCard);
            resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_00);
            resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_00);
            return response;
		} catch (RestClientException ex) {
			ex.printStackTrace();
			log.error("query-debitcard-info, exception: " + ex.getMessage());
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
			log.error("query-debitcard-info, exception: " + ex.getMessage());
		}
		return response;
	}

    /**
     * @author Trung.Nguyen
     * @date 07-Sep-2022
     * */
    public ResponseModel<LpbCard> inquiryCardInfo(String cardNumber) {
        ResponseModel<LpbCard> response = new ResponseModel<LpbCard>();
        LpbResCode resCode = new LpbResCode();
        resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_99);
        resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_99);
        response.setResCode(resCode);
        try {
            // Create JSON headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            // Build the rest request
            LpbCardInfo lpbCardInfo = LpbCardInfo.builder()
                .cardNumber(cardNumber)
                .inputType("CARD")
                .appId(flexMsgUtils.getMsgIdByDate()).build();
            HttpEntity<LpbCardInfo> restRequest = new HttpEntity<>(lpbCardInfo, headers);
            ResponseEntity<String> restResponse = restClientUtils.doPost(serviceApiConfig.getFindListCardUrl(), restRequest);
            if ((restResponse == null)) {
                log.error("He thong khong nhan duoc response");
                resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_06);
                resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_06);
                return response;
            }
            // Convert JSON to object
            String body = restResponse.getBody();
            log.info("query-card-info, response body: " + body);
            ResponseModel<List<LpbCard>> cardsInqRes = objectMapper.readValue(body, new TypeReference<ResponseModel<List<LpbCard>>>() {});
            // Check response object
            if ((cardsInqRes == null) || (cardsInqRes.getResCode() == null)) {
                log.error("Response khong hop le");
                resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_06);
                resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_06);
                return response;
            }
            // Check response code
            if (!EtaxErrorConfig.ERROR_CODE_00.equals(cardsInqRes.getResCode().getErrorCode())) {
                resCode.setErrorCode(cardsInqRes.getResCode().getErrorCode());
                resCode.setErrorDesc(cardsInqRes.getResCode().getErrorDesc());
                return response;
            }
            // Get list card
            List<LpbCard> cards = (List<LpbCard>) cardsInqRes.getData();
            if (cards == null) {
                resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_057);
                resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_057);
                return response;
            }
            // Get the first card from list
            LpbCard card = cards.get(0);
            if (card == null) {
                resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_057);
                resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_057);
                return response;
            }
            // Check card type
            if (!GeneralTransConfig.CARD_TYPE_LOCAL_DEBIT.equals(card.getCardType())) {
                resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_120);
                resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_120);
                return response;
            }
            // Check card status
            if (!GeneralTransConfig.CARD_STATUS_CSTS0.equals(card.getCardStatus())) {
                resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_120);
                resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_120);
                return response;
            }
            // return
            response.setData(card);
            resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_00);
            resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_00);
            return response;
        } catch (RestClientException ex) {
            ex.printStackTrace();
            log.error("query-card-info, exception: " + ex.getMessage());
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            log.error("query-card-info, exception: " + ex.getMessage());
        }
        return response;
    }

}
