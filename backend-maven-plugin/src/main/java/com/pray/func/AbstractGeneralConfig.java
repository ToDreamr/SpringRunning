package com.pray.func;

/**
 * AbstractGeneralConfig
 *
 * @author Cotton Eye Joe
 * @since 2024/11/20 23:25
 */
public abstract class AbstractGeneralConfig<T> {
    public String[] distribution;
    public abstract GeneralConfig addConfig(InnerConfig<T> config,String[] distribution);
    private T getSelf() {
        return (T) this;
    }
}
