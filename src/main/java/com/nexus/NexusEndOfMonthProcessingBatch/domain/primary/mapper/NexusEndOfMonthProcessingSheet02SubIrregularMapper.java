package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02SubIrregularEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 月処理キット情報のマッパー
 * 分類：月末処理シート
 * イレギュラー入力
 */
@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusEndOfMonthProcessingSheet02SubIrregularMapper {
    int insertList(@Param("entities") List<NexusEndOfMonthProcessingSheet02SubIrregularEntity> entities);
    void deleteByParentId(@Param("parentId") int parentId);
    void deleteByParentIdAndIndexGreaterThanEqual(@Param("parentId") int parentId, @Param("index") int index);
    long countByParentId(@Param("parentId") int parentId);
    List<NexusEndOfMonthProcessingSheet02SubIrregularEntity> findByParentIds(@Param("parentIds") List<Integer> parentIds);
}