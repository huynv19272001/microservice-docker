package com.lpb.esb.service.sms.utils;

import com.lpb.esb.service.sms.model.request.EsbSmsRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

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

    public String buildFtelKey(EsbSmsRequest esbSmsRequest) {
        String key = esbSmsRequest.getEsbHeader().getServiceId()
            + "#" + esbSmsRequest.getEsbHeader().getProductCode()
            + "#" + esbSmsRequest.getEsbHeader().getHasRole();
        return key;
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

}
