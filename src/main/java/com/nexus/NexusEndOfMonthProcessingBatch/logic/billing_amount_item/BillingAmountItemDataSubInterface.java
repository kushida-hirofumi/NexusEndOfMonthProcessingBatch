package com.nexus.NexusEndOfMonthProcessingBatch.logic.billing_amount_item;

/**
 * 請求金額項目情報の生成に利用するエンティティのインターフェース
 * 「イレギュラー入力項目」
 */
public interface BillingAmountItemDataSubInterface {
    //課税フラグ
    boolean isTaxation();
    //金額出力
    double amountOfMoney();
    //件名
    String getSubject();
    //数量
    String getQuantity();
    //単価
    Integer getUnitPrice();
}