package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02SubAdjustmentAmountEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEndOfMonthProcessingSheet02SubAdjustmentAmountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 月処理キット情報のサービス
 * 分類：月末処理シート
 * 調整額
 */
@Service
public class NexusEndOfMonthProcessingSheet02SubAdjustmentAmountService {

    @Autowired
    NexusEndOfMonthProcessingSheet02SubAdjustmentAmountMapper mapper;

    public int insert(NexusEndOfMonthProcessingSheet02SubAdjustmentAmountEntity entity) {
        if(entity==null) return 0;
        return mapper.insert(entity);
    }

    public int update(NexusEndOfMonthProcessingSheet02SubAdjustmentAmountEntity entity) {
        if(entity==null) return 0;
        return mapper.update(entity);
    }

    public void deleteByParentId(int parentId) {
        mapper.deleteByParentId(parentId);
    }

    public NexusEndOfMonthProcessingSheet02SubAdjustmentAmountEntity findByParentId(int parentId) {
        return mapper.findByParentId(parentId);
    }

    public List<NexusEndOfMonthProcessingSheet02SubAdjustmentAmountEntity> findByParentIds(List<Integer> parentIds) {
        if(parentIds==null || parentIds.isEmpty()) return new ArrayList<>();
        return mapper.findByParentIds(parentIds);
    }

}