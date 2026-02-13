package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.MstCompaniesThatOutputProfitInformationEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.MstCompaniesThatOutputProfitInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 利益情報を出力する会社情報のサービス
 */
@Service
public class MstCompaniesThatOutputProfitInformationService {

    @Autowired
    MstCompaniesThatOutputProfitInformationMapper mstCompaniesThatOutputProfitInformationMapper;

    public List<MstCompaniesThatOutputProfitInformationEntity> findAll() {
        return mstCompaniesThatOutputProfitInformationMapper.findAll();
    }

}