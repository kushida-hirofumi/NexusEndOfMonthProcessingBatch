package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusFreeeHumanResourcesAndLaborInfoEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusFreeeHumanResourcesAndLaborInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Freeeの人事労務情報のテーブルのサービス
 */
@Service
public class NexusFreeeHumanResourcesAndLaborInfoService {

    @Autowired
    NexusFreeeHumanResourcesAndLaborInfoMapper mapper;

    public int insertList(List<NexusFreeeHumanResourcesAndLaborInfoEntity> entities) {
        if(entities == null || entities.isEmpty()) return 0;
        return mapper.insertList(entities);
    }

}