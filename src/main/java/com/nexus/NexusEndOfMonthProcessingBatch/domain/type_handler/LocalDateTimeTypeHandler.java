package com.nexus.NexusEndOfMonthProcessingBatch.domain.type_handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * LocalDateTimeクラスのタイプハンドラー
 */
public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

    public static final String LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public String localDateTimeToString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT, Locale.JAPANESE));
    }

    private LocalDateTime parseStringToLocalDateTime(String str) {
        if(StringUtils.isBlank(str)) return null;
        try {
            return LocalDateTime.ofInstant(new SimpleDateFormat(LOCAL_DATE_TIME_FORMAT).parse(str).toInstant(), ZoneId.systemDefault());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, localDateTimeToString(parameter));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseStringToLocalDateTime(rs.getString(columnName));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseStringToLocalDateTime(rs.getString(columnIndex));
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseStringToLocalDateTime(cs.getString(columnIndex));
    }

}