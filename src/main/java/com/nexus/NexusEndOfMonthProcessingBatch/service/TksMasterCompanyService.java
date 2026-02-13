package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.TksMasterCompanyEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.mapper.TksMasterCompanyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TksMasterCompanyService {

    @Autowired
    TksMasterCompanyMapper tksMasterCompanyMapper;

    public TksMasterCompanyEntity findById(@Param("id") int id) {
        return tksMasterCompanyMapper.findById(id);
    }

    public List<TksMasterCompanyEntity> findByIds(@Param("idList") List<Integer> idList) {
        if(idList==null || idList.isEmpty()) return new ArrayList<>();
        return tksMasterCompanyMapper.findByIds(idList);
    }

}