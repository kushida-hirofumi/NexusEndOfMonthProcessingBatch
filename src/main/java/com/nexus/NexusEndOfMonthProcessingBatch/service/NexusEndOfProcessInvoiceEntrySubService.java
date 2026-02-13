package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessInvoiceEntrySubEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEndOfProcessInvoiceEntrySubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 月末処理の請求書のサブ情報を扱うサービス
 */
@Service
public class NexusEndOfProcessInvoiceEntrySubService {

    @Autowired
    NexusEndOfProcessInvoiceEntrySubMapper nexusEndOfProcessInvoiceEntrySubMapper;

    public void insert(List<NexusEndOfProcessInvoiceEntrySubEntity> entities) {
        if(entities==null || entities.isEmpty()) return;
        nexusEndOfProcessInvoiceEntrySubMapper.insert(entities);
    }

    public void deleteByParentId(int parentId) {
        nexusEndOfProcessInvoiceEntrySubMapper.deleteByParentId(parentId);
    }

    public List<NexusEndOfProcessInvoiceEntrySubEntity> findByParentId(int parentId) {
        return nexusEndOfProcessInvoiceEntrySubMapper.findByParentId(parentId);
    }

    public List<NexusEndOfProcessInvoiceEntrySubEntity> findByParentIds(List<Integer> parentIds) {
        if(parentIds==null || parentIds.isEmpty()) return new ArrayList<>();
        return nexusEndOfProcessInvoiceEntrySubMapper.findByParentIds(parentIds);
    }

}