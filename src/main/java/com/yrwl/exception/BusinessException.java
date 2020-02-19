package com.yrwl.exception;

import lombok.Getter;

/**
 * @author shentao
 * @date  2019-12-13
 */
@Getter
public class BusinessException extends RuntimeException {

    protected ErrorCode errorCode;

    protected String code;

    protected String msg;

    protected BusinessException() {
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getErrorMsg());
        this.errorCode = errorCode;
    }

    public BusinessException(String code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public static BusinessException withErrorCode(String msg) {

        return withErrorCode("0", msg);
    }

    public static BusinessException withErrorCode(ErrorCode errorCode) {

        return new BusinessException(errorCode);
    }

    public static BusinessException withErrorCode(String code, String msg) {

        return new BusinessException(code, msg);
    }
}
