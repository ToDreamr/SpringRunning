package com.pray.exception;

/**
 * ErrorCode
 * 枚举类，自定义的错误异常枚举类
 * @author Cotton Eye Joe
 */
public enum ErrorCode {
    SUCCESS(200,"ok"),
    PARAMS_ERROR(40000,"请求参数异常或错误"),
    NOT_LOGIN_ERROR(40100,"未登录"),
    NO_AUTH_ERROR(40101,"未授权"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败");
    /**
     * 枚举异常的构造类
     * @param code 错误代码
     * @param message 错误信息
     */
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 状态码
     */
    private final int code;
    /**
     * 具体错误信息
     */
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
