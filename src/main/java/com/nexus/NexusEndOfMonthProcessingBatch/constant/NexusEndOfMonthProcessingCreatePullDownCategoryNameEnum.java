package com.nexus.NexusEndOfMonthProcessingBatch.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DBに登録される月末処理のプルダウンテーブルの分類のEnum
 */
@AllArgsConstructor
@Getter
public enum NexusEndOfMonthProcessingCreatePullDownCategoryNameEnum {
    INVOICE_COMPANY("請求書会社検索"),
    ENGINEER_COMPANY_NAME("EG所属会社検索");
    final String name;
}