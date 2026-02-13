package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom;

import lombok.Builder;
import lombok.Getter;

/**
 * 出金依頼情報
 */
@Builder
@Getter
public class WithdrawalRequestDetails {
    //出金額
    String withdrawalAmount;
    //備考1
    String note1;
    //請求額
    String invoiceAmount;
    //適用先
    String appliedTo;
    //入金予定日
    String expectedDepositDate;
    //作成者
    String createdBy;
    //その他
    String other;
}