package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.MstCompaniesThatOutputProfitInformationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 利益情報を出力する会社情報のマッパー
 */
@Mapper
public interface MstCompaniesThatOutputProfitInformationMapper {
    List<MstCompaniesThatOutputProfitInformationEntity> findAll();
}