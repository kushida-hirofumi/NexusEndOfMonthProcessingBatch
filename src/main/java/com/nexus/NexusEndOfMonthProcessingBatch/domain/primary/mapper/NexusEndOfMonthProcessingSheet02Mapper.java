package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02Entity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 月処理キット情報のマッパー
 * 分類：月末処理シート
 */
@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusEndOfMonthProcessingSheet02Mapper {
    int insert(NexusEndOfMonthProcessingSheet02Entity entity);
    int update(NexusEndOfMonthProcessingSheet02Entity entity);
    List<NexusEndOfMonthProcessingSheet02Entity> findAll();
    List<NexusEndOfMonthProcessingSheet02Entity> findByEmployeeIdsAndWorkingDate(@Param("employeeIds") List<Integer> employeeIds, @Param("workingDate") LocalDateTime workingDate);
}