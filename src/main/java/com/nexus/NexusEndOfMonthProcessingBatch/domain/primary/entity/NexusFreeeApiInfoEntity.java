package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * FreeeApiで利用する情報を管理するエンティティ
 */
@Data
public class NexusFreeeApiInfoEntity {

    //FreeeのアプリのクライアントID
    String clientId;

    //Freeeのアプリのクライアントシークレット
    String clientSecret;

    //リダイレクトURL
    String redirectUrl;

    //FREEE上の企業ID
    int companyId;

    //FREEE上の企業名
    String companyName;

    //アクセストークン
    String accessToken;

    //リフレッシュトークン
    String refreshToken;

    //削除フラグ
    boolean deleteFlg;

    //登録ユーザーID
    Integer registeredUserId;

    //登録日時
    LocalDateTime registeredDate;

    //更新ユーザーID
    Integer updateUserId;

    //更新日時
    LocalDateTime updateDate;
}