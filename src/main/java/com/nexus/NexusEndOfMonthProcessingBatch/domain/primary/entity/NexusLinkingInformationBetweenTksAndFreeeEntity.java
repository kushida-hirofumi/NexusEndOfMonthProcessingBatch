package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * エンティティ
 * 【テーブル】TKSとfreee会計の情報の紐付を行うテーブル
 */
@Data
public class NexusLinkingInformationBetweenTksAndFreeeEntity {

    //TKSの社員ID
    Integer tksData;

    //freee会計の情報
    String freeeData;

    //登録ユーザーID
    Integer registeredUserId;

    //登録日時
    LocalDateTime registeredDate;

    //更新ユーザーID
    Integer updateUserId;

    //更新日時
    LocalDateTime updateDate;

    /**
     * FreeeIDから会社略称を抽出する
     * @return  会社略称
     */
    public String extractFreeeCompanyShortName() {
        if(StringUtils.isBlank(freeeData)) return null;
        //大文字変換しつつ抽出
        return freeeData.substring(0, 3).toUpperCase();
    }
}