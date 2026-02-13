package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 社員情報のマッパー
 */
@Mapper
public interface NexusEmployeeMapper {

    NexusEmployeeEntity findById(@Param("id") int id);

}