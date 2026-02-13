package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusProfitListSummaryEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusProfitListSummaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NexusProfitListSummaryService {
    @Autowired
    NexusProfitListSummaryMapper nexusProfitListSummaryMapper;

    public int insert(NexusProfitListSummaryEntity entity) {
        if(entity==null) return 0;
        return nexusProfitListSummaryMapper.insert(entity);
    }

    public List<NexusProfitListSummaryEntity> findAll() {
        return nexusProfitListSummaryMapper.findAll();
    }

}