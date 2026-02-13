package com.nexus.NexusEndOfMonthProcessingBatch.logic.invoice_pattern_definition;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.InvoicePatternDefinitionConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 請求書のパターン定義判定処理で利用する会社情報の管理用
 */
@AllArgsConstructor
@Getter
public class InvoicePatternDefinitionCompany {
    Integer companyId;
    String companyName;
    InvoicePatternDefinitionConstant.CompanyPatternEnum companyPatternEnum;
}