package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessingSubEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 月末処理の給与計算サブテーブルのマッパー
 */
@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusEndOfProcessingSubMapper {
    void insert(@Param("entities") List<NexusEndOfProcessingSubEntity> entities);
    void deleteByParentId(@Param("parentId") int parentId);
    List<NexusEndOfProcessingSubEntity> findByParentId(@Param("parentId") int parentId);
    List<NexusEndOfProcessingSubEntity> findByParentIds(@Param("parentIds") List<Integer> parentIds);
}