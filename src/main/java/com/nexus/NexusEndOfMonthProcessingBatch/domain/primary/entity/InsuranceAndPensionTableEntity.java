package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 保険年金情報のエンティティ
 */
@Getter
@Setter
public class InsuranceAndPensionTableEntity {
    //適用されている年
    int year;

    //消費税 %
    double consumptionTax;

    //都道府県保険料率  %
    double prefecturalInsurancePremiumRates;

    //介護保険料率    %
    double longTermCareInsurancePremiumRates;

    //厚生年金保険料率  %
    double employeePensionInsurancePremiumRates;

    //雇用保険　上
    //労働者負担の雇用保険料率
    double employeePaidEmploymentInsurancePremiumRates;

    //雇用保険　下
    //事業主負担の雇用保険料率
    double employerPaidEmploymentInsurancePremiumRates;

    //労災
    //労働保険料率
    double laborInsurancePremiumRates;

    //児童福祉手当
    //児童福祉手当拠出金料率
    double childWelfareAllowanceContributionRates;

    /**
     * 消費税計算用
     * 小数点以下は切り捨て
     * @param value     計算対象の値
     * @return  消費税
     */
    public double consumptionTaxCalculation(double value) {
        return Math.floor(value * (consumptionTax / 100));
    }

    /**
     * 小数点0以下は切り捨て
     * @return
     */
    public String zeroConsumptionTax() {
        if (consumptionTax % 1 == 0) {
            return "" + (int) consumptionTax; // または long でもOK
        } else {
            return "" + consumptionTax;
        }
    }
}