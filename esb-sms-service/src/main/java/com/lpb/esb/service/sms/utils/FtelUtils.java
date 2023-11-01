package com.lpb.esb.service.sms.utils;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.sms.model.entities.EsbErrorPartnerMessageEntity;
import com.lpb.esb.service.sms.model.request.EsbSmsRequest;
import com.lpb.esb.service.sms.repositories.EsbErrorPartnerMessageRepository;
import com.lpb.esb.service.sms.repositories.PkgEsbUtilRepository;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Created by tudv1 on 2022-05-19
 */
@Component
@Log4j2
public class FtelUtils {
    @Autowired
    PkgEsbUtilRepository pkgEsbUtilRepository;
    @Autowired
    EsbErrorPartnerMessageRepository esbErrorPartnerMessageRepository;
//    public ResponseModel parseFtelError(ExecuteModel executeModel) {
//        LpbResCode lpbResCode = new LpbResCode();
//        try {
//            JSONObject jsonObject = new JSONObject(executeModel.getMessage());
//            int errorCode = jsonObject.getInt("error");
//            String errorDesc = jsonObject.getString("error_description");
//            lpbResCode.setRefCode(errorCode + "");
//            lpbResCode.setRefDesc(errorDesc);
//
//            switch (lpbResCode.getRefCode().trim()) {
//                case "1001":
//                    lpbResCode.setErrorCode("11");
//                    break;
//                case "1002":
//                    lpbResCode.setErrorCode("21");
//                    break;
//                case "1003":
//                    lpbResCode.setErrorCode("21");
//                    break;
//                case "1004":
//                    lpbResCode.setErrorCode("32");
//                    break;
//                case "1005":
//                    lpbResCode.setErrorCode("37");
//                    break;
//                case "1006":
//                    lpbResCode.setErrorCode("98");
//                    break;
//                case "1007":
//                    lpbResCode.setErrorCode("98");
//                    break;
//                case "1008":
//                    lpbResCode.setErrorCode("2");
//                    break;
//                case "1009":
//                    lpbResCode.setErrorCode("10");
//                    break;
//                case "1010":
//                    lpbResCode.setErrorCode("37");
//                    break;
//                case "1011":
//                    lpbResCode.setErrorCode("106");
//                    break;
//                case "1012":
//                    lpbResCode.setErrorCode("106");
//                    break;
//                case "1013":
//                    lpbResCode.setErrorCode("106");
//                    break;
//                case "1014":
//                    lpbResCode.setErrorCode("11");
//                    break;
//                case "1015":
//                    lpbResCode.setErrorCode("11");
//                    break;
//                case "1016":
//                    lpbResCode.setErrorCode("14");
//                    break;
//                default:
//                    lpbResCode.setErrorCode("99");
//                    break;
//            }
//        } catch (Exception e) {
//
//            lpbResCode.setErrorCode("98");
//            lpbResCode.setRefCode(executeModel.getStatusCode() + "");
//            lpbResCode.setRefDesc(HttpStatus.valueOf(executeModel.getStatusCode().intValue()).getReasonPhrase());
//        }
//        pkgEsbUtilRepository.esbLoadErrorDesc(lpbResCode);
//
//        ResponseModel responseModel = ResponseModel.builder()
//            .resCode(lpbResCode)
//            .build();
//
//        return responseModel;
//    }
public ResponseModel parseFtelError(ExecuteModel executeModel, EsbSmsRequest esbSmsRequest) {
    LpbResCode lpbResCode = new LpbResCode();
    try {
        JSONObject jsonObject = new JSONObject(executeModel.getMessage());
        String respCode = jsonObject.getString("status");
        String respDesc = jsonObject.getString("description");
        lpbResCode.setRefCode(respCode);
        lpbResCode.setRefDesc(respDesc);

    } catch (Exception e) {

        lpbResCode.setErrorCode("ESB_99");
        lpbResCode.setRefCode(executeModel.getStatusCode() + "");
        lpbResCode.setRefDesc(HttpStatus.valueOf(executeModel.getStatusCode().intValue()).getReasonPhrase());
    }
    EsbErrorPartnerMessageEntity errorMessage = esbErrorPartnerMessageRepository.getErrorMessage(esbSmsRequest.getEsbHeader().getServiceId(),"FTEL", lpbResCode.getRefCode());

    lpbResCode.setErrorCode(errorMessage.getErrorCode());
    lpbResCode.setErrorDesc(errorMessage.getDescription());
    ResponseModel responseModel = ResponseModel.builder()
        .resCode(lpbResCode)
        .data(new JSONObject(executeModel.getMessage()).toMap())
        .build();

    return responseModel;
}

    public String getTokenLoginFtel(String body) {
        JSONObject jsonObject = new JSONObject(body);
        String accessToken = jsonObject.getString("access_token");
        log.info("access_token: {}", accessToken);
        return accessToken;
    }

    public long getTtlTokenFtel(String body) {
        JSONObject jsonObject = new JSONObject(body);
        long ttl = jsonObject.getLong("expires_in");
        log.info("access_token: {}", ttl);
        return ttl;
    }

    public ResponseModel parseFtelPushSuccess(String body) {
        JSONObject jsonObject = new JSONObject(body);
        String msgId = jsonObject.getString("MessageId");
        String des = jsonObject.getString("Telco");
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode("00")
            .errorDesc("Success")
            .refCode(msgId)
            .refDesc(des)
            .build();

        pkgEsbUtilRepository.esbLoadErrorDesc(lpbResCode);

        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .data(jsonObject.toMap())
            .build();
        return responseModel;
    }
}
