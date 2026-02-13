package com.nexus.NexusEndOfMonthProcessingBatch.domain.type_handler;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.InvoicePatternDefinitionConstant;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoicePatternDefinitionCompanyMarginPatternTypeHandler  extends BaseTypeHandler<InvoicePatternDefinitionConstant.CompanyMarginPatternEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, InvoicePatternDefinitionConstant.CompanyMarginPatternEnum parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public InvoicePatternDefinitionConstant.CompanyMarginPatternEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return InvoicePatternDefinitionConstant.CompanyMarginPatternEnum.valueOfType(rs.getString(columnName));
    }

    @Override
    public InvoicePatternDefinitionConstant.CompanyMarginPatternEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return InvoicePatternDefinitionConstant.CompanyMarginPatternEnum.valueOfType(rs.getString(columnIndex));
    }

    @Override
    public InvoicePatternDefinitionConstant.CompanyMarginPatternEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return InvoicePatternDefinitionConstant.CompanyMarginPatternEnum.valueOfType(cs.getString(columnIndex));
    }
}