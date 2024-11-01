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

    USERNAME_EXISTED(-1200,"Tên người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(-1201,"Địa chỉ email đã tồn tại tài khoản", HttpStatus.BAD_REQUEST),
    OTP_EXISTED(-1202,"Otp đã được sử dụng", HttpStatus.BAD_REQUEST),
    BANK_ACCOUNT_EXISTED(-1203,"Tài khoản ngân hàng đã tồn tại", HttpStatus.BAD_REQUEST),

    BANK_NOT_FOUND(-1301,"Ngân hàng không tồn tại", HttpStatus.BAD_REQUEST),
    BANK_ACCOUNT_NOT_FOUND(-1302,"Tài khoản ngân hàng không tồn tại", HttpStatus.BAD_REQUEST),
    BANK_CREDENTIAL_NOT_FOUND(-1303,"Xác thực tài khoản ngân hàng không tồn tại", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(-1304,"Không tìm thấy địa chỉ email", HttpStatus.BAD_REQUEST),
    OTP_NOT_FOUND(-1305,"OTP không tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(-1306,"Người dùng không tồn tại", HttpStatus.BAD_REQUEST),
    NOTIFICATION_NOT_FOUND(-1307,"Thông báo không tồn tại", HttpStatus.BAD_REQUEST),
    ADMIN_NOT_FOUND(-1308,"Không tìm thấy tài khoản admin", HttpStatus.BAD_REQUEST),

    EXPIRED_OTP(-1400,"OTP hết hạn", HttpStatus.BAD_REQUEST),
    NOT_VERIFY(-1401, "Tài khoản ngân hàng không hợp lệ", HttpStatus.BAD_REQUEST),
    TRANSFER_FAIL(-1402, "Chuyển tiền thất bại", HttpStatus.BAD_REQUEST),
    RATE_LIMIT(-1403, "Giới hạn truy cập", HttpStatus.BAD_REQUEST),
    BANK_ACCOUNT_LINKED(-1404, "Tài khoản này đang được liên kết", HttpStatus.BAD_REQUEST),
    EMAIL_SIGN_UP(-140, "Email đang được đăng ký bởi 1 người dùng khác", HttpStatus.BAD_REQUEST),

    FAIL_GET_USER_INFO(-1500, "Lấy thông tin người dùng thất bại", HttpStatus.BAD_REQUEST),
    FAIL_FEE_TRANSFER(-1501, "Chuyển phí thất bại", HttpStatus.BAD_REQUEST),
    FAIL_GRANT_REMOVE(-1502, "Grant remove thất bại", HttpStatus.BAD_REQUEST),
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
