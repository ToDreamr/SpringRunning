package com.pray.exception;

/**
 * BusinessException
 *
 * @author Cotton Eye Joe
 * 2024/9/3 14:29
 */
public class BusinessException extends RuntimeException{
    /**
     * 错误码
     */
    private final int code;


    public BusinessException(int code,String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(int code) {
        this.code = code;
    }
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
