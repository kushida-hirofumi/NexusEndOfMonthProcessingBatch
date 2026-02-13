package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessInvoiceEntrySubEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 月末処理の請求書サブテーブルのマッパー
 */
@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusEndOfProcessInvoiceEntrySubMapper {
    void insert(@Param("entities") List<NexusEndOfProcessInvoiceEntrySubEntity> entities);
    void deleteByParentId(@Param("parentId") int parentId);
    List<NexusEndOfProcessInvoiceEntrySubEntity> findByParentId(@Param("parentId") int parentId);
    List<NexusEndOfProcessInvoiceEntrySubEntity> findByParentIds(@Param("parentIds") List<Integer> parentIds);
}