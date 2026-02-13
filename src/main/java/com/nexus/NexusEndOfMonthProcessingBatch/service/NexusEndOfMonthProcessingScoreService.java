package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessing01Entity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessingInvoiceEntryEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEndOfMonthProcessingScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NexusEndOfMonthProcessingScoreService {
    @Autowired
    NexusEndOfMonthProcessingScoreMapper nexusEndOfMonthProcessingScoreMapper;

    @Autowired
    NexusHolidayInfoService nexusHolidayInfoService;

    public void insert(NexusEndOfProcessingInvoiceEntryEntity entity) {
        NexusEndOfMonthProcessing01Entity nexusEndOfMonthProcessing01Entity = new NexusEndOfMonthProcessing01Entity();
        nexusEndOfMonthProcessing01Entity.setRecordId(entity.getId());
        LocalDate ld = nexusHolidayInfoService.paymentDueDate(entity);
        if(ld==null) return;
        nexusEndOfMonthProcessing01Entity.setTransferDueDate(ld);
        nexusEndOfMonthProcessingScoreMapper.deleteScoreById(entity.getId());
        nexusEndOfMonthProcessingScoreMapper.insert(nexusEndOfMonthProcessing01Entity);
    }

}