package com.lpd.esb.service.mobifone.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpd.esb.service.mobifone.configuration.RestTemplateConfig;
import com.lpd.esb.service.mobifone.model.Request;
import com.lpd.esb.service.mobifone.model.mobifone.BillInfoShortResponse;
import com.lpd.esb.service.mobifone.model.mobifone.MobifoneRequest;
import com.lpd.esb.service.mobifone.model.mobifone.MobifoneRequestBody;
import com.lpd.esb.service.mobifone.model.mobifone.MobifoneResponse;
import com.lpd.esb.service.mobifone.service.LogInService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Log4j2
public class MobifoneUtility {
    @Autowired
    RestTemplateConfig restTemplate;

    private String mobiResCode;
    private String mobiResMsg;

    @SneakyThrows
    public ArrayList<MobifoneRequestBody> buildMobiReqBody(Request request, List<ServiceInfo> serviceInfos) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<MobifoneRequestBody> mobiBody = new ArrayList<>();

        //0, 3, 94
        String[] fields = serviceInfos.get(0).getUdf2().split("(?<=})");
        for (int i = 0; i < fields.length; i++) {
            mobiBody.add(objectMapper.readValue(fields[i], MobifoneRequestBody.class));
        }
        //7
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
        Date now = new Date();
        String dateNow = simpleDateFormat.format(now);
        log.info("dateNow: " + dateNow);
        MobifoneRequestBody mobiReqBody = MobifoneRequestBody.builder()
            .fieldId("7")
            .value(dateNow)
            .build();
        mobiBody.add(mobiReqBody);
        //42
        mobiReqBody = MobifoneRequestBody.builder()
            .fieldId("42")
            .value(request.getHeader().getMsgId())
            .build();
        mobiBody.add(mobiReqBody);
        return mobiBody;
    }

    public ArrayList<MobifoneRequestBody> buildMobiReqBodyBillPay(Request request, ArrayList<MobifoneRequestBody> mobiBody) {
        //12
        MobifoneRequestBody mobiReqBody = MobifoneRequestBody.builder()
            .fieldId("12")
            .value("970449")
            .build();
        mobiBody.add(mobiReqBody);
        //61
        mobiReqBody = MobifoneRequestBody.builder()
            .fieldId("61")
            .value(request.getBody().getData().getPhoneNumber() + "|" + request.getBody().getData().getCustCode() + "|" + request.getBody().getData().getSettlementAmount())
            .build();
        mobiBody.add(mobiReqBody);
        //73
        mobiReqBody = MobifoneRequestBody.builder()
            .fieldId("73")
            .value(request.getBody().getData().getSettlementDate())
            .build();
        mobiBody.add(mobiReqBody);
        return mobiBody;
    }

    @SneakyThrows
    public void sendMobiRequest(Request request, List<ServiceInfo> serviceInfos, ArrayList<MobifoneRequestBody> mobiBody, LogInService logInService) {
        ObjectMapper objectMapper = new ObjectMapper();

        MobifoneRequest mobifoneRequest = MobifoneRequest.builder()
            .token(logInService.getToken())
            .body(mobiBody)
            .build();
        log.info("msgId: " + request.getHeader().getMsgId() + " | Mobifone Request: " + objectMapper.writeValueAsString(mobifoneRequest));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<MobifoneRequest> entity = new HttpEntity<>(mobifoneRequest, headers);
        MobifoneResponse mobiResponse = restTemplate.getRestTemplate().postForObject(
            serviceInfos.get(0).getConnectorURL(), entity, MobifoneResponse.class);
        log.info("msgId: " + request.getHeader().getMsgId() + " | Mobifone Response: " + objectMapper.writeValueAsString(mobiResponse));

        processXmlPayload(mobiResponse);
    }

    @SneakyThrows
    public void processXmlPayload(MobifoneResponse mobiResponse) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(new StringReader(mobiResponse.getPayload())));
        document.getDocumentElement().normalize();
        NodeList payload = document.getElementsByTagName("field");

        for (int i = 0; i < payload.getLength(); i++) {
            Node node = payload.item(i);
            Element element = (Element) node;
            if (element.getAttribute("id").equals("39")) {
                mobiResCode = element.getAttribute("value");
            } else if (element.getAttribute("id").equals("61")) {
                mobiResMsg = element.getAttribute("value");
            }
        }
    }

    public String getMobiResCode() {
        return mobiResCode;
    }

    public String getMobiResMsg() {
        return mobiResMsg;
    }

    public ResponseModel checkMobiCodeSuccess(BillInfoShortResponse billInfoShortResponse) {
        LpbResCode lpbResCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
        ResponseModel responseModel;

        if (mobiResCode.equals("00")) {
            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .data(billInfoShortResponse)
                .build();
        } else {
            lpbResCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Failure")
                .refCode(mobiResCode)
                .refDesc(mobiResMsg)
                .build();

            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
        }
        return responseModel;
    }
}
