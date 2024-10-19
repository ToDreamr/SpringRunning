package com.pray.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * DataBaseSchemaConfig
 *
 * @author Cotton Eye Joe
 * @since 2024/10/19 21:12
 */
@ConfigurationProperties(prefix = "runner",ignoreInvalidFields = true)//检查非法参数
@Configuration
@Validated //检查参数校验
@Order(-1)
public class DataBaseSchemaConfig {
    public String schemaName;
    public String tableName;


    public List<String> tables;

    public List<String> getTables() {
        return tables;
    }
    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
