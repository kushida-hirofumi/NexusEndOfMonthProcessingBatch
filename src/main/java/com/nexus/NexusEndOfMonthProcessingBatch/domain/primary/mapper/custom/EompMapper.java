package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.custom;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.ExcessDeductionStatusEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 月末処理情報の計算結果を出力するマッパー
 */
@Mapper
public interface EompMapper {
    //超過控除ステータスの情報
    List<ExcessDeductionStatusEntity> findExcessDeductionStatusData(@Param("ids") List<Integer> ids);
}