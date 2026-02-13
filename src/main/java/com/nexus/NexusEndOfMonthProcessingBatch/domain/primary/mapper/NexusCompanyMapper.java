package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusCompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  会社情報テーブルのマッパー
 */
@Mapper
public interface NexusCompanyMapper {

    NexusCompanyEntity findById(@Param("id") int id);

    List<NexusCompanyEntity> findByCompanyName(@Param("companyName") String companyName);

}