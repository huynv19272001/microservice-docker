package com.lpb.esb.service.common.utils.validate;

import com.lpb.esb.service.common.constant.MessageContent;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.DateUtils;
import com.lpb.esb.service.common.utils.ExceptionUtils;
import com.lpb.esb.service.common.utils.StringUtils;
import lombok.extern.log4j.Log4j2;

import javax.persistence.Column;
import java.util.Date;
import java.util.Objects;

@Log4j2
public class ValidateCommon {
    public static void validateNullObject(Object object, String objectName) {
        if (StringUtils.isNullOrBlank(String.valueOf(object))) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(MessageContent.VALIDATE_NULL.label)
                .errorDesc(MessageContent.getMessage(MessageContent.VALIDATE_NULL.description, objectName))
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            throw new ExceptionUtils(responseModel);
        } else if (Objects.isNull(object)) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(MessageContent.VALIDATE_NULL.label)
                .errorDesc(MessageContent.getMessage(MessageContent.VALIDATE_NULL.description, objectName))
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            throw new ExceptionUtils(responseModel);
        }
    }

    public static void validateLengthObject(String param, Object object, String objectName) throws NoSuchFieldException {
        if (param.length() > object.getClass().getDeclaredField(objectName).getAnnotation(Column.class).length()) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(MessageContent.VALIDATE_LENGTH.label)
                .errorDesc(MessageContent.getMessage(MessageContent.VALIDATE_LENGTH.description, objectName))
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            throw new ExceptionUtils(responseModel);
        }
    }

    public static void validateCurrentDate(String date, String objectName, String format) {
        Date valueDate = DateUtils.convertStringToDate(date, format);
        if (valueDate == null) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(MessageContent.VALIDATE_FORMAT_DATE.label)
                .errorDesc(MessageContent.getMessage(MessageContent.VALIDATE_FORMAT_DATE.description, objectName, format))
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            throw new ExceptionUtils(responseModel);
        }
        Date currentDate = new Date();
        Date parseCurrentDate = DateUtils.convertStringToDate(DateUtils.convertDateToString(currentDate, "dd/MM/yyyy"), "dd/MM/yyyy");
        Date parseValueDate = DateUtils.convertStringToDate(DateUtils.convertDateToString(valueDate, "dd/MM/yyyy"), "dd/MM/yyyy");
        int result = parseValueDate.compareTo(parseCurrentDate);
        if (result != 0) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(MessageContent.VALIDATE_CURRENT_DATE.label)
                .errorDesc(MessageContent.getMessage(MessageContent.VALIDATE_CURRENT_DATE.description, objectName))
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            throw new ExceptionUtils(responseModel);
        }
    }

    public static void validateSmallCurrentDate(String date, String objectName, String format) throws NoSuchFieldException {
        Date valueDate = DateUtils.convertStringToDate(date, format);
        if (valueDate == null) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(MessageContent.VALIDATE_FORMAT_DATE.label)
                .errorDesc(MessageContent.getMessage(MessageContent.VALIDATE_FORMAT_DATE.description, objectName, format))
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            throw new ExceptionUtils(responseModel);
        }
        Date currentDate = new Date();
        Date parseCurrentDate = DateUtils.convertStringToDate(DateUtils.convertDateToString(currentDate, "dd/MM/yyyy"), "dd/MM/yyyy");
        Date parseValueDate = DateUtils.convertStringToDate(DateUtils.convertDateToString(valueDate, "dd/MM/yyyy"), "dd/MM/yyyy");
        int result = parseValueDate.compareTo(parseCurrentDate);
        if (result < 0) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(MessageContent.VALIDATE_SMALL_CURRENT_DATE.label)
                .errorDesc(MessageContent.getMessage(MessageContent.VALIDATE_SMALL_CURRENT_DATE.description, objectName))
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            throw new ExceptionUtils(responseModel);
        }
    }
}
