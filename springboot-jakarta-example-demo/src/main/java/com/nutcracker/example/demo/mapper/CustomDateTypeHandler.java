package com.nutcracker.example.demo.mapper;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 自定义日期类型处理程序
 *
 * @author 胡桃夹子
 * @date 2025/03/28 15:36:31
 */
public class CustomDateTypeHandler extends BaseTypeHandler<Date> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date date, JdbcType jdbcType) throws SQLException {
        String formattedDate = DateUtil.format(date, DatePattern.NORM_DATETIME_MS_FORMATTER);
        ps.setString(i, formattedDate);
    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String dateStr = rs.getString(columnName);
        if (dateStr != null) {
            try {
                return DateUtil.parse(dateStr, DatePattern.NORM_DATETIME_MS_FORMATTER);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String dateStr = rs.getString(columnIndex);
        if (dateStr != null) {
            try {
                return DateUtil.parse(dateStr, DatePattern.NORM_DATETIME_MS_FORMATTER);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String dateStr = cs.getString(columnIndex);
        if (dateStr != null) {
            try {
                return DateUtil.parse(dateStr, DatePattern.NORM_DATETIME_MS_FORMATTER);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}