package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessingEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEndOfProcessingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 月末処理の給与計算情報を扱うサービス
 */
@Service
public class NexusEndOfProcessingService {
    @Autowired
    NexusEndOfProcessingMapper nexusEndOfProcessingMapper;

    public int insert(NexusEndOfProcessingEntity entity) {
        return nexusEndOfProcessingMapper.insert(entity);
    }

    public NexusEndOfProcessingEntity findById(Integer id) {
        return nexusEndOfProcessingMapper.findById(id);
    }

    public List<NexusEndOfProcessingEntity> findByIds(List<Integer> idList) {
        if(idList==null || idList.isEmpty()) return new ArrayList<>();
        return nexusEndOfProcessingMapper.findByIds(idList);
    }
}