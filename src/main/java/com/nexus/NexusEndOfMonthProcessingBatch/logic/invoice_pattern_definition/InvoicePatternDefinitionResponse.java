package com.nexus.NexusEndOfMonthProcessingBatch.logic.invoice_pattern_definition;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.InvoicePatternDefinitionConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * 請求書のパターン定義のレスポンス情報
 */
@Getter
@Setter
public class InvoicePatternDefinitionResponse {
    //請求元会社ID
    Integer billingSourceCompanyId;
    //請求元会社
    String billingSource;
    //請求先会社
    String billingDestination;
    //マージン
    InvoicePatternDefinitionConstant.MarginEnum marginEnum;
}