package com.lpb.esb.service.common.constant;

import com.lpb.esb.service.common.utils.StringUtils;

import java.text.MessageFormat;

public enum MessageContent {

    SUCCESS("00", "SUCCESS"), FAIL("99", "System error"), NO_DATA("90", ""),
    VALIDATE_NULL("06", "Validation error, field {0} cannot be null"),
    VALIDATE_LENGTH("07", "Validation error, field {0} too long"),
    VALIDATE_SMALL_CURRENT_DATE("08", "Validation error, field {0} < currentdate"),
    VALIDATE_FORMAT_DATE("09", "Validation error, field {0} unlike format {1}"),
    VERIFY_SIGNATURE("10", "Verify signature fail"),
    VALIDATE_CURRENT_DATE("11", "Validation error, field {0} < > currentdate");

    public final String label;
    public final String description;

    private MessageContent(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public static String getMessage(String msgPattern, String param) {
        if (!StringUtils.isNullOrBlank(msgPattern) && !StringUtils.isNullOrBlank(param)) {
            msgPattern = MessageFormat.format(msgPattern, param);
        }
        return msgPattern;
    }

    public static String getMessage(String msgPattern, String param1, String param2) {
        if (!StringUtils.isNullOrBlank(msgPattern) && !StringUtils.isNullOrBlank(param1)
            && !StringUtils.isNullOrBlank(param2)) {
            msgPattern = MessageFormat.format(msgPattern, param1, param2);
        }
        return msgPattern;
    }
}
