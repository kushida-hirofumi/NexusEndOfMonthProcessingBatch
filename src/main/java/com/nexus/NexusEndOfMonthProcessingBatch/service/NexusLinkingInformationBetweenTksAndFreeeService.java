package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusLinkingInformationBetweenTksAndFreeeEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusLinkingInformationBetweenTksAndFreeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * サービス
 * 【テーブル】TKSとfreee会計の情報の紐付を行うテーブル
 */
@Service
public class NexusLinkingInformationBetweenTksAndFreeeService {

    @Autowired
    NexusLinkingInformationBetweenTksAndFreeeMapper mapper;

    public int insertList(List<NexusLinkingInformationBetweenTksAndFreeeEntity> entityList) {
        if(entityList==null || entityList.isEmpty()) return 0;
        return mapper.insertList(entityList);
    }

    public List<NexusLinkingInformationBetweenTksAndFreeeEntity> findByTksIds(List<Integer> tksIds) {
        if(tksIds==null || tksIds.isEmpty()) return new ArrayList<>();
        return mapper.findByTksIds(tksIds);
    }

    public List<NexusLinkingInformationBetweenTksAndFreeeEntity> findAll() {
        return mapper.findAll();
    }

    public List<NexusLinkingInformationBetweenTksAndFreeeEntity> findByFreeeIdList(List<String> freeeIdList) {
        if(freeeIdList==null || freeeIdList.isEmpty()) return new ArrayList<>();
        return mapper.findByFreeeIdList(freeeIdList);
    }
}