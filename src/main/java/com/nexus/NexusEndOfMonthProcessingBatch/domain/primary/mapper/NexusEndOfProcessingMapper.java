package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessingEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 月末処理の給与計算テーブルのマッパー
 */
@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusEndOfProcessingMapper {
    int insert(NexusEndOfProcessingEntity entity);
    void deleteById(@Param("id") Integer id);
    NexusEndOfProcessingEntity findById(@Param("id") Integer id);
    List<NexusEndOfProcessingEntity> findByIds(@Param("idList") List<Integer> idList);
}