package com.esb.transaction.utils;

import com.esb.transaction.dto.PartnerDTO;
import com.esb.transaction.dto.PostInfoDTO;
import com.esb.transaction.dto.ServiceDTO;
import com.lpb.esb.service.common.utils.StringUtils;
import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Log4j2
public class ESBUtils {
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

    public static String createXmlServiceDTO(ServiceDTO serviceDto) {
        StringBuilder bu = new StringBuilder();
        bu.append("<SERVICE>");
        bu.append("<SERVICE_ID>" + serviceDto.getServiceId() + "</SERVICE_ID>");
        bu.append("<PRODUCT>");
        bu.append("<PRODUCT_CODE>" + serviceDto.getProductCode()
            + "</PRODUCT_CODE>");
        bu.append("</PRODUCT>");
        bu.append("<MERCHANT_ID>" + serviceDto.getMerchantId()
            + "</MERCHANT_ID>");
        bu.append("</SERVICE>");
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

    public static String createXmlPartner(PartnerDTO partnerDTO) {
        StringBuilder bu = new StringBuilder();
        bu.append("<PARTNER>");
        bu.append("<TXN_REF_NO>" + partnerDTO.getTxnRefNo() + "</TXN_REF_NO>");
        bu.append("<TXN_DATETIME>" + partnerDTO.getTxnDatetime() + "</TXN_DATETIME>");
        bu.append("<TXN_CODE>" + partnerDTO.getTxnCode() + "</TXN_CODE>");
        bu.append("<CHANEL>" + partnerDTO.getChanel() + "</CHANEL>");
        bu.append("</PARTNER>");
        return bu.toString();
    }

    public static String createXmlPostInfo(List<PostInfoDTO> listPost) {
        StringBuilder bu = new StringBuilder();
        bu.append("<TRN_POST>");
        for (PostInfoDTO postInfoDTO : listPost) {
            bu.append("<POST_INFO>");
            bu.append("<ACCOUNT>");
            bu.append("<PROVIDER/>");
            bu.append("<ORGANIZE_ISSUE/>");
            bu.append("<MODULE>" + postInfoDTO.getServiceType() + "</MODULE>");
            bu.append("<AC_NO>" + postInfoDTO.getAcNo() + "</AC_NO>");
            bu.append("<AC_DESC/>");
            bu.append("<BRANCH_CODE>" + postInfoDTO.getBranchCode()
                + "</BRANCH_CODE>");
            bu.append("<CUSTOMER_NO>" + postInfoDTO.getCustomerNo()
                + "</CUSTOMER_NO>");
            bu.append("<CCY>" + postInfoDTO.getCcy() + "</CCY>");
            if (StringUtils.isNullOrBlank(postInfoDTO.getCardNo())) {
                bu.append("<REF_ACCOUNT><AC_NO></AC_NO></REF_ACCOUNT>");
            } else {
                bu.append("<REF_ACCOUNT><AC_NO>" + genCardNumber(postInfoDTO.getCardNo()) + "</AC_NO></REF_ACCOUNT>");
            }
            bu.append("</ACCOUNT>");
            if (postInfoDTO.getFcyAmount() != null) {
                bu.append("<FCY_AMOUNT>" + postInfoDTO.getFcyAmount()
                    + "</FCY_AMOUNT>");
            } else {
                bu.append("<FCY_AMOUNT></FCY_AMOUNT>");
            }
            bu.append("<LCY_AMOUNT>" + postInfoDTO.getLcyAmount()
                + "</LCY_AMOUNT>");
            bu.append("<TXN_CCY>" + postInfoDTO.getCcy() + "</TXN_CCY>");
            bu.append("<DRCR_IND>" + postInfoDTO.getDrcrInd() + "</DRCR_IND>");
            bu.append("<BANK_CODE/>");
            bu.append("<BANK_NAME/>");
            bu.append("<POST_DESC/>");

            bu.append("<AMOUNT_TAG />");
            bu.append("</POST_INFO>");
        }
        bu.append("</TRN_POST>");
        return bu.toString();
    }

    public static String genCardNumber(String cardNo) {
        if (!StringUtils.isNullOrBlank(cardNo)) {
            String unCardNumber = "";
            //lấy type card
            String typeCard = cardNo.substring(0, 6);
            //lấy 4 số cuối
            String bonSoCuoi = cardNo.substring(cardNo.length() - 4);
            //chuyển sang dấu *
            for (int i = 0; i < cardNo.length() - 10; i++) {
                unCardNumber = unCardNumber + "*";
            }
            String cardNumber = typeCard + unCardNumber + bonSoCuoi;
            return cardNumber;
        } else {
            return null;
        }
    }
}
