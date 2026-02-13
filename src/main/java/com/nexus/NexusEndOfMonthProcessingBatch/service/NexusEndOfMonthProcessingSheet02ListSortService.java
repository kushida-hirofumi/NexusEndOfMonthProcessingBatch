package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02ListSortEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEndOfMonthProcessingSheet02ListSortMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 月末処理(月末処理シート)で利用する一覧ソート用のテーブル
 */
@Service
public class NexusEndOfMonthProcessingSheet02ListSortService {

    @Autowired
    NexusEndOfMonthProcessingSheet02ListSortMapper mapper;

    public int insertList(List<NexusEndOfMonthProcessingSheet02ListSortEntity> entities) {
        return mapper.insertList(entities);
    }

}