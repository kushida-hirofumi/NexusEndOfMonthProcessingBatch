package com.nexus.NexusEndOfMonthProcessingBatch.domain.type_handler;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.InvoicePatternDefinitionConstant;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoicePatternDefinitionCompanyPatternTypeHandler extends BaseTypeHandler<InvoicePatternDefinitionConstant .CompanyPatternEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, InvoicePatternDefinitionConstant .CompanyPatternEnum parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public InvoicePatternDefinitionConstant .CompanyPatternEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return InvoicePatternDefinitionConstant .CompanyPatternEnum.valueOfPattern(rs.getString(columnName));
    }

    @Override
    public InvoicePatternDefinitionConstant .CompanyPatternEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return InvoicePatternDefinitionConstant .CompanyPatternEnum.valueOfPattern(rs.getString(columnIndex));
    }

    @Override
    public InvoicePatternDefinitionConstant .CompanyPatternEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return InvoicePatternDefinitionConstant .CompanyPatternEnum.valueOfPattern(cs.getString(columnIndex));
    }
}