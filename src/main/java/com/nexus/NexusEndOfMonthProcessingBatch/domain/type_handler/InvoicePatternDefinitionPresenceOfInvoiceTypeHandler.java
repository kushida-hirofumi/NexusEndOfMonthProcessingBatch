package com.nexus.NexusEndOfMonthProcessingBatch.domain.type_handler;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.InvoicePatternDefinitionConstant;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoicePatternDefinitionPresenceOfInvoiceTypeHandler extends BaseTypeHandler<InvoicePatternDefinitionConstant.PresenceOfInvoiceEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, InvoicePatternDefinitionConstant.PresenceOfInvoiceEnum parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public InvoicePatternDefinitionConstant.PresenceOfInvoiceEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return InvoicePatternDefinitionConstant.PresenceOfInvoiceEnum.valueOfName(rs.getString(columnName));
    }

    @Override
    public InvoicePatternDefinitionConstant.PresenceOfInvoiceEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return InvoicePatternDefinitionConstant.PresenceOfInvoiceEnum.valueOfName(rs.getString(columnIndex));
    }

    @Override
    public InvoicePatternDefinitionConstant.PresenceOfInvoiceEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return InvoicePatternDefinitionConstant.PresenceOfInvoiceEnum.valueOfName(cs.getString(columnIndex));
    }
}