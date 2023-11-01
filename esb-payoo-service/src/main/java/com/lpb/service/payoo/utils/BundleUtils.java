package com.lpb.service.payoo.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class BundleUtils {
    public static String mapMessagePayBillBE(String subReturnCode, String extraInfo, String message) {
        if (subReturnCode.equals(ResponseCodePayBillBE.RESERVATION_BEEN_PAID.getResponseCodePayoo())) {
            String orderCode = extraInfo.replaceAll("ORDER_CODE:", "").trim();
            message = message.replaceAll("[x]", orderCode);
        }
        if (subReturnCode.equals(ResponseCodePayBillBE.AMOUNT_NOT_WITHIN_LIMIT5.getResponseCodePayoo())
            || subReturnCode.equals(ResponseCodePayBillBE.AMOUNT_NOT_WITHIN_LIMIT6.getResponseCodePayoo())) {
            String[] info = extraInfo.split("\\|");
            String cutomerId = info[0].replaceAll("CUSTOMER_ID:", "").trim();
            String maxAmount = info[1].replaceAll("MAX_AMOUNT:", "").trim();
            message = message.replaceAll("[x]", cutomerId);
            message = message.replaceAll("[y]", maxAmount);
        }
        if (subReturnCode.equals(ResponseCodePayBillBE.EXCEEDING_ALLOWED_NUMBER_TIMES.getResponseCodePayoo())) {
            String[] info = extraInfo.split("\\|");
            String cutomerId = info[0].replaceAll("CUSTOMER_ID:", "").trim();
            String maxTimes = info[1].replaceAll("MAX_TIMES:", "").trim();
            message = message.replaceAll("[x]", cutomerId);
            message = message.replaceAll("[z]", maxTimes);
        }
        if (subReturnCode.equals(ResponseCodePayBillBE.AMOUNT_NOT_WITHIN_LIMIT3.getResponseCodePayoo())
            || subReturnCode.equals(ResponseCodePayBillBE.AMOUNT_NOT_WITHIN_LIMIT4.getResponseCodePayoo())) {
            String amount = extraInfo.replaceAll("“AMOUNT:", "").trim();
            message = message.replaceAll("[y]", amount);
        }
        return message;
    }

    public static String mapMessageQueryBillEx(String subReturnCode, String extraInfo, String message) {
        if (subReturnCode.equals(ResponseCodeQueryBillEx.ORDER_BEEN_PAID.getResponseCodePayoo())) {
            String orderCode = extraInfo.replaceAll("ORDER_CODE:", "").trim();
            message = message.replaceAll("[x]", orderCode);
        }

        if (subReturnCode.equals(ResponseCodeQueryBillEx.RESERVATION_BEEN_PAID.getResponseCodePayoo())) {
            String orderCode = extraInfo.replaceAll("BOOKING_CODE:", "").trim();
            message = message.replaceAll("[x]", orderCode);
        }

        if (subReturnCode.equals(ResponseCodeQueryBillEx.ORDER_EXPIRED.getResponseCodePayoo())) {
            String orderCode = extraInfo.replaceAll("ORDER_CODE:", "").trim();
            message = message.replaceAll("[x]", orderCode);
        }

        if (subReturnCode.equals(ResponseCodeQueryBillEx.BALANCE_EXCEEDS.getResponseCodePayoo())) {
            String orderCode = extraInfo.replaceAll("MAX_AMOUNT:", "").trim();
            message = message.replaceAll("[x]", orderCode);
        }
        return message;
    }

}
