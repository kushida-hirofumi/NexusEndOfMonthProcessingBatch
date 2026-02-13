package com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.public_model.request_body;

import lombok.Data;

/**
 * 【クラス分類】
 * リクエストボディ
 * *
 * 【api分類】
 * 公開API
 * *
 * 【データ名】
 * トークンの取得を行う
 */
@Data
public class FreeeApiPublicAccessTokenRequestBody {
    public enum GrantType {
        authorization_code,
        refresh_token
    }
    //処理分類
    String grantType;
    //コード
    String code;
    //リフレッシュトークン
    String refreshToken;
    //クライアントID
    String clientId;
    //クライアントシークレット
    String clientSecret;
    //リダイレクトURI
    String redirectUri;

    public static FreeeApiPublicAccessTokenRequestBody createRefreshToken(String clientId, String clientSecret, String value) {
        FreeeApiPublicAccessTokenRequestBody result = new FreeeApiPublicAccessTokenRequestBody();
        result.grantType = GrantType.refresh_token.name();
        result.refreshToken = value;
        result.clientId = clientId;
        result.clientSecret = clientSecret;
        return result;
    }

}