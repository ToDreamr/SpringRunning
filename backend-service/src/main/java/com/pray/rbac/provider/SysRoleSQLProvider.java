package com.pray.rbac.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.stereotype.Component;

/**
 * SysRoleSQLProvider
 *
 * @author 九歌天上有
 * @since 2024/12/18 上午1:10
 */
@Deprecated
public class SysRoleSQLProvider implements SqlProvider {
    private String sql;

    public SQL selectByProvidedSQL(final Long id) {
        SQL tbSysRole = new SQL() {
            {
                SELECT(" id,role_name,enabled," +
                        "create_by,create_time");
                FROM("tb_sys_role");
                WHERE("id = #{id,jdbcType=BIGINT}");
            }
        };
        sql = tbSysRole.toString();
        setSql(sql);
        return tbSysRole;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * @return
     */
    @Override
    public String getSql() {
        return this.sql;
    }
}
