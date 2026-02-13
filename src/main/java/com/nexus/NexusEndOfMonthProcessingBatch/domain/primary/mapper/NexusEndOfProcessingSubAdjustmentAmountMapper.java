package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessingSubAdjustmentAmountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 月末処理の給与計算の調整額テーブルのマッパー
 */
@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusEndOfProcessingSubAdjustmentAmountMapper {
    int insert(NexusEndOfProcessingSubAdjustmentAmountEntity entity);
    void deleteByParentId(@Param("parentId") int parentId);
    NexusEndOfProcessingSubAdjustmentAmountEntity findByParentId(@Param("parentId") int parentId);
    List<NexusEndOfProcessingSubAdjustmentAmountEntity> findByParentIds(@Param("parentIds") List<Integer> parentIds);
}