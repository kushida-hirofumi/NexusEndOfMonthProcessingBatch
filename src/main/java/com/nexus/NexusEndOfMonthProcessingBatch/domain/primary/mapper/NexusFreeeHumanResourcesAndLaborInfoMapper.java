package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusFreeeHumanResourcesAndLaborInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Freeeの人事労務情報のテーブルのマッパー
 */
@Mapper
public interface NexusFreeeHumanResourcesAndLaborInfoMapper {
    int insertList(@Param("entities") List<NexusFreeeHumanResourcesAndLaborInfoEntity> entities);
}