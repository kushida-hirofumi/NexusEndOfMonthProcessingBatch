package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEompInvoicePatternDefinitionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 月末処理における請求書パターン定義の情報
 */
@Mapper
public interface NexusEompInvoicePatternDefinitionMapper {
    List<NexusEompInvoicePatternDefinitionEntity> findAll();
}