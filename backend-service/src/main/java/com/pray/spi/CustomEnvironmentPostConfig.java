package com.pray.spi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Order(0)
public class CustomEnvironmentPostConfig implements EnvironmentPostProcessor { //EnvironmentPostProcessor含有获取系统运行的相关参数

    //静态字段，无法使用Spring注入，后续使用文件内容？？？
    public static final String TABLE_NAME = "tb_sys";
    public static final String SQL = "select * from `spring_runner`.tb_sys_conf";

    public static final String sqlPath = "";
    private static final String DATABASE = "spring_runner";
    private static final Logger log = LoggerFactory.getLogger(CustomEnvironmentPostConfig.class);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.debug("加载系统默认配置");
        try {
            Map<String, Object> map = new HashMap<>();

            String username = environment.getProperty("spring.datasource.username");
            String password = environment.getProperty("spring.datasource.password");
            String url = environment.getProperty("spring.datasource.url");
            String driver = environment.getProperty("spring.datasource.driver-class-name");
            Class.forName(driver);
            assert url != null;
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                //初始化数据库
                initDb(connection);
                //加载配置文件
                try (Statement statement = connection.createStatement()) {
                    try (ResultSet resultSet = statement.executeQuery(SQL)) {
                        while (resultSet.next()) {
                            map.put(resultSet.getString("config_key"), resultSet.getString("config_value"));
                        }
                    }
                }
            }

            MutablePropertySources propertySources = environment.getPropertySources();
            PropertySource<?> source = new MapPropertySource(TABLE_NAME, map);
            propertySources.addFirst(source);
            log.debug("默认配置加载完成");
        } catch (Exception e) {
            log.error("系统配置加载失败",e);
            // 输出异常堆栈信息到控制台（在开发、测试阶段方便查看，生产环境可根据需求决定是否保留）
            e.printStackTrace();
            //todo loadWithDefaultConfig()
        }
    }

    private void initDb(Connection connection) throws SQLException,IOException{
        try (Statement statement = connection.createStatement()){
            try (ResultSet set = statement.executeQuery("Show databases like'"+DATABASE+"'")){
                if (!set.next()){
                    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
                    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                    populator.addScripts(resolver.getResources(sqlPath));
                    populator.populate(connection);
                }
            }
        }
    }
}
