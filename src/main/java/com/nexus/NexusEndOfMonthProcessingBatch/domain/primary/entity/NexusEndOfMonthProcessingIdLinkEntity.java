package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.EndOfMonthProcessingIdLinkAttributeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 月末処理のID紐付けテーブルのエンティティ
 */
@Getter
@Setter
public class NexusEndOfMonthProcessingIdLinkEntity {
    //リンクの分類
    EndOfMonthProcessingIdLinkAttributeEnum attribute;
    //リンク元のID
    Integer srcId;
    //リンク先のID
    Integer destId;
    //登録ユーザーID
    Integer registeredUserId;
    //登録日時
    LocalDateTime registeredDate;
    //更新ユーザーID
    Integer updateUserId;
    //更新日時
    LocalDateTime updateDate;
}