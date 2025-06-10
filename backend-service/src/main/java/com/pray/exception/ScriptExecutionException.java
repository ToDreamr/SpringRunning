package com.pray.exception;


/**
 * <p>
 * ScriptExecutionException (Rain)
 * <p>
 *
 * @author 花行
 * @since 2025/6/10 20:54
 */
public class ScriptExecutionException extends RuntimeException {

    public ScriptExecutionException(String message) {
        super(message);
    }
    public ScriptExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
