package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.constant.EndOfMonthProcessingIdLinkAttributeEnum;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingIdLinkEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 月末処理のID紐付けテーブルのマッパー
 */
@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusEndOfMonthProcessingIdLinkMapper {
    NexusEndOfMonthProcessingIdLinkEntity findByAttributeAndDestId(@Param("attribute") EndOfMonthProcessingIdLinkAttributeEnum attribute, @Param("destId") int destId);
}