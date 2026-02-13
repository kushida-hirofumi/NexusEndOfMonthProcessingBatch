package com.nexus.NexusEndOfMonthProcessingBatch.domain.type_handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateのタイプハンドラー
 */
public class LocalDateTypeHandler extends BaseTypeHandler<LocalDate> {

    public static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";

    private static String localDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT));
    }

    private LocalDate parseStringToLocalDate(String str) {
        if(StringUtils.isBlank(str)) return null;
        return LocalDate.parse(str, DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, localDateToString(parameter));
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseStringToLocalDate(rs.getString(columnName));
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseStringToLocalDate(rs.getString(columnIndex));
    }

    @Override
    public LocalDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseStringToLocalDate(cs.getString(columnIndex));
    }

}