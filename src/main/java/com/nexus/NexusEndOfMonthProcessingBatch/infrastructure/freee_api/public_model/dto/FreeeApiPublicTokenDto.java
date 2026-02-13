package com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.public_model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 【クラス分類】
 * レスポンス
 * *
 * 【api分類】
 * 公開API
 * *
 * 【データ名】
 * トークン
 */
@Data
public class FreeeApiPublicTokenDto {
    //アクセストークン
    @JsonProperty("access_token")
    String accessToken;

    //アクセストークンに利用するリフレッシュトークン
    @JsonProperty("refresh_token")
    String refreshToken;
}