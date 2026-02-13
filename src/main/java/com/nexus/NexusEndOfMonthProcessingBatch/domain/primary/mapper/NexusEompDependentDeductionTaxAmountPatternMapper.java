package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEompDependentDeductionTaxAmountPatternEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 月末処理における扶養控除の税額パターン情報
 */
@Mapper
public interface NexusEompDependentDeductionTaxAmountPatternMapper {
    List<NexusEompDependentDeductionTaxAmountPatternEntity> findAll();
}