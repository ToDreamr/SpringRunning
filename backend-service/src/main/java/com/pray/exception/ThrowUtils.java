package com.pray.exception;

/**
 * ThrowUtils
 * 错误抛出工具
 * @author Cotton Eye Joe
 */
public class ThrowUtils {

    /**
     * 设计一个工具类，在条件成立的时候抛出这个异常
     */
    public static void throwIf(boolean condition,RuntimeException runtimeException){
        if (condition){
            throw runtimeException;
        }
    }
    /**
     * 工具类应该设计为静态方法
     * //只通过对应的错误代号来创建业务异常
     * @param condition
     * @param errorCode
     */
    public static void throwIf(boolean condition,ErrorCode errorCode){
        throwIf(condition,new BusinessException(errorCode.getCode()));
    }
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
    public static void merelyThrow(ErrorCode errorCode, String message) {
        throw new BusinessException(errorCode, message);
    }
}
