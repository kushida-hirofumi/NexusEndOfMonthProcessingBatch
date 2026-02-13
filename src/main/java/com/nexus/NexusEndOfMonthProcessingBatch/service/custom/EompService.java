package com.nexus.NexusEndOfMonthProcessingBatch.service.custom;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.custom.EompMapper;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.ExcessDeductionStatusEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 月末処理情報の計算結果を出力するサービス
 */
@Service
public class EompService {

    @Autowired
    EompMapper eompMapper;

    //超過控除ステータスの情報
    public List<ExcessDeductionStatusEntity> findExcessDeductionStatusData(@Param("ids") List<Integer> ids) {
        if(ids == null || ids.isEmpty()) return new ArrayList<>();
        return eompMapper.findExcessDeductionStatusData(ids);
    }
}