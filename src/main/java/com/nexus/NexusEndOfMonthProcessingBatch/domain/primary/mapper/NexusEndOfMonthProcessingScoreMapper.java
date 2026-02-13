package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessing01Entity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusEndOfMonthProcessingScoreMapper {
    void deleteScoreById(@Param("id") Integer id);

    void insert(NexusEndOfMonthProcessing01Entity entity);

    List<NexusEndOfMonthProcessing01Entity> findByTransferDueDate(@Param("workingDate") LocalDate workingDate);
}