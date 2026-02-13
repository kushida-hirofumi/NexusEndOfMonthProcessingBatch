package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusFreeeApiInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * FreeeApiで利用する情報を管理する
 */
@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusFreeeApiInfoMapper {
    int updateAccessToken(NexusFreeeApiInfoEntity entity);
    int delete(NexusFreeeApiInfoEntity entity);
    List<NexusFreeeApiInfoEntity> findAll();
    NexusFreeeApiInfoEntity findByCompanyName(@Param("companyName") String companyName);
}