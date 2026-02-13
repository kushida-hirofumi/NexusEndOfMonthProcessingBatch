package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.config.PrimaryDbConfig;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessingInvoiceEntryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 月末処理の請求書テーブルのマッパー
 */
@Mapper
@Transactional(PrimaryDbConfig.TRANSACTION_MANAGER_BEAN)
public interface NexusEndOfProcessingInvoiceEntryMapper {
    int insert(NexusEndOfProcessingInvoiceEntryEntity entity);

    void deleteById(@Param("id") Integer id);

    //月処理の利用済みの請求データ検索
    List<NexusEndOfProcessingInvoiceEntryEntity> monthlyProcessingUsedBillingSearch(@Param("employeeId") Integer employeeId, @Param("employeeName") String employeeName, @Param("workingDate")LocalDateTime workingDate);

    NexusEndOfProcessingInvoiceEntryEntity findById(@Param("id") Integer id);

    List<NexusEndOfProcessingInvoiceEntryEntity> findByIds(@Param("idList") List<Integer> idList);
}