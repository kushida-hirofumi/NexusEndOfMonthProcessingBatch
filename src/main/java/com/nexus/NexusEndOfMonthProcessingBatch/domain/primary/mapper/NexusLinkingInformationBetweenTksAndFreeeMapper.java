package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusLinkingInformationBetweenTksAndFreeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * マッパー
 * 【テーブル】TKSとFreee会計の情報の紐付を行うテーブル
 */
@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusLinkingInformationBetweenTksAndFreeeMapper {
    int insertList(@Param("entities") List<NexusLinkingInformationBetweenTksAndFreeeEntity> entityList);
    List<NexusLinkingInformationBetweenTksAndFreeeEntity> findByTksIds(@Param("tksIds") List<Integer> tksIds);
    List<NexusLinkingInformationBetweenTksAndFreeeEntity> findAll();
    List<NexusLinkingInformationBetweenTksAndFreeeEntity> findByFreeeIdList(List<String> freeeIdList);
}