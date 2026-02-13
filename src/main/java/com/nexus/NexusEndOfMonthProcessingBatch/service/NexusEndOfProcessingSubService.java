package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessingSubEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEndOfProcessingSubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 月末処理の給与計算のサブ情報を扱うサービス
 */
@Service
public class NexusEndOfProcessingSubService {

    @Autowired
    NexusEndOfProcessingSubMapper nexusEndOfProcessingSubMapper;

    public void insert(List<NexusEndOfProcessingSubEntity> entities) {
        if(entities==null || entities.isEmpty()) return;
        nexusEndOfProcessingSubMapper.insert(entities);
    }

    public void deleteByParentId(int parentId) {
        nexusEndOfProcessingSubMapper.deleteByParentId(parentId);
    }

    public List<NexusEndOfProcessingSubEntity> findByParentId(int parentId) {
        return nexusEndOfProcessingSubMapper.findByParentId(parentId);
    }

    public List<NexusEndOfProcessingSubEntity> findByParentIds(List<Integer> parentIds) {
        if(parentIds==null || parentIds.isEmpty()) return new ArrayList<>();
        return nexusEndOfProcessingSubMapper.findByParentIds(parentIds);
    }

}