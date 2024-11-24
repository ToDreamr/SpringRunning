package com.pray.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.curator.shaded.com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Test
 *
 * @author Cotton Eye Joe
 * @since 2024/11/24 0:45
 */
public class Test {
    private static Map<String, Object> createData() {
        // 创建要写入的数据
        // ...
        Map<String,Object> unmodifiedMap = Map.of("a","dd","er","ds");
        return unmodifiedMap;
    }
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        // 读取 YAML 文件
        try {
            Map<String, Object> data = mapper.readValue(Resources.getResource("application.yml"), Map.class);
            // 处理 YAML 数据
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 写入 YAML 文件
        try {
            Map<String, Object> data = createData();
            mapper.writeValue(new File("D:\\SpringRunning\\backend-rpc-core\\src\\main\\resources\\output.yaml"), data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
