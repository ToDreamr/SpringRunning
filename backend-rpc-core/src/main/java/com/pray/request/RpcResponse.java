package com.pray.request;

import lombok.*;

import java.io.Serializable;

/**
 * RpcResponse
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 15:11
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse implements Serializable {
    /**
     * 响应数据
     */
    private Object data;

    /**
     * 响应数据类型(预留)
     */
    private Class<?> dataType;

    /**
     * 响应信息
     */
    private String message;


    /**
     * 异常信息
     */
    private Exception exception;
}
