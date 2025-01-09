package com.ruoyi.common.config.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(String[].class)
public class StringArrayHandler extends BaseTypeHandler<String[]> {
    /**
     * 增删改
     *
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, String.join(",", parameter));
    }

    /**
     * Gets the nullable result.
     * 以下都是查询
     *
     * @param rs         the rs
     * @param columnName Column name, when configuration <code>useColumnLabel</code> is <code>false</code>
     * @return the nullable result
     * @throws SQLException the SQL exception
     */
    @Override
    public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (Objects.isNull(rs.getString(columnName))) {
            return new String[]{};
        }
        return rs.getString(columnName).split(",");
    }

    @Override
    public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (Objects.isNull(rs.getString(columnIndex))) {
            return new String[]{};
        }
        return rs.getString(columnIndex).split(",");
    }

    @Override
    public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (Objects.isNull(cs.getString(columnIndex))) {
            return new String[]{};
        }
        return cs.getString(columnIndex).split(",");
    }
}
