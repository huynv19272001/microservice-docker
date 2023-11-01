/**
 * @author Trung.Nguyen
 * @date 06-Jun-2022
 * */
package com.lpb.esb.etax.payment.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.etax.payment.config.ServiceApiConfig;
import com.lpb.esb.etax.payment.model.config.EtaxErrorConfig;
import com.lpb.esb.etax.payment.model.data.LpbCustomer;
import com.lpb.esb.etax.payment.model.data.LpbCustomerInfo;
import com.lpb.esb.etax.payment.util.RestClientUtils;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.List;

@Component
@Log4j2
public class CustomerInfoProcess {

	@Autowired
	private ServiceApiConfig serviceApiConfig;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private RestClientUtils restClientUtils;

	private LpbCustomer getOpenCIFFrList(List<LpbCustomer> customerList) {
        if ((customerList == null) || (customerList.isEmpty()))
            return null;

        for (LpbCustomer customer : customerList) {
            if ("O".equals(customer.getRecordStat())) return customer;
        }
        return null;
    }

	public ResponseModel<LpbCustomer> inquiryCustomer(String customerNumber, String uniqueValue) {
		ResponseModel<LpbCustomer> response = new ResponseModel<LpbCustomer>();
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
			LpbCustomerInfo lpbCustomerInfo = LpbCustomerInfo.builder()
													.customerNumber(customerNumber)
													.uniqueValue(uniqueValue).build();
			HttpEntity<LpbCustomerInfo> restRequest = new HttpEntity<>(lpbCustomerInfo, headers);
			ResponseEntity<String> restResponse = restClientUtils.doPost(serviceApiConfig.getFlexQueryCustUrl(), restRequest);
			if ((restResponse == null)) {
				log.error("He thong khong nhan duoc response");
				resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_06);
            	resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_06);
				return response;
			}
			// Convert JSON to object
			String body = restResponse.getBody();
			log.info("query-customer-info, response body: " + body);
			ResponseModel<List<LpbCustomer>> customerInqRes = objectMapper.readValue(body, new TypeReference<ResponseModel<List<LpbCustomer>>>() {});
			// Check response object
			if ((customerInqRes == null) || (customerInqRes.getResCode() == null)) {
				log.error("Response khong hop le");
				resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_06);
            	resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_06);
				return response;
			}
			// Check response code
			if (!EtaxErrorConfig.ERROR_CODE_00.equals(customerInqRes.getResCode().getErrorCode())) {
				resCode.setErrorCode(customerInqRes.getResCode().getErrorCode());
				resCode.setErrorDesc(customerInqRes.getResCode().getErrorDesc());
				return response;
			}
			// Get CIF info
            List<LpbCustomer> customerList = (List<LpbCustomer>) customerInqRes.getData();
            LpbCustomer customer = this.getOpenCIFFrList(customerList);
            if (customer == null) {
//            	resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_052);
//            	resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_052);
                resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_118);
                resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_118);
                return response;
            }
            // return
            response.setData(customer);
            resCode.setErrorCode(EtaxErrorConfig.ERROR_CODE_00);
            resCode.setErrorDesc(EtaxErrorConfig.ERROR_DESC_00);
            return response;
		} catch (RestClientException ex) {
			ex.printStackTrace();
			log.error("query-customer-info, exception: " + ex.getMessage());
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
			log.error("query-customer-info, exception: " + ex.getMessage());
		}
		return response;
	}

}
