package com.lpb.esb.service.common.utils.code;

public enum ErrorMessage {
    SUCCESS("00", "Thành công"),
    FAIL("99", "Lỗi hệ thống"),
    TIMEOUT("90", "Timeout"),
    NODATA("01", "Không có dữ liệu"),
    SERVICE_NOT_EXIST("02", "Service không tồn tại"),
    INPUT_ERROR("11", "Field Input không hợp lệ"),
    WRONG_MANY_OTP("43", "OTP sai quá số lần quy định"),
    WRONG_OTP("42", "Sai mã OTP"),
    EXPIRED_OTP("41", "OTP quá hạn"),
    AVAILABLE_AMOUNT("44", "Tài khoản số dư không đủ"),
    DUPLICATE_MESSAGE("45", "Duplicate message"),
    INVALID_ACCOUNT_NUMBER("46", "Số tài khoản không đúng"),
    OTHER_ERROR("47", "Lỗi khác"),
    ACCESS_DENIED("48", "Access is denied"),
    FILE_NOT_EXISTS("49", "File does not exists"),
    FILE_NOT_SYNTAX_INCORRECT("50", "The filename, directory name, or volume label syntax is incorrect"),
    SERVICE_NOT_EXITS("48", "Service không tồn tại"),
    WRONG_USER_PASSWORD("49", "Username, Password nhập vào không đúng"),
    USER_INFO_NOTFOUND("110", "Không tìm thấy thông tin user"),
    SPECIALIZED_ACCOUNT_NOTFOUND("111", "Ngân hàng chưa triển khai thanh toán tiền với đối tác tại mã Khu vực này"),
    NO_SUPPORT("493", "Dịch vụ Nhà cung cấp không hợp lệ");

    public final String label;

    public final String description;

    private ErrorMessage(String label, String description) {
        this.label = label;
        this.description = description;
    }
}
