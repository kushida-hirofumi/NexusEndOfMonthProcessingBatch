package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 祝日情報のエンティティ
 */
@Getter
@Setter
public class NexusHolidayInfoEntity {

    //休日月日
    LocalDate holidayMonthDay;

    //休日名称
    String holidayName;

    //登録ユーザーID
    Integer registeredUserId;

    //登録日時
    LocalDateTime registeredDate;

    //更新ユーザーID
    Integer updateUserId;

    //更新日時
    LocalDateTime updateDate;

}