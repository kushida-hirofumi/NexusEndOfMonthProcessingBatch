package com.nexus.NexusEndOfMonthProcessingBatch.logic.entity_collection;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.InsuranceAndPensionTableEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02Entity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02SubIrregularEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.ExcessDeductionStatusEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.supplementary_information.EompEmployeeNameObject;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyNumberUtility;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class EompSheet2EntityCollection {
    NexusEndOfMonthProcessingSheet02Entity nexusEndOfMonthProcessingSheet02Entity;
    List<NexusEndOfMonthProcessingSheet02SubIrregularEntity> irregularEntities;
    InsuranceAndPensionTableEntity insuranceAndPensionTableEntity;
    EompEmployeeNameObject eompEmployeeNameObject;
    ExcessDeductionStatusEntity excessDeductionStatusEntity;

    /**
     * 非課税額の合計した値を出力する
     * 利用項目１：イレギュラー入力情報の非課税金額
     * 利用項目２：経費(客先請求)
     * @return  合計値　（失敗時は0）
     */
    public double getHikazei() {
        if(irregularEntities==null || nexusEndOfMonthProcessingSheet02Entity==null) return 0;

        List<NexusEndOfMonthProcessingSheet02SubIrregularEntity> nonTaxableItems =
                irregularEntities.stream()
                        .filter(node -> !node.isTaxation())
                        .collect(Collectors.toList());

        double irregularMax = nonTaxableItems.stream()
                .mapToDouble(NexusEndOfMonthProcessingSheet02SubIrregularEntity::amountOfMoney)
                .sum();

        int customerBillingExpenses = MyNumberUtility.nullCheckInt(nexusEndOfMonthProcessingSheet02Entity.getCustomerBillingExpenses());

        return irregularMax + customerBillingExpenses;
    }
}