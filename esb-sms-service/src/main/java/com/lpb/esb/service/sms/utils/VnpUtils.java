package com.lpb.esb.service.sms.utils;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.sms.model.entities.EsbErrorPartnerMessageEntity;
import com.lpb.esb.service.sms.model.request.EsbSmsRequest;
import com.lpb.esb.service.sms.repositories.EsbErrorPartnerMessageRepository;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by cuongnm10 on 2022-07-04
 */
@Component
@Log4j2
public class VnpUtils {
    public static String YYYYMMdd = "yyyyMMddhhmmss";
    @Autowired
    EsbErrorPartnerMessageRepository esbErrorPartnerMessageRepository;

    public ResponseModel parseVnpError(ExecuteModel executeModel, EsbSmsRequest esbSmsRequest) {
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
        EsbErrorPartnerMessageEntity errorMessage = esbErrorPartnerMessageRepository.getErrorMessage(esbSmsRequest.getEsbHeader().getServiceId(),"VNP", lpbResCode.getRefCode());

        lpbResCode.setErrorCode(errorMessage.getErrorCode());
        lpbResCode.setErrorDesc(errorMessage.getDescription());
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .data(new JSONObject(executeModel.getMessage()).toMap())
            .build();

        return responseModel;
    }

    public static String formatDate(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMdd);
            return sdf.format(date);
        } catch (Exception ex) {
        }
        return "";
    }

    public Long getTimeLong(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMdd);
            String dt = sdf.format(date);
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern(YYYYMMdd);
            LocalDateTime localDate = LocalDateTime.parse(dt, inputFormat);
            return localDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (Exception ex) {
        }
        return 0l;
    }

    public ResponseModel parseVnpPushSuccess(String body) {
        JSONObject jsonObject = new JSONObject(body);
        String msgId = jsonObject.getString("status");
        String des = jsonObject.getString("description");
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode("ESB_00")
            .errorDesc("Success")
            .refCode(msgId)
            .refDesc(des)
            .build();

//        pkgEsbUtilRepository.esbLoadErrorDesc(lpbResCode);

        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .data(jsonObject.toMap())
            .build();
        return responseModel;
    }
}
