package com.lpb.esb.service.lv24.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.lv24.config.RestTemplateConfig;
import com.lpb.esb.service.lv24.exception.validation.FieldValidator;
import com.lpb.esb.service.lv24.model.EsbRequestDTO;
import com.lpb.esb.service.lv24.model.config.Lv24FileConfig;
import com.lpb.esb.service.lv24.model.lvt.request.BdsdMessage;
import com.lpb.esb.service.lv24.model.lvt.request.BdsdMessageList;
import com.lpb.esb.service.lv24.model.lvt.response.Response;
import com.lpb.esb.service.lv24.model.lvt.response.ResponseHeader;
import com.lpb.esb.service.lv24.service.DangKyBdsdQuaNotificationService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class DangKyBdsdQuaNotificationServiceImpl implements DangKyBdsdQuaNotificationService {
    @Autowired
    Lv24FileConfig lv24FileConfig;
    @Autowired
    RestTemplateConfig restTemplate;
    @Autowired
    FieldValidator fieldValidator;

    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public ResponseModel sendMessages(EsbRequestDTO esbRequest) {
        fieldValidator.validate(esbRequest);
        String msgId = esbRequest.getHeader().getMsgId();

//        ObjectMapper objectMapper = new ObjectMapper();
        ResponseModel responseModel;
        LpbResCode lpbResCode;

        log.info("Message ID: " + msgId + " | ESB REQ: " + objectMapper.writeValueAsString(esbRequest));

        BdsdMessageList esbRequestData = objectMapper.readValue(objectMapper.writeValueAsString(esbRequest.getBody().getData()), BdsdMessageList.class);

        List<ServiceInfo> serviceInfos = ConnectInfoService.getServiceInfo(restTemplate.getRestTemplateLB(), esbRequest.getHeader().getServiceId(), esbRequest.getHeader().getProductCode());

        if (serviceInfos == null || serviceInfos.size() <= 0) {
            lpbResCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Service not exits!").build();
            responseModel = ResponseModel.builder().resCode(lpbResCode).build();
            return responseModel;
        }

        String user = serviceInfos.get(0).getUdf1();
        String password = serviceInfos.get(0).getUdf2();
        String url = serviceInfos.get(0).getConnectorURL();
        //get date now in yyyyMMddHHmmss format
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String requestDateTime = formatter.format(date);
        //build message list to send
        StringBuilder messageList = new StringBuilder();
        log.info("Total records: {}", esbRequestData.getListMessage().size());
        for (BdsdMessage message : esbRequestData.getListMessage()) {
            messageList.append("<Message>");
            messageList.append("<TransDate>" + message.getTransDate() + "</TransDate>");
            messageList.append("<MobileNo>" + message.getMobileNo() + "</MobileNo>");
            messageList.append("<AccountNo>" + message.getAccountNo() + "</AccountNo>");
            messageList.append("<SmsContent>" + message.getSmsContent() + "</SmsContent>");
            messageList.append("<TransRefno>" + message.getTransRefno() + "</TransRefno>");
            messageList.append("</Message>");
        }

        //add data to LVT request data field
        String lvtRequestDataTemp = FileUtils.readFileToString(new File(lv24FileConfig.getFilePrefix() + lv24FileConfig.getLv24BdsdNotiRequestData()), (Charset) null);
        String lvtRequestData = String.format(lvtRequestDataTemp, password, msgId, requestDateTime, messageList);
        log.info("Message ID: " + msgId + " | LVT RequestData: " + lvtRequestData);

        //build then send LVT request
        String lvtRequestExecuteFile = FileUtils.readFileToString(new File(lv24FileConfig.getFilePrefix() + lv24FileConfig.getLv24BdsdExecute()), (Charset) null);
        String lvtRequestExecute = String.format(lvtRequestExecuteFile, user, lvtRequestData);
        log.info("Message ID: " + msgId + " | lvtRequestExecute: " + lvtRequestExecute);

        //send request to Atomi
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "text/xml;charset=UTF-8");
        headers.set("SOAPAction", "urn:Execute");
        headers.setAccept(Arrays.asList(MediaType.TEXT_XML));
        HttpEntity<String> entity = new HttpEntity<>(lvtRequestExecute, headers);
        String lvtResponse = restTemplate.getRestTemplate().postForEntity(url, entity, String.class).getBody();
        log.info("Message ID: " + msgId + " | lvtResponse: " + lvtResponse);

        XmlMapper xmlMapper = new XmlMapper();
        ResponseHeader jsonLvtResData = xmlMapper
            .readValue(xmlMapper
                    .readTree(lvtResponse)
                    .get("Body")
                    .get("executeResponse")
                    .get("return")
                    .get("ResponseData")
                    .textValue()
                , Response.class
            )
            .getResponseHeader();

        String lvtResCode = jsonLvtResData.getResponseCode();
        String lvtResMsg = jsonLvtResData.getResponseMessage();
        //if success
        if (lvtResCode.equals("0")) {
            lpbResCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Service Success")
                .refCode(lvtResCode)
                .refDesc(lvtResMsg)
                .build();

            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .data(jsonLvtResData)
                .build();
        } else {
            lpbResCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Service Failure")
                .refCode(lvtResCode)
                .refDesc(lvtResMsg)
                .build();

            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
        }
        log.info("Message ID: " + msgId + " | ESB RES: " + objectMapper.writeValueAsString(responseModel));
        return responseModel;
    }
}
