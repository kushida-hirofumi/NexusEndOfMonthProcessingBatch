package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusCompanyEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusCompanyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NexusCompanyService {

    @Autowired
    NexusCompanyMapper nexusCompanyMapper;

    public NexusCompanyEntity findById(Integer id) {
        if (id==null) return null;
        return nexusCompanyMapper.findById(id);
    }

    public List<NexusCompanyEntity> findByCompanyName(String companyName) {
        if(StringUtils.isBlank(companyName)) return new ArrayList<>();
        return nexusCompanyMapper.findByCompanyName(companyName);
    }

}