package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 月末処理(月末処理シート)で利用する一覧ソート用のテーブル
 */
@Getter
@Setter
public class NexusEndOfMonthProcessingSheet02ListSortEntity {

    //ソートを行うアカウントのID
    int accountId;

    //ソート対象のレコードID
    int recordId;

    /**
     * 並び順
     * 値が大きいほど下に並ぶ
     */
    int sort;

    //登録ユーザーID
    Integer registeredUserId;

    //登録日時
    LocalDateTime registeredDate;

    //更新ユーザーID
    Integer updateUserId;

    //更新日時
    LocalDateTime updateDate;
}