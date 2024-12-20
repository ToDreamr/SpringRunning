package com.pray.rbac.type;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * RoleEnabledTypeHandler
 *
 * @author 九歌天上有
 * @since 2024/12/20 下午2:25
 */
public class RoleEnabledTypeHandler implements TypeHandler<RoleEnabled> {

    private final Map<Integer, RoleEnabled> enabledMap = new HashMap<>();

    public RoleEnabledTypeHandler() {
        for (RoleEnabled enabled : RoleEnabled.values()) {
            enabledMap.put(enabled.getValue(), enabled);
        }
    }

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, RoleEnabled enabled, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, enabled.getValue());
    }

    @Override
    public RoleEnabled getResult(ResultSet resultSet, String s) throws SQLException {
        Integer value = resultSet.getInt(s);
        return enabledMap.get(value);
    }

    @Override
    public RoleEnabled getResult(ResultSet resultSet, int i) throws SQLException {
        Integer value = resultSet.getInt(i);
        return enabledMap.get(value);
    }

    @Override
    public RoleEnabled getResult(CallableStatement callableStatement, int i) throws SQLException {
        Integer value = callableStatement.getInt(i);
        return enabledMap.get(value);
    }

}
