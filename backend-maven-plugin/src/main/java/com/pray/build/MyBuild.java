package com.pray.build;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * MyBuild
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 22:32
 */
public class MyBuild<T> {
    private final Supplier<T> constructor;
    private final List<Consumer<T>> fieldInjectConsumer = new ArrayList<>();

    public MyBuild(Supplier<T> constructor) {
        this.constructor = constructor;
    }

    public static <T> MyBuild<T> builder(Supplier<T> constructor){
        return new MyBuild<>(constructor);
    }
    public interface  Injection<T,P>{
        void accept(T t,P p);
    }
    public <P> MyBuild<T> withField(MyBuild.Injection<T,P> consumer,P p){
        Consumer<T> C = instance->consumer.accept(instance,p);
        fieldInjectConsumer.add(C);
        return this;
    }
    public T build(){
        // 调用supplier 生成类实例
        T instance = constructor.get();
        // 调用传入的setter方法，完成属性初始化
        fieldInjectConsumer.forEach(dInject -> dInject.accept(instance));
        // 返回 建造完成的类实例
        return instance;
    }
}
