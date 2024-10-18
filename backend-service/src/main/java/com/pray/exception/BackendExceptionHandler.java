package com.pray.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.pray.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * <p>
 * BackendExceptionHandler
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/9/14 0:23
 */
@ControllerAdvice
public class BackendExceptionHandler {
    @ExceptionHandler(JWTDecodeException.class)
    public Result<String> run(JWTDecodeException jwtDecodeException){
        return Result.fail(jwtDecodeException.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result<String> bus(BusinessException businessException){
        return Result.fail(businessException.getMessage());
    }
}
