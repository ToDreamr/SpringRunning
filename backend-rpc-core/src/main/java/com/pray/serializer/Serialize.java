package com.pray.serializer;

import java.io.IOException;

/**
 * Serialize
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 16:51
 */
public interface Serialize {
    <T> byte[] serialize(T obj) throws IOException;

    <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException;
}
