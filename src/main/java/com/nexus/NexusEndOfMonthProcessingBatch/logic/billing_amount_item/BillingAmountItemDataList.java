package com.nexus.NexusEndOfMonthProcessingBatch.logic.billing_amount_item;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 *  請求金額項目情報のリスト管理用
 */
@Getter
public class BillingAmountItemDataList {

    final List<BillingAmountItemData> nodeList = new ArrayList<>();
    //金額の合計
    int total = 0;

}