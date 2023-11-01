package com.lpb.esb.service.transaction.utils;

import com.lpb.esb.service.transaction.model.EsbRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
@Log4j2
public class ESBUtils {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static String YYMMdd = "YYMMdd";
    public static String HHmmssSS = "HHmmssSS";
    public static String YYYYMMddHHmmssSSSSSS = "YYYYMMddHHmmssSSSSSS";
    public static String HHmmssSSSS = "HHmmssSSSS";
    public static String YYYYMMdd = "YYYYMMdd";

    public static String formatDate(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception ex) {
            log.error("formatDate Exception ex " + ex.getMessage());
        }
        return "";
    }

    public static String genRefCode(String cardNumber, String userName) {
        StringBuilder bu = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(ESBUtils.YYMMdd);
        bu.append(userName);
        bu.append(dateFormat.format(cal.getTime()));

        if (cardNumber.length() > 16) {
            bu.append(cardNumber.substring(9, 19));
        } else if (cardNumber.length() == 16) {
            bu.append(cardNumber.substring(6, 16));
        } else {
            bu.append("0000000000");
        }
        dateFormat = new SimpleDateFormat(ESBUtils.HHmmssSS);
        bu.append(dateFormat.format(cal.getTime()));
        return bu.toString();
    }

    public static String genRequestCode(String channelCode) {
        StringBuilder bu = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(ESBUtils.YYYYMMdd);
        bu.append(channelCode);
        bu.append(dateFormat.format(cal.getTime()));
        dateFormat = new SimpleDateFormat(ESBUtils.HHmmssSSSS);
        bu.append(dateFormat.format(cal.getTime()));
        return bu.toString();
    }

    public String loadSequencesTypeMsg(String typeMsg) {
        SimpleJdbcCall jdbcCall;
        String result = "";
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_ESB_UTIL")
                .withFunctionName("loadSequencesTypeMsg")
                .withReturnValue();
            SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("typeMsg", typeMsg);
            Map results = jdbcCall.execute(paramMap);
            result = (String) results.get("return");
        } catch (Exception e) {
            log.info("Exception loadSequencesTypeMsg: " + e.getMessage());
            throw e;
        }
        return result;
    }

    public static String createXmlServiceDTO(EsbRequestDTO data) {
        StringBuilder bu = new StringBuilder();
        bu.append("<SERVICE>");
        bu.append("<SERVICE_ID>" + data.getHeader().getServiceId() + "</SERVICE_ID>");
        bu.append("<PRODUCT><PRODUCT_CODE>" + data.getHeader().getProductCode() + "</PRODUCT_CODE></PRODUCT>");
        bu.append("<MERCHANT_ID>" + data.getBody().getTransactionInfo().getMerchantId() + "</MERCHANT_ID>");
        bu.append("<SERVICE_INFO><BILLS>");

        /*
        if (data.getHeader().getServiceId().equals("580000") && data.getHeader().getProductCode().equals("TCT_CHUNGTU_HUY")) {
            bu.append("<BILL>" +
                "<BILL_ID>" + data.getBody().getBillInfo().getBillId() + "</BILL_ID>" +
                "<BILL_CODE>" + data.getBody().getBillInfo().getBillCode() + "</BILL_CODE>" +
                "<BILL_DESC>" + data.getBody().getTransactionInfo().getTranDesc() + "</BILL_DESC>" +
                "<BILL_AMOUNT>" + data.getBody().getTransactionInfo().getSettleAmount() + "</BILL_AMOUNT>" +
                "<SETTLED_AMOUNT>" + data.getBody().getTransactionInfo().getSettleAmount() + "</SETTLED_AMOUNT>" +
                "<AMT_UNIT></AMT_UNIT>" +
                "<OTHER_INFO></OTHER_INFO>" +
                "<BILL_TYPE></BILL_TYPE>" +
                "<BILL_STATUS></BILL_STATUS>" +
                "<MSG_ID>" + data.getHeader().getMsgId() + "</MSG_ID>" +
                "<PAYMENT_METHOD></PAYMENT_METHOD>" +
                "</BILL>");
        } else if (data.getHeader().getServiceId().equals("580000") && data.getHeader().getProductCode().equals("TCT_CHUNGTU_NOP")) {
            bu.append("<BILL>" +
                "<BILL_ID></BILL_ID>" +
                "<BILL_CODE>" + data.getBody().getBillThueInfo().getChungtu().getSo_chungtu() + "</BILL_CODE>" +
                "<BILL_DESC>" + data.getBody().getTransactionInfo().getTranDesc() + "</BILL_DESC>" +
                "<BILL_AMOUNT>" + data.getBody().getTransactionInfo().getSettleAmount() + "</BILL_AMOUNT>" +
                "<SETTLED_AMOUNT>" + data.getBody().getTransactionInfo().getSettleAmount() + "</SETTLED_AMOUNT>" +
                "<AMT_UNIT></AMT_UNIT>" +
                "<OTHER_INFO></OTHER_INFO>" +
                "<BILL_TYPE></BILL_TYPE>" +
                "<BILL_STATUS></BILL_STATUS>" +
                "<MSG_ID>" + data.getHeader().getMsgId() + "</MSG_ID>" +
                "<PAYMENT_METHOD></PAYMENT_METHOD>" +
                "</BILL>");
        }
        else if(data.getHeader().getServiceId().equals("570000")){
            bu.append("<BILL>" +
                "<BILL_ID></BILL_ID>" +
                "<BILL_CODE></BILL_CODE>" +
                "<BILL_DESC></BILL_DESC>" +
                "<BILL_AMOUNT>" + data.getBody().getMobifoneInfo().getSettlementAmount() + "</BILL_AMOUNT>" +
                "<SETTLED_AMOUNT>" + data.getBody().getMobifoneInfo().getSettlementAmount() + "</SETTLED_AMOUNT>" +
                "<AMT_UNIT></AMT_UNIT>" +
                "<OTHER_INFO></OTHER_INFO>" +
                "<BILL_TYPE></BILL_TYPE>" +
                "<BILL_STATUS></BILL_STATUS>" +
                "<MSG_ID>" + data.getHeader().getMsgId() + "</MSG_ID>" +
                "<PAYMENT_METHOD></PAYMENT_METHOD>" +
                "</BILL>");
        }
         */
        bu.append("<BILL>" +
            "<BILL_CODE>" + data.getBody().getTransactionInfo().getTranRefNo() + "</BILL_CODE>" +
            "<BILL_DESC>" + data.getBody().getTransactionInfo().getTranDesc() + "</BILL_DESC>" +
            "<BILL_AMOUNT>" + data.getBody().getTransactionInfo().getSettleAmount() + "</BILL_AMOUNT>" +
            "<SETTLED_AMOUNT>" + data.getBody().getTransactionInfo().getSettleAmount() + "</SETTLED_AMOUNT>" +
            "<AMT_UNIT></AMT_UNIT>" +
            "<OTHER_INFO></OTHER_INFO>" +
            "<BILL_TYPE></BILL_TYPE>" +
            "<BILL_STATUS></BILL_STATUS>" +
            "<MSG_ID>" + data.getHeader().getMsgId() + "</MSG_ID>" +
            "<PAYMENT_METHOD></PAYMENT_METHOD>" +
            "</BILL>");
        bu.append("</BILLS></SERVICE_INFO></SERVICE>");
        return bu.toString();
    }

    public static String createXmlCustomer(String customerNo) {
        StringBuilder bu = new StringBuilder();
        bu.append("<CUSTOMER>");
        bu.append("<USER_ID></USER_ID>");
        bu.append("<CUSTOMER_NO>" + customerNo + "</CUSTOMER_NO>");
        bu.append("<KIND_OF_OTP></KIND_OF_OTP>");
        bu.append("</CUSTOMER>");
        return bu.toString();
    }

    public static String createXmlPartner(EsbRequestDTO data) {
        StringBuilder bu = new StringBuilder();
//        if (data.getHeader().getServiceId().equals("570000")) {
//            bu.append("<PARTNER>");
//            bu.append("<TXN_REF_NO></TXN_REF_NO>");
//            bu.append("<TXN_DATETIME></TXN_DATETIME>");
//            bu.append("<TXN_CODE></TXN_CODE>");
//            bu.append("<CHANEL></CHANEL>");
//            bu.append("<TERMINAL_ID></TERMINAL_ID>");
//            bu.append("</PARTNER>");
//        } else {
        bu.append("<PARTNER>");
        bu.append("<TXN_REF_NO>" + data.getBody().getTransactionInfo().getTranRefNo() + "</TXN_REF_NO>");
        bu.append("<TXN_DATETIME>" + data.getBody().getTransactionInfo().getTranDt() + "</TXN_DATETIME>");
        bu.append("<TXN_CODE>" + data.getBody().getTransactionInfo().getTranCode() + "</TXN_CODE>");
        bu.append("<CHANEL>" + data.getBody().getTransactionInfo().getChannel() + "</CHANEL>");
        bu.append("<TERMINAL_ID>" + data.getBody().getTransactionInfo().getTerminalId() + "</TERMINAL_ID>");
        bu.append("</PARTNER>");
        return bu.toString();
    }
}
