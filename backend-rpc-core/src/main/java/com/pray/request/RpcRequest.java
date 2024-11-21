package com.pray.request;

import com.pray.constants.RpcConstant;
import lombok.*;

import java.io.Serializable;

/**
 * RpcRequest
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 15:02
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RpcRequest implements Serializable {
    //需要具有服务名信息和方法名来请求
    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数类型列表
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数列表
     */
    private Object[] args;

    /**
     * 服务版本
     */
    private String serviceVersion = RpcConstant.DEFAULT_SERVICE_VERSION;

}
