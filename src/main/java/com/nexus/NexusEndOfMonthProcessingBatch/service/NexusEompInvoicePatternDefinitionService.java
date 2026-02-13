package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEompInvoicePatternDefinitionEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEompInvoicePatternDefinitionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 月末処理における請求書パターン定義の情報
 */
@Service
public class NexusEompInvoicePatternDefinitionService {
    @Autowired
    NexusEompInvoicePatternDefinitionMapper nexusEompInvoicePatternDefinitionMapper;

    public List<NexusEompInvoicePatternDefinitionEntity> findAll() {
        return nexusEompInvoicePatternDefinitionMapper.findAll();
    }
}