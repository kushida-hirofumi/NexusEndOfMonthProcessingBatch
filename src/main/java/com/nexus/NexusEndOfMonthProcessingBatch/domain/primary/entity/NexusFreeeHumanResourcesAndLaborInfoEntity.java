package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Freeeの人事労務情報のテーブルのエンティティ
 */
@Data
public class NexusFreeeHumanResourcesAndLaborInfoEntity {
    //Freee会社ID
    String freeeId;
    //支払日
    LocalDate payDate;
    //基本給
    int basicSalary;
    //職務手当
    int jobAllowance;
    //業務手当
    int businessAllowances;
    //登録ユーザーID
    Integer registeredUserId;
    //登録日時
    LocalDateTime registeredDate;
    //更新ユーザーID
    Integer updateUserId;
    //更新日時
    LocalDateTime updateDate;
}