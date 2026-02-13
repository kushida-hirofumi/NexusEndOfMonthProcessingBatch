package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 月処理キット情報のエンティティ
 * 分類：月末処理シート
 * 調整額
 */
@Getter
@Setter
public class NexusEndOfMonthProcessingSheet02SubAdjustmentAmountEntity {
    //月末処理ID
    Integer parentId;
    //ボーナス額
    Integer bonusMoney;
    //ボーナスフラグ
    Boolean bonusFlag;
    //時間外時間
    Double offHoursTime;
    //時間外フラグ
    Boolean offHoursFlag;
    //夜勤時間
    Double nightShiftTime;
    //夜勤フラグ
    Boolean nightShiftFlag;
    //その他額
    Integer othersMoney;
    //その他フラグ
    Boolean othersFlag;
    //登録ユーザーID
    Integer registeredUserId;
    //登録日時
    LocalDateTime registeredDate;
    //更新ユーザーID
    Integer updateUserId;
    //更新日時
    LocalDateTime updateDate;
}