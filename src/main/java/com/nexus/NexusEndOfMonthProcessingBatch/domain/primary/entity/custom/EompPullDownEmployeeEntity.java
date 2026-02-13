package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom;

import lombok.Getter;
import lombok.Setter;

/**
 * 月末処理の社員プルダウン
 */
@Getter
@Setter
public class EompPullDownEmployeeEntity {
    //社員ID
    int employeeId;
    //氏名
    String name;
}