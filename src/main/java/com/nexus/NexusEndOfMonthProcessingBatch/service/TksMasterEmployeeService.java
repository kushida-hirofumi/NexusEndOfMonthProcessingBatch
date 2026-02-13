package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.TksMasterEmployeeEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.mapper.TksMasterEmployeeMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TksMasterEmployeeService {

    @Autowired
    TksMasterEmployeeMapper tksMasterEmployeeMapper;

    public TksMasterEmployeeEntity findById(int id) {
        return tksMasterEmployeeMapper.findById(id);
    }

    public List<TksMasterEmployeeEntity> findByIds(@Param("idList") List<Integer> idList) {
        if(idList==null || idList.isEmpty()) return new ArrayList<>();
        return tksMasterEmployeeMapper.findByIds(idList);
    }

    public List<TksMasterEmployeeEntity> findByMailAddressList(List<String> mailAddressList) {
        if(mailAddressList ==null || mailAddressList.isEmpty()) return new ArrayList<>();
        return tksMasterEmployeeMapper.findByMailAddressList(mailAddressList);
    }

}