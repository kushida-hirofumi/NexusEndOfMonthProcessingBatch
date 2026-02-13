package com.nexus.NexusEndOfMonthProcessingBatch.interface_code;

import java.time.LocalDateTime;

/**
 * 月末処理関連のエンティティのインターフェース
 */
public interface EndOfMonthProcessingEntityPrimitive extends EntityPrimitiveInterface {
    //社員ID
    Integer getEmployeeId();
    //社員名
    String getEmployeeName();
    //作業日付
    LocalDateTime getWorkingDate();
}