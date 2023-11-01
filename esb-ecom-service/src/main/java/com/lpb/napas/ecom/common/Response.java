package com.lpb.napas.ecom.common;

public enum Response {
    //Thành công
    SUCCESSFULL("0", "Successful"),
    //Yêu cầu không hợp lệ
    INVALIID("1", "Invalid request"),
    //Mật khẩu đối tác sai
    WRONG_PARTER_PASSWORD("2", "Wrong partner password"),
    //Giao dịch đã hết hạn
    EXPIRED_TRANSACTION("3", "Expired transaction"),
    //Chữ ký sai
    WRONG_SIGNATURE("4", "Wrong signature"),
    //Giao dịch không tồn tại
    TRANSACTION_DOES_NOT_EXIST("5", "Transaction does not exist"),
    //Giao dịch không hợp lệ
    INVALID_TRANSACTION("6", "Invalid transaction"),
    //Trạng thái giao dịch không hợp lệ
    INVALID_TRANSACTION_STATUS("7", "Invalid transaction status"),
    //Giao dịch trùng lặp
    DUPLICATE_TRANSACTION("8", "Duplicate transaction"),
    //Thông tin thẻ sai
    WRONG_CARD_INFO("9", "Wrong card info"),
    //Số thẻ / số tài khoản sai
    WRONG_CARD_NUMBER_ACCOUNT_NUMBER("10", "Wrong card number/account number"),
    //Tên trên thẻ / tên tài khoản sai
    WRONG_NAME_ON_CARD_ACCOUNT_NAME("11", "Wrong name on card/account name"),
    //Thẻ hết hạn sai
    WRONG_CARD_EXPIRY("12", "Wrong card expiry"),
    //Ngày phát hành sai
    WRONG_ISSUE_DATE("13", "Wrong issue date"),
    //Thẻ / Tài khoản không hợp lệ
    CARD_ACCOUNT_IS_INVALID("14", "Card/Account is invalid"),
    //Thẻ / Tài khoản bị khóa
    CARD_ACCOUNT_IS_LOCKED("15", "Card/Account is locked"),
    //Thẻ / Tài khoản chưa được đăng ký để thanh toán trực tuyến
    CARD_ACCOUNT_HAVE_NOT_BEEN_REGISTERED_FOR_ONLINE_PAYMENT("16", "Card/Account haven’t been registered for online payment"),
    //Số tiền giao dịch vượt quá một giới hạn giao dịch
    TRANSACTION_AMOUNT_EXCEEDS_ONE_TRANSACTION_LIMIT("17", "Transaction amount exceeds one transaction limit"),
    //Số tiền giao dịch vượt quá giới hạn mỗi ngày
    TRANSACTION_AMOUNT_EXCEEDS_LIMIT_PER_DAY("18", "Transaction amount exceeds limit per day"),
    //Thẻ / Tài khoản không đủ số dư
    CARD_ACCOUNT_HAS_INSUFFICIENT_BALANCE("19", "Card/Account has insufficient balance"),
    //Sai OTP
    WRONG_OTP("20", "Wrong OTP"),
    //OTP quá hạn
    EXPIRED_OTP("21", "Expired OTP"),
    //Số lượng giao dịch vượt quá giới hạn mỗi ngày
    TRANSACTION_NUMBER_EXCEEDS_LIMIT_PER_DAY("23", "Transaction number exceeds limit per day"),
    //Giao dịch không được phép
    NOT_ALLOWED_TRANSACTION("97", "Not allowed transaction"),
    //Lỗi khác
    OTHER_ERROR("98", "Other error"),
    //LỖI BÊN TRONG
    INTERNAL_ERROR("99", "Internal error");

    private String responseCode;
    private String responseMessage;

    private Response(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("\"responseCode\":").append("\"").append(this.responseCode).append("\"").append(",");
        stringBuilder.append("\"responseMessage\":").append("\"").append(this.responseMessage).append("\"");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
