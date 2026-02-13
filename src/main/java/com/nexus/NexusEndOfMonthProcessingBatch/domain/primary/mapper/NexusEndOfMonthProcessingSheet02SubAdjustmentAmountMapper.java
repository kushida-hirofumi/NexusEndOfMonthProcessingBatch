package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02SubAdjustmentAmountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 月処理キット情報のマッパー
 * 分類：月末処理シート
 * 調整額
 */
@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusEndOfMonthProcessingSheet02SubAdjustmentAmountMapper {
    int insert(NexusEndOfMonthProcessingSheet02SubAdjustmentAmountEntity entity);
    int update(NexusEndOfMonthProcessingSheet02SubAdjustmentAmountEntity entity);
    void deleteByParentId(@Param("parentId") int parentId);
    NexusEndOfMonthProcessingSheet02SubAdjustmentAmountEntity findByParentId(@Param("parentId") int parentId);
    List<NexusEndOfMonthProcessingSheet02SubAdjustmentAmountEntity> findByParentIds(@Param("parentIds") List<Integer> parentIds);
}