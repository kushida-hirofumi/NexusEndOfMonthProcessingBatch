package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02ListSortEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 月末処理(月末処理シート)で利用する一覧ソート用のテーブル
 */
@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusEndOfMonthProcessingSheet02ListSortMapper {
    int insertList(@Param("entities") List<NexusEndOfMonthProcessingSheet02ListSortEntity> entities);
    void deleteByRecordId(@Param("recordId") int recordId);
}