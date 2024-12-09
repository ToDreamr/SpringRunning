package com.pray.util;


import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.core.io.resource.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * LoadEnvUtil
 *
 * @author Cotton Eye Joe
 * @since 2024/11/23 16:01
 */
public class LoadEnvUtil {
    //TODO 完成不同环境下面的配置文件隔离
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        loadAllEnv(properties,DEFAULT_FILE_NAME);
        properties.entrySet().forEach(System.out::println);
        yamlMapConfig.entrySet().forEach(System.out::println);
        ymlMapConfig.entrySet().forEach(System.out::println);
    }
    private static String DEFAULT_FILE_NAME = "application";

    private final static ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
    public static void setDefaultFileName(String defaultFileName) {
        DEFAULT_FILE_NAME = defaultFileName;
    }
    private static Map<String, Object> ymlMapConfig = new HashMap<>();
    private static Map<String, Object> yamlMapConfig = new HashMap<>();

    public LoadEnvUtil() {
    }
    public static void loadAllEnvFile(Properties pro) throws IOException {
        loadAllEnv(pro,DEFAULT_FILE_NAME);
    }

    /**
     * 加载所有的配置信息
     * @param pro
     * @param fileName
     * @throws IOException
     */
    private static void loadAllEnv(Properties pro, String fileName) throws IOException {
        loadAllEnv(pro,fileName,(String)null);
    }

    /**
     * 加载所有的配置文件
     * @param pro
     * @param fileName
     * @param includeName
     * @throws IOException
     */
    private static void loadAllEnv(Properties pro, String fileName, String includeName) throws IOException {
        //文件后缀名
        String[] envSuffixes = new String[]{"yaml", "yml", "properties"};
        String[] midSub = envSuffixes;

        for (int i = 0; i < envSuffixes.length; i++) {
            String envSuffix = midSub[i];
            //文件资源
            List<Resource> resources = new ArrayList<>();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String exactFileName = fileName+"."+envSuffix;

            try {
                ClassPathResource classPathResource = new ClassPathResource(exactFileName,classLoader);
                //加载进入所有的配置文件
                resources.add(classPathResource);

                if ("properties".equals(envSuffix)){
                    loadProRes((Resource[])resources.toArray(new Resource[0]),pro);
                }else{
                    loadYmlRes((Resource[])resources.toArray(new Resource[0]),envSuffix);
                }
            }catch (NoResourceException e){
                //没找到对应的配置文件，啥也不用干
            }
        }
    }

    @SuppressWarnings("uncheck")
    private static void loadYmlRes(Resource[] resources,String envSuffix) throws IOException {
        if (resources != null && resources.length > 0) {
            Resource[] param = resources;
            for (int i = 0; i < param.length; i++) {
                Resource currentRes = resources[i];
                if(currentRes.getName()!=null){
                    InputStream ins = currentRes.getStream();
                    Map<String, Object> data = yamlMapper.readValue(ins, Map.class);
                    if (envSuffix.equals("yaml")){
                        yamlMapConfig.putAll(data);
                    }
                    ymlMapConfig.putAll(data);
                }
            }
        }
    }

    private static void loadProRes(Resource[] resources,Properties properties) throws IOException {
        if (resources != null && resources.length > 0) {
            Resource[] param = resources;
            for (int i = 0; i < param.length; i++) {
                Resource currentRes = resources[i];
                if(currentRes.getName()!=null){
                    Properties props = new Properties();//继承自Hashtable<Object,Object>
                    fillProperties(props, currentRes);
                    properties.putAll(props);//加入解析好的配置属性
                }
            }
        }
    }

    private static void fillProperties(Properties props, Resource currentRes) throws IOException {
        //填充元素
        InputStream stream = currentRes.getStream();
        //可能会产生的异常
        Throwable throwable = null;

        try {
            String fileName = currentRes.getName();
            if (fileName!=null&&fileName.endsWith(".xml")){
                throw new UnsupportedOperationException("XML resolve is not supported!");
            }else {
                props.load(stream);
            }
        }catch (Throwable t){
            throwable=t;
            throw t;
        }finally {
            if (stream!=null){
                //合并抛出
                if (throwable!=null){
                    try {
                        stream.close();
                    }catch (Throwable close){
                        throwable.addSuppressed(close);
                    }
                }
            }
        }
    }
}
