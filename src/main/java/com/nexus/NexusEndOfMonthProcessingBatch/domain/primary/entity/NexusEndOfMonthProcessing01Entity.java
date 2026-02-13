package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NexusEndOfMonthProcessing01Entity {
    //請求書テーブルのレコードID
    Integer recordId;
    //振込期日
    LocalDate transferDueDate;
}