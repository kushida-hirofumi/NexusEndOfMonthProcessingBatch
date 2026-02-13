package com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.TksMasterCompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TksMasterCompanyMapper {
    TksMasterCompanyEntity findById(@Param("id") int id);
    List<TksMasterCompanyEntity> findByIds(@Param("idList") List<Integer> idList);
}