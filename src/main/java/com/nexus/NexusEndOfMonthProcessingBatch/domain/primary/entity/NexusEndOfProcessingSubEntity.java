package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 有給情報を管理する
 */
@Data
public class NexusEndOfProcessingSubEntity {
    //月末処理ID
    Integer parentId;
    //使用月・日
    LocalDate monthAndDayOfUses;
    //半休使用
    Boolean halfTimeUse;
    //登録ユーザーID
    Integer registeredUserId;
    //登録日時
    LocalDateTime registeredDate;
    //更新ユーザーID
    Integer updateUserId;
    //更新日時
    LocalDateTime updateDate;
}