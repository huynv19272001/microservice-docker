package com.lpb.service.payoo.utils;

public enum ResponseCodePayBillBE {
    ACCOUNTING_STEP1("1","Đã thanh toán với VietUnion và chưa gạch nợ cho Khách hàng","509","Đã thanh toán với VietUnion và chưa gạch nợ cho Khách hàng."),
    SUCCESSFUL("0", "Thanh toán và đã gạch nợ cho khách hàng","000","Thành công."),
    FAIL_INVALID_INFORMATION("-1", "Thanh toán hóa đơn bị thất bại. Do thông tin thanh toán không hợp lệ", "005","Giao dịch thất bại"),
    TIMEOUT("-3","Thanh toán hóa đơn bị Timeout.","090","Timeout"),
    AMOUNT_NOT_WITHIN_LIMIT("-4", "Số tiền thanh toán không nằm trong phạm vi (Min,Max) cho phép","291","Số tiền thanh toán không nằm trong hạn mức giao dịch cho phép"),
    FAIL_AGENT_NOT_ENOUGH("-7", "Thanh toán hóa đơn bị thất bại. Do số dư TK của Đối tác không đủ thanh toán","005","Thanh toán hóa đơn bị thất bại. Do số dư TK của Đối tác không đủ tiền thanh toán."),
    ERROR_CODE("-9", "Đây là mã lỗi chung","006", "Lỗi không xác định từ đối tác"),
    INVALID_REQUESTTIME("2001", "Thông tin RequestTime không hợp lệ.","011","Field Input không hợp lệ."),
    INVALID_IP("2005","IP không hợp lệ","021","IP không được phép gửi tin"),
    INVALID_SIGNATURE("2004","Chữ ký điện tử không hợp lệ","288","Lỗi mã hoá."),
    ORDER_BEEN_PAID("-111", "Hóa đơn đã được thanh toán trước đó.","003","Khách hàng không còn nợ cước"),
    AMOUNT_NOT_WITHIN_LIMIT2("-118", "Số tiền thanh toán không nằm trong giới hạn Min - Max","291","Số tiền thanh toán không nằm trong hạn mức giao dịch cho phép"),
    RESERVATION_BEEN_PAID("-119","Hóa đơn [x] đã hết hạn thanh toán theo qui định nhà cung cấp.","019","Mã hóa đơn hết hạn hoặc đã thanh toán"),
    AMOUNT_NOT_WITHIN_LIMIT3("-115","Số tiền thanh toán phải nhỏ hơn hoặc bằng [y] (VND)","291","Số tiền thanh toán không nằm trong hạn mức giao dịch cho phép"),
    AMOUNT_NOT_WITHIN_LIMIT4("-116","Số tiền thanh toán phải lớn hơn hoặc bằng [y] (VND)","291","Số tiền thanh toán không nằm trong hạn mức giao dịch cho phép"),
    AMOUNT_NOT_WITHIN_LIMIT5("-112", "Bạn đã thanh toán vượt số tiền quy định cho mã khách hàng [x] ([y] đ/tháng)","291","Số tiền thanh toán không nằm trong hạn mức giao dịch cho phép"),
    AMOUNT_NOT_WITHIN_LIMIT6("-113","Bạn đã thanh toán vượt số tiền quy định cho mã khách hàng[x] ([y]đ/ngày).","291","Số tiền thanh toán không nằm trong hạn mức giao dịch cho phép"),
    EXCEEDING_ALLOWED_NUMBER_TIMES("-114", "Bạn đã thanh toán vượt số lần quy định cho mã khách hàng[x] ([z]lần/tháng)","485","Giao dịch thất bại do thanh toán vượt quá số lần cho phép trên một hợp đồng"),
    SERVICE_OFFLINE("-117","Hệ thống chưa hỗ trợ thanh toán cho dịch vụ / nhà cung cấp này.","493","Dịch vụ Nhà cung cấp không hợp lệ");

    private String responseCodePayoo;
    private String responseCode;
    private String responseMessage;

    private String responseMessagePayoo;


    private ResponseCodePayBillBE(String responseCodePayoo, String responseMessagePayoo, String responseCode, String responseMessage) {
        this.responseCodePayoo = responseCodePayoo;
        this.responseMessagePayoo = responseMessagePayoo;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public String getResponseMessagePayoo() {
        return responseMessagePayoo;
    }

    public void setResponseMessagePayoo(String responseMessagePayoo) {
        this.responseMessagePayoo = responseMessagePayoo;
    }

    public String getResponseCodePayoo() {
        return responseCodePayoo;
    }

    public void setResponseCodePayoo(String responseCodePayoo) {
        this.responseCodePayoo = responseCodePayoo;
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
