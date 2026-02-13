package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEompDependentDeductionTaxAmountPatternEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEompDependentDeductionTaxAmountPatternMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 月末処理における扶養控除の税額パターン情報
 */
@Service
public class NexusEompDependentDeductionTaxAmountPatternService {

    @Autowired
    NexusEompDependentDeductionTaxAmountPatternMapper nexusEompDependentDeductionTaxAmountPatternMapper;

    public List<NexusEompDependentDeductionTaxAmountPatternEntity> findAll() {
        return nexusEompDependentDeductionTaxAmountPatternMapper.findAll();
    }

    /**
     * 所得税抽出
     * @param dependentsNumber  扶養者数
     * @param value     給与
     * @return  所得税
     */
    public double extract(int dependentsNumber, double value) {
        List<NexusEompDependentDeductionTaxAmountPatternEntity> result = findAll().stream().filter(node->value < node.getSalaryAmountAfterInsurancePremiumDeductions()).toList();
        if(result.isEmpty()) return 0;
        NexusEompDependentDeductionTaxAmountPatternEntity entity = result.get(dependentsNumber);
        return entity!=null ? entity.getTaxAmount() : 0;
    }
}