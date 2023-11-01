package com.esb.card.utils;

import com.esb.card.dto.telcoRequest.EsbTelCoReqDTO;
import com.esb.card.dto.telcoResponse.EsbTelcoBodyResDTO;
import com.esb.card.dto.telcoResponse.EsbTelcoResDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tudv1 on 2022-05-16
 */
@Component
@Log4j2
public class LogicUtils {
    public static String YYYYMMdd = "yyyy-MM-dd HH:mm:ss";

    //    public String switchTypeSmsFPT(ServiceInfo serviceInfo) {
//        String typeMsg = "";
//        switch (serviceInfo.getProductCode().trim()) {
//            case "SEND_BANCHNAME":
//                typeMsg = "send_brandname";
//                break;
//            case "SEND_BANCHNAME_OTP":
//                typeMsg = "send_brandname_otp";
//                break;
//            default:
//                typeMsg = "send_brandname_otp";
//                break;
//        }
//        return typeMsg;
//    }
    public String formatDate(Long date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMdd);
            return sdf.format(date);
        } catch (Exception ex) {
        }
        return "";
    }

    public String convertDateToTimestamp(Long date1) {
        try {
            DateFormat df = new SimpleDateFormat("dd-MMM-yy hh:mm:ss a");
            Date date = new Date(System.currentTimeMillis());
            return df.format(date);
        } catch (Exception e) {

        }
        return null;
    }


    public String phoneTo84(String mobileNumber) {
        // format mobileNumber to 84
        String fMobile = "";

        mobileNumber = mobileNumber.replace("o", "0");
        if (mobileNumber.startsWith("84")) {
            return mobileNumber;
        } else if (mobileNumber.startsWith("0")) {
            mobileNumber = "84" + mobileNumber.substring(1);
            fMobile = mobileNumber;

        } else if (mobileNumber.startsWith("+84")) {
            mobileNumber = mobileNumber.substring(1);
            fMobile = mobileNumber;
        } else {
            mobileNumber = "84" + mobileNumber;
            fMobile = mobileNumber;
        }
        return fMobile;
    }

    public String phone84To0(String mobileNumber) {
        String fMobile = "";
        mobileNumber = mobileNumber.replace("o", "0");
        if (mobileNumber.startsWith("0")) {
            fMobile = mobileNumber;
        } else if (mobileNumber.startsWith("84")) {
            mobileNumber = "0" + mobileNumber.substring(2);
            fMobile = mobileNumber;
        } else if (mobileNumber.startsWith("+84")) {
            mobileNumber = "0" + mobileNumber.substring(3);
            fMobile = mobileNumber;
        } else {
            mobileNumber = "0" + mobileNumber;
            fMobile = mobileNumber;
        }
        return fMobile;
    }

    public String checkValidPhone(String mobileNumber){
        String mb = "";
        if (mobileNumber.length() != 10){
            mb = "";
        } else {
            mb = mobileNumber;
        }
        return mb;
    }
    private EsbTelcoBodyResDTO convertCardToObject(EsbTelcoResDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo, EsbTelCoReqDTO esbTelCoReqDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());
        log.info(" TelcoUpdate: " + "RES " + xmlResponse);
        EsbTelcoBodyResDTO esbTelcoBodyResDTO = xmlMapper.readValue(xmlResponse, EsbTelcoBodyResDTO.class);

        return esbTelcoBodyResDTO;
    }
}

