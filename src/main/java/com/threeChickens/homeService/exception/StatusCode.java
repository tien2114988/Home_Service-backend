package com.threeChickens.homeService.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum StatusCode {
    SUCCESS(1000, "Success", HttpStatus.OK),

    UNCATEGORIZED(-2000, "Lỗi không phân loại", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(-2001,"Lỗi xác thực", HttpStatus.UNAUTHORIZED),
    NO_PERMISSION(-2002,"Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    INVALIDATION(-2003,"Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),

    PASSWORD_INVALID(-1100, "Mật khẩu không hợp lệ", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(-1101, "Tên người dùng không hợp lệ", HttpStatus.BAD_REQUEST),
    OTP_INVALID(-1102,"Mã OTP không hợp lệ", HttpStatus.BAD_REQUEST),
    BANK_ACCOUNT_INVALID(-1103, "Tên tài khoản ngân hàng khác với tên nhân viên", HttpStatus.BAD_REQUEST),
    ROLE_INVALID(-1104,"Role có giá trị CUSTOMER hoặc FREELANCER", HttpStatus.BAD_REQUEST),
    PAYMENT_TYPE_INVALID(-1105,"Payment Type có giá trị QR hoặc CASH", HttpStatus.BAD_REQUEST),
    POST_STATUS_INVALID(-1106,"Post Status có giá trị INITIAL, SCHEDULED, CANCELED, DOING, COMPLETED, FAILED", HttpStatus.BAD_REQUEST),
    PACKAGE_NAME_INVALID(-1107,"Package Name có giá trị _1DAY, _1MONTH, _2MONTH, _3MONTH, _4MONTH", HttpStatus.BAD_REQUEST),
    TAKE_POST_STATUS_INVALID(-1108,"Take Post Status có giá trị PENDING, REJECTED, ACCEPTED", HttpStatus.BAD_REQUEST),
    FREELANCER_WORK_STATUS_INVALID(-1109,"Freelancer Work Status có giá trị INITIAL, DISABLE, WORK, PROHIBITIVE ", HttpStatus.BAD_REQUEST),

    USERNAME_EXISTED(-1200,"Tên người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(-1201,"Địa chỉ email đã tồn tại tài khoản", HttpStatus.BAD_REQUEST),
    OTP_EXISTED(-1202,"Otp đã được sử dụng", HttpStatus.BAD_REQUEST),
    BANK_ACCOUNT_EXISTED(-1203,"Tài khoản ngân hàng đã tồn tại", HttpStatus.BAD_REQUEST),
    TEST_EXISTED(-1204,"Bài test cho dịch vụ đã tồn tại", HttpStatus.BAD_REQUEST),

    BANK_NOT_FOUND(-1301,"Ngân hàng không tồn tại", HttpStatus.BAD_REQUEST),
    BANK_ACCOUNT_NOT_FOUND(-1302,"Tài khoản ngân hàng không tồn tại", HttpStatus.BAD_REQUEST),
    BANK_CREDENTIAL_NOT_FOUND(-1303,"Xác thực tài khoản ngân hàng không tồn tại", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(-1304,"Không tìm thấy địa chỉ email", HttpStatus.BAD_REQUEST),
    OTP_NOT_FOUND(-1305,"OTP không tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(-1306,"Người dùng không tồn tại", HttpStatus.BAD_REQUEST),
    NOTIFICATION_NOT_FOUND(-1307,"Thông báo không tồn tại", HttpStatus.BAD_REQUEST),
    ADMIN_NOT_FOUND(-1308,"Không tìm thấy tài khoản admin", HttpStatus.BAD_REQUEST),
    JOB_TYPE_NOT_FOUND(-1309,"Không tìm thấy loại dịch vụ", HttpStatus.BAD_REQUEST),
    POST_NOT_FOUND(-1310, "Không tìm thấy bài đăng công việc", HttpStatus.BAD_REQUEST),
    TAKE_POST_NOT_FOUND(-1311, "Freelancer chưa từng nhận công việc này", HttpStatus.BAD_REQUEST),
    PROVINCE_NOT_FOUND(-1312, "Mã tỉnh không tồn tại", HttpStatus.BAD_REQUEST),
    DISTRICT_NOT_FOUND(-1313, "Mã quận không tồn tại", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_FOUND(-1314, "Địa chỉ không tồn tại", HttpStatus.BAD_REQUEST),

    EXPIRED_OTP(-1400,"OTP hết hạn", HttpStatus.BAD_REQUEST),
    NOT_VERIFY(-1401, "Tài khoản ngân hàng không hợp lệ", HttpStatus.BAD_REQUEST),
    TRANSFER_FAIL(-1402, "Chuyển tiền thất bại", HttpStatus.BAD_REQUEST),
    RATE_LIMIT(-1403, "Giới hạn truy cập", HttpStatus.BAD_REQUEST),
    BANK_ACCOUNT_LINKED(-1404, "Tài khoản này đang được liên kết", HttpStatus.BAD_REQUEST),
    EMAIL_SIGN_UP(-1405, "Email đang được đăng ký bởi 1 người dùng khác", HttpStatus.BAD_REQUEST),
    POST_FULL_FREELANCER(-1406, "Đã đủ Freelancer nhận công việc", HttpStatus.BAD_REQUEST),

    FAIL_GET_USER_INFO(-1500, "Lấy thông tin người dùng thất bại", HttpStatus.BAD_REQUEST),
    FAIL_FEE_TRANSFER(-1501, "Chuyển phí thất bại", HttpStatus.BAD_REQUEST),
    FAIL_GRANT_REMOVE(-1502, "Grant remove thất bại", HttpStatus.BAD_REQUEST),

    MISSING_HOUSE_CLEANING(-1600, "Thiếu tham số House Cleaning", HttpStatus.BAD_REQUEST),
    MISSING_BABYSITTING(-1601, "Thiếu tham số Babysitting", HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    StatusCode(int code, String message, HttpStatusCode httpStatusCode){
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
