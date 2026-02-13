package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02Entity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEndOfMonthProcessingSheet02Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 月処理キット情報のサービス
 * 分類：月末処理シート
 */
@Service
public class NexusEndOfMonthProcessingSheet02Service {
    @Autowired
    NexusEndOfMonthProcessingSheet02Mapper nexusEndOfMonthProcessingSheet02Mapper;

    public int insert(NexusEndOfMonthProcessingSheet02Entity entity) {
        if(entity==null) return 0;
        return nexusEndOfMonthProcessingSheet02Mapper.insert(entity);
    }

    public int update(NexusEndOfMonthProcessingSheet02Entity entity) {
        if(entity==null) return 0;
        return nexusEndOfMonthProcessingSheet02Mapper.update(entity);
    }

    public List<NexusEndOfMonthProcessingSheet02Entity> findAll() {
        return nexusEndOfMonthProcessingSheet02Mapper.findAll();
    }

    public List<NexusEndOfMonthProcessingSheet02Entity> findByEmployeeIdsAndWorkingDate(List<Integer> employeeIds, LocalDateTime workingDate) {
        if(employeeIds==null || employeeIds.isEmpty() || workingDate==null) return new ArrayList<>();
        return nexusEndOfMonthProcessingSheet02Mapper.findByEmployeeIdsAndWorkingDate(employeeIds, workingDate);
    }

}