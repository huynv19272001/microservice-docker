package com.lpb.esb.infocustomerbill.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.infocustomerbill.service.GetInfoCustomerBillService;
import com.lpb.esb.service.common.model.request.infocustomerbill.*;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.model.response.SettleAccountInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class GetInfoCustomerBillServiceImpl implements GetInfoCustomerBillService {

    @Autowired
    RestTemplate restTemplateLB;

    @Override
    public ResponseModel excuteGetInfoCustomerBill(DataQueryDTO REQ) {
        ResponseModel.builder().build();
        ResponseModel responseModel;
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        DataQueryDTO RES = new DataQueryDTO();
        HeaderDTO Header = REQ.getHeader();
        BodyDTO bodyDTO = REQ.getBody();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            log.info(Header.getMsgid() + " Request: " + objectMapper.writeValueAsString(REQ));
//            Get thông tin kết nối
            List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLB, REQ.getBody().getService().getServiceId(), REQ.getBody().getService().getProductCode().toUpperCase(), REQ.getHeader().getOperation().toUpperCase());

            if (list.size() == 0) {
                log.info(Header.getMsgid() + " Error RQ GetInfoCustomerBill: 493 - Dịch vụ Nhà cung cấp không hợp lệ");
                lpbResCode.setErrorCode("493");
                lpbResCode.setErrorDesc("Dịch vụ Nhà cung cấp không hợp lệ");
                responseModel = ResponseModel.builder().resCode(lpbResCode).data(REQ).build();
                return responseModel;
            }
            ServiceInfo serviceInfo = list.get(0);

//            Get Request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            Header.setDestination(serviceInfo.getConnectorURL());
            REQ.setHeader(Header);
            bodyDTO.setServiceInfo(serviceInfo);
            REQ.setBody(bodyDTO);
            log.info(Header.getMsgid() + " Request call partner : " + objectMapper.writeValueAsString(REQ));
            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(REQ), headers);
            log.info(Header.getMsgid() + " Request call partner : " + entity);

//            Get response
            String response = restTemplateLB.exchange(serviceInfo.getUrlApi(), HttpMethod.POST, entity, String.class).getBody();
            log.info(Header.getMsgid() + " Response call partner : " + response);

            JSONObject jsonResponse = new JSONObject(response);
            JSONObject jsonResCode = jsonResponse.getJSONObject("resCode");
            JSONObject jsonData = jsonResponse.getJSONObject("data");
            RES = objectMapper.readValue(jsonData.toString(), DataQueryDTO.class);
            lpbResCode = objectMapper.readValue(jsonResCode.toString(), LpbResCode.class);
            if (lpbResCode.getErrorCode().equals("00")) {
                List<SettleAccountDTO> ListSettleAccount = new ArrayList<>();
                List<SettleAccountInfo> settleAccountInfo = new ArrayList<>();
                for (CustomerDetailDTO CustomerDetail : RES.getBody().getListCustomer()) {
                    if (CustomerDetail.getCustomerInfo() != null) {
                        if (CustomerDetail.getCustomerInfo().getProviderId() != null && CustomerDetail.getCustomerInfo().getProviderId().length() > 0) {
                            settleAccountInfo = ConnectInfoService.getPartnerInfo(restTemplateLB, RES.getBody().getService().getServiceId(), CustomerDetail.getCustomerInfo().getProviderId());
                        } else if (RES.getBody().getService().getMerchantId() != null && RES.getBody().getService().getMerchantId().length() > 0) {
                            settleAccountInfo = ConnectInfoService.getPartnerInfo(restTemplateLB, RES.getBody().getService().getServiceId(), RES.getBody().getService().getMerchantId());
                        }
                        if (settleAccountInfo == null || settleAccountInfo.isEmpty() || settleAccountInfo.size() == 0) {
                            lpbResCode.setErrorCode(ErrorMessage.SPECIALIZED_ACCOUNT_NOTFOUND.label);
                            lpbResCode.setErrorDesc(ErrorMessage.SPECIALIZED_ACCOUNT_NOTFOUND.description);
                            lpbResCode.setRefCode(null);
                            lpbResCode.setRefDesc(null);
                            responseModel = ResponseModel.builder().resCode(lpbResCode).build();
                            return responseModel;
                        } else {
                            for (SettleAccountInfo AccountInfo : settleAccountInfo) {
                                SettleAccountDTO SettleAccount = new SettleAccountDTO();
                                SettleAccount.setSettleAcNo(AccountInfo.getAccNo());
                                SettleAccount.setSettleAcBrn(AccountInfo.getAccBranch());
                                SettleAccount.setSettleCustomerNo(AccountInfo.getCustomerNo());
                                SettleAccount.setSettleAcDesc(AccountInfo.getAccDesc());
                                SettleAccount.setSettleMerchant(AccountInfo.getMerchantId());
                                ListSettleAccount.add(SettleAccount);
                            }
                            CustomerDetail.setSettleAccountInfo(ListSettleAccount);
                        }
                    }
                }
            }
            log.info(Header.getMsgid() + " Response GetInfoCustomerBill: " + response);
        } catch (HttpClientErrorException e) {
            try {
                String noQuotes = StringEscapeUtils.unescapeJson(e.getResponseBodyAsString().replaceAll("^\"|\"$", ""));
                JSONObject jsonResponse = new JSONObject(noQuotes);
                JSONObject jsonResCode = jsonResponse.getJSONObject("resCode");
                JSONObject jsonData = jsonResponse.getJSONObject("data");
                RES = objectMapper.readValue(jsonData.toString(), DataQueryDTO.class);
                lpbResCode = objectMapper.readValue(jsonResCode.toString(), LpbResCode.class);
            } catch (Exception ex) {
                log.info(REQ.getHeader().getMsgid() + " Exception GetInfoCustomerBill: " + ex);
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
        } catch (ResourceAccessException e) {
            log.info(REQ.getHeader().getMsgid() + " ResourceAccessException GetInfoCustomerBill: " + e);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
        } catch (Exception e) {
            log.info(REQ.getHeader().getMsgid() + " Exception GetInfoCustomerBill: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel = ResponseModel.builder().resCode(lpbResCode).data(RES).build();
        log.info(Header.getMsgid() + " Response: " + responseModel);
        return responseModel;
    }
}
