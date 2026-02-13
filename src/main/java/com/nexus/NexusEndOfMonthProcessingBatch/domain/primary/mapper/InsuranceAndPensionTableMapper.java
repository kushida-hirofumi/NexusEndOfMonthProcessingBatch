package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.InsuranceAndPensionTableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 保険年金情報のマッパー
 */
@Mapper
public interface InsuranceAndPensionTableMapper {
    List<InsuranceAndPensionTableEntity> findByYearList(@Param("yearList") List<Integer> yearList);
}