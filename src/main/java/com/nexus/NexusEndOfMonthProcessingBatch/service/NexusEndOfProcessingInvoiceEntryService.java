package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessingInvoiceEntryEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEndOfProcessingInvoiceEntryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 月末処理の請求書情報を扱うサービス
 */
@Service
public class NexusEndOfProcessingInvoiceEntryService {
    @Autowired
    NexusEndOfProcessingInvoiceEntryMapper nexusEndOfProcessingMapper;

    public int insert(NexusEndOfProcessingInvoiceEntryEntity entity) {
        return nexusEndOfProcessingMapper.insert(entity);
    }

    public NexusEndOfProcessingInvoiceEntryEntity findById(Integer id) {
        return nexusEndOfProcessingMapper.findById(id);
    }

    public List<NexusEndOfProcessingInvoiceEntryEntity> findByIds(List<Integer> idList) {
        if(idList==null || idList.isEmpty()) return new ArrayList<>();
        return nexusEndOfProcessingMapper.findByIds(idList);
    }
}