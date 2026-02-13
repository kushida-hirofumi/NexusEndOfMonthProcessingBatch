package com.nexus.NexusEndOfMonthProcessingBatch.logic.billing_amount_item;

import java.time.LocalDateTime;

/**
 * 請求金額項目情報の生成に利用するエンティティのインターフェース
 */
public interface BillingAmountItemDataInterface {
    //作業日付
    LocalDateTime getWorkingDate();
    //数量
    String getQuantity();
    //単価
    Integer getUnitPrice();
    //合計金額
    double maximumPrice();
}