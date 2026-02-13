package com.nexus.NexusEndOfMonthProcessingBatch.domain.type_handler;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.EndOfMonthProcessingIdLinkAttributeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EndOfMonthProcessingIdLinkAttributeTypeHandler extends BaseTypeHandler<EndOfMonthProcessingIdLinkAttributeEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EndOfMonthProcessingIdLinkAttributeEnum parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public EndOfMonthProcessingIdLinkAttributeEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return EndOfMonthProcessingIdLinkAttributeEnum.valueOfName(rs.getString(columnName));
    }

    @Override
    public EndOfMonthProcessingIdLinkAttributeEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return EndOfMonthProcessingIdLinkAttributeEnum.valueOfName(rs.getString(columnIndex));
    }

    @Override
    public EndOfMonthProcessingIdLinkAttributeEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return EndOfMonthProcessingIdLinkAttributeEnum.valueOfName(cs.getString(columnIndex));
    }
}