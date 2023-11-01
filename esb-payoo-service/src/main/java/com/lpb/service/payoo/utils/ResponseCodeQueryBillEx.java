package com.lpb.service.payoo.utils;

public enum ResponseCodeQueryBillEx {
    SUCCESSFUL("0", "Query bill thành công","000", "Thành công"),
    FAIL("-1", "Truy vấn hóa đơn thất bại","005","Truy vấn hóa đơn thất bại"),
    TIMEOUT("-3", " Truy vấn hóa đơn bị time out","090", "Timeout"),
    CUSTOMER_NOT_INVOICE("3","Khách hàng chưa phát sinh hóa đơn bên hệ thống nhà cung cấp","495","Mã hóa đơn không chính xác hoặc chưa đến kỳ thanh toán"),
    CUSTOMER_NOT_FOUND( "4","Không tìm thấy thông tin Khách hàng theo thông tin truy vấn:SearchValue, ServiceId và ProviderId","290", "Không tìm thấy hóa đơn"),
    ERROR_CODE("-9","Mã lỗi chung","006", "Lỗi không xác định từ đối tác"),
    SERVICE_WITHIN_LIMIT("-10","Cặp Dịch vụ - Nhà cung cấp đối tác gửi qua đang nằm trong giới hạn mặt hàng","493","Dịch vụ Nhà cung cấp không hợp lệ"),
    SERVICE_OFFLINE("-14","Thông tin ServiceId và ProviderId tại hệ thống VietUnion đang là Offline","493","Dịch vụ Nhà cung cấp không hợp lệ"),
    MORE_INFORMATION("-25", "Khách hàng có nhiều loại phí cần thanh toán.","498", "Khách hàng có nhiều loại phí cần thanh toán"),
    SERVICE_NOT_FOUND("-26", "Thông tin ServiceId và ProviderId không tồn tại trong hệ thống VietUnion.","493","Dịch vụ Nhà cung cấp không hợp lệ"),
    INVOICE_LOCK("-28","Hóa đơn Khách hàng đang bị khóa tại hệ thống Nhà cung cấp.","499","Hóa đơn Khách hàng đang bị khóa tại hệ thống Nhà cung cấp."),
    CUS_ARES_NOT_SUPPORT("-84", "Thông tin Khách hàng (SearchValue) đang nằm trong khu vực không hỗ trợ thanh toán qua VietUnion.","502","Thông tin Khách hàng đang nằm trong khu vực không hỗ trợ thanh toán"),
    SUPPLIER_OFF_SYSTEM("-85", "Hiện tại Nhà cung cấp đang Off hệ thống.","501","Hiện tại Nhà cung cấp đang Off hệ thống."),
    EXCEEDING_NUMBER_QUERY("-86", "Vượt quá số lần truy vấn trong ngày do VietUnion qui định .","500","Vượt quá số lần truy vấn trong ngày"),
    INVALID_REQUESTTIME("2001", "Thông tin RequestTime không hợp lệ","011","Field Input không hợp lệ."),
    INVALID_IP("2005", "IP không hợp lệ.","021","IP không được phép gửi tin"),
    INVALID_SIGNATURE("2004","Chữ ký điện tử không hợp lệ.","288","Lỗi mã hoá."),
    PROVIDER_INTERRUPTION("-101", "Nhà cung cấp/ dịch vụ tạm gián đoạn","508","Nhà cung cấp/dịch vụ tạm gián đoạn"),
    SUPPORT_SUSPENSION_AREA("-102", "Khu vực tạm ngưng hỗ trợ.","507","Khu vực tạm ngưng hỗ trợ"),
    AREA_NOT_SERVICED("-103","Khu vực chưa phục vụ","506","Khu vực chưa phục vụ"),
    ECOMMERCE_NOT_EXIST("-104", "Mã thanh toán TMĐT không tồn tại.","505","Mã thanh toán TMĐT không tồn tại"),
    SCHOOL_NOT_SUPPORT_PAYMENT("-105", "Trường học chưa hỗ trợ thanh toán","504","Trường học chưa hỗ trợ thanh toán"),
    ORDER_BEEN_PAID("-106","Mã đơn hàng [x] đã được thanh toán","003","Khách hàng không còn nợ cước"),
    RESERVATION_BEEN_PAID("-107","Mã đặt chỗ [x] hết thời gian hiệu lực hoặc đã được thanh toán trước đó","019","Mã hóa đơn hết hạn hoặc đã thanh toán"),
    INVALID_DEPOSIT_ACCOUNT("-108","Tài khoảng nạp tiền không hợp lệ","503","Tài khoản nạp tiền không hợp lệ"),
    CUSTOMER_NOT_EXIST("-109","Mã khách hàng không tồn tại hoặc chưa phát sinh hóa đơn thanh toán","495","Mã hóa đơn không chính xác hoặc chưa đến kỳ thanh toán"),
    ORDER_EXPIRED("-110","Đơn hàng [x] đã hết hạn thanh toán.","496","Hết thời gian thanh toán"),
    BALANCE_EXCEEDS("-120","Dư nợ vượt quá số tiền thanh toán tối đa [x]","497","Hạn mức không đủ để thanh toán");

    private String responseCodePayoo;
    private String responseCode;
    private String responseMessage;

    private String responseMessagePayoo;

    private ResponseCodeQueryBillEx(String responseCodePayoo, String responseMessagePayoo, String responseCode, String responseMessage) {
        this.responseCodePayoo = responseCodePayoo;
        this.responseMessagePayoo= responseMessagePayoo;
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
