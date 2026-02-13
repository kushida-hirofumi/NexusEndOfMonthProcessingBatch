package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessingSubAdjustmentAmountEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEndOfProcessingSubAdjustmentAmountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 月末処理の給与計算の調整額テーブルのサービス
 */
@Service
public class NexusEndOfProcessingSubAdjustmentAmountService {

    @Autowired
    NexusEndOfProcessingSubAdjustmentAmountMapper mapper;

    public int insert(NexusEndOfProcessingSubAdjustmentAmountEntity entity) {
        if(entity==null) return 0;
        return mapper.insert(entity);
    }

    public void deleteByParentId(int parentId) {
        mapper.deleteByParentId(parentId);
    }

    public NexusEndOfProcessingSubAdjustmentAmountEntity findByParentId(int parentId) {
        return mapper.findByParentId(parentId);
    }

    public List<NexusEndOfProcessingSubAdjustmentAmountEntity> findByParentIds(List<Integer> parentIds) {
        if(parentIds.isEmpty()) return new ArrayList<>();
        return mapper.findByParentIds(parentIds);
    }

}