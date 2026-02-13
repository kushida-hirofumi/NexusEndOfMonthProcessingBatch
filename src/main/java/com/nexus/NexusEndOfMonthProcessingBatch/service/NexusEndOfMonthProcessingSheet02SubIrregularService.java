package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02Entity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02SubIrregularEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEndOfMonthProcessingSheet02SubIrregularMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 月処理キット情報のサービス
 * 分類：月末処理シート
 * イレギュラー入力
 */
@Service
public class NexusEndOfMonthProcessingSheet02SubIrregularService {

    @Autowired
    NexusEndOfMonthProcessingSheet02SubIrregularMapper mapper;

    public int insertList(List<NexusEndOfMonthProcessingSheet02SubIrregularEntity> entities) {
        if(entities==null || entities.isEmpty()) return 0;
        return mapper.insertList(entities);
    }

    public void deleteByParentId(int parentId) {
        mapper.deleteByParentId(parentId);
    }

    public List<NexusEndOfMonthProcessingSheet02SubIrregularEntity> findByParentId(int parentId) {
        List<Integer> ids = new ArrayList<>();
        ids.add(parentId);
        return mapper.findByParentIds(ids);
    }

    public List<NexusEndOfMonthProcessingSheet02SubIrregularEntity> findByParentEntities(List<NexusEndOfMonthProcessingSheet02Entity> entities) {
        if(entities==null || entities.isEmpty()) return new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        for(NexusEndOfMonthProcessingSheet02Entity entity : entities) {
            ids.add(entity.getId());
        }
        return findByParentIds(ids);
    }

    public List<NexusEndOfMonthProcessingSheet02SubIrregularEntity> findByParentIds(List<Integer> parentIds) {
        if(parentIds==null || parentIds.isEmpty()) return new ArrayList<>();
        return mapper.findByParentIds(parentIds);
    }

}