package com.pray.entity.dto;


import java.io.Serializable;

/**
 * <p>
 * ToString
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/5/26 9:56
 */
public  abstract class ToString implements Serializable {

    public ToString() {

    }

    /**
     * 将对象转化为字符串
     * @param obj
     * @return
     */
    public String toString(Object obj) {
        return obj == null ? "null" : obj.toString();
    }
}
