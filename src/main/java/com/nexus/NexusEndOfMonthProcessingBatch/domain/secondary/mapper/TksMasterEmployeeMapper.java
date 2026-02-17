package com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.TksMasterEmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TksMasterEmployeeMapper {
    TksMasterEmployeeEntity findById(@Param("id") int id);
    List<TksMasterEmployeeEntity> findByIds(@Param("idList") List<Integer> idList);
}