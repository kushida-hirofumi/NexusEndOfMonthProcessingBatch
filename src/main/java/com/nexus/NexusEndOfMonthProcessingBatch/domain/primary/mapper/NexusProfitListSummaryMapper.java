package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusProfitListSummaryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 利益一覧情報を管理する
 */
@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusProfitListSummaryMapper {
    int insert(NexusProfitListSummaryEntity entity);
    List<NexusProfitListSummaryEntity> findAll();
}