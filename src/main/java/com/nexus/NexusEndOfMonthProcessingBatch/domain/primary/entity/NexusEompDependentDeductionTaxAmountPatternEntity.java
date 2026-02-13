package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import lombok.Data;

/**
 * 月末処理における扶養控除の税額パターン情報
 */
@Data
public class NexusEompDependentDeductionTaxAmountPatternEntity {
    //保険料等控除後の給与等の金額
    double salaryAmountAfterInsurancePremiumDeductions;
    //扶養親族等の人数
    int numberOfDependents;
    //税額
    double taxAmount;
}