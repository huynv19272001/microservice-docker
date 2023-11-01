package com.lpb.service.vimo.util;

public enum ResponseCode {
    SUCCESSFUL("00", "000", "Thành công"),
    FAIL("01", "005","Giao dịch thất bại"),
    INVALID_PARAMETER("02", "011", "Field Input không hợp lệ"),
    INVALID_CHECKSUM("03" , "011", "Field Input không hợp lệ"),
    IP_LOCKED("04", "021", "IP không được phép gửi tin"),
    PARTNER_LOCKED("05", "488","Đối tác bị khóa hoặc chưa được khai báo"),
    PARAMETER_INCORRECT("06", "011","Field Input không hợp lệ"),
    SERVICE_NOT_DECLARED("07", "489","Dịch vụ chưa được khai báo"),
    PRODUCT_NOT_DECLARED("08", "490","Sản phẩm chưa được khai báo"),
    ATTRIBUTES_NOT_DECLARED("09", "491","Thuộc tính/Mệnh giá sản phẩm chưa được khai báo"),
    CHANNEL_NOT_DECLARED("20", "492","Kênh chưa được khai báo"),
    INVALID_PROVIDER_SERVICE("21", "493","Dịch vụ Nhà cung cấp không hợp lệ"),
    DUPLICATE_TRANSACTION("22", "294","Giao dịch bị lặp lại"),
    SERVICE_REQUEST_NOT_FOUND("23", "494","Không tìm thấy yêu cầu dịch vụ"),
    DUPLICATE_PAYMENT_REQUEST("24", "294","Giao dịch bị lặp lại"),
    BALANCE_INENOUGH("25","280", "Số dư tài khoản không hợp lệ"),
    PAYMENT_NOT_DUE_YET("26", "495","Mã hóa đơn không chính xác hoặc chưa đến kỳ thanh toán"),
    PAYOUT_TIMEOUT("27", "496","Hết thời gian thanh toán"),
    PAYMENT_CODE_NOT_EXIST("28", "290", "Không tìm thấy hóa đơn"),
    INVALID_AMOUNT("29", "028", "Mã thanh toán và số tiền không hợp lệ"),
    TICKET_BLOCK("30", "486","Vé bị block sau khi thanh toán"),
    PAID_BY_ANOTHER_BRANCH("31", "019", "Mã hóa đơn hết hạn hoặc đã thanh toán"),
    LIMIT_INENOUGH("32", "497","Hạn mức không đủ để thanh toán"),
    INCORRECT_SURCHARGE_AMOUNT("33", "028","Số tiền không hợp lệ"),
    INVALID_PAYMENT_AMOUNT("34","028", "Số tiền không hợp lệ"),
    TRANSACTION_OVERPAYMENT("35", "485","Giao dịch thất bại do thanh toán vượt quá số lần cho phép trên một hợp đồng"),
    PRE_RECORDED_INVOICE("36", "294","Giao dịch bị lặp lại"),
    POWER_UNIT_LOCKED("37", "487","Đơn vị điện lực đang tạm khóa"),
    TIMEOUT("98","090", "Timeout"),
    EXCEPTION_PARTNER("99", "006", "Lỗi không xác định từ đối tác");

    private String responseCodeVimo;
    private String responseCode;
    private String responseMessage;

        private ResponseCode(String responseCodeVimo, String responseCode, String responseMessage) {
            this.responseCodeVimo = responseCodeVimo;
            this.responseCode = responseCode;
            this.responseMessage = responseMessage;
        }

    public String getResponseCodeVimo() {
        return responseCodeVimo;
    }

    public void setResponseCodeVimo(String responseCodeVimo) {
        this.responseCodeVimo = responseCodeVimo;
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
}
