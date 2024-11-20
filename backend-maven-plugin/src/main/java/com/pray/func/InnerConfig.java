package com.pray.func;

/**
 * InnerConfig
 *
 * @author Cotton Eye Joe
 * @since 2024/11/20 23:13
 */
@FunctionalInterface
public interface InnerConfig<T> {
    void customize(T t);
    static <T> InnerConfig<T> withDefaults() {
        return (t) -> {
        };
    }
}
