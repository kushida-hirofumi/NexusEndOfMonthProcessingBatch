package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.EndOfMonthProcessingIdLinkAttributeEnum;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingIdLinkEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEndOfMonthProcessingIdLinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 月末処理のID紐付け情報を扱うのサービス
 */
@Service
public class NexusEndOfMonthProcessingIdLinkService {

    @Autowired
    NexusEndOfMonthProcessingIdLinkMapper nexusEndOfMonthProcessingIdLinkMapper;

    public NexusEndOfMonthProcessingIdLinkEntity findByAttributeAndDestId(EndOfMonthProcessingIdLinkAttributeEnum attribute, int destId) {
        if(attribute==null) return null;
        return nexusEndOfMonthProcessingIdLinkMapper.findByAttributeAndDestId(attribute, destId);
    }
}