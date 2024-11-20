package com.pray.func;

/**
 * InterfaceSingleMethod
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 0:02
 */
public interface InterfaceSingleMethod<T> {
    void customize(T t);


    static <T> InterfaceSingleMethod<T> defaultImplement() {
        return (t) -> {
        };
    }
}
