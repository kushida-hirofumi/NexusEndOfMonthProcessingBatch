package com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.kaikei.request_body;

import lombok.Data;

/**
 * 【クラス分類】
 * リクエストボディ
 * *
 * 【api分類】
 * 会計API
 * *
 * 【データ名】
 * 事業所情報
 */
@Data
public class FreeeApiAccountingCompanyRequestBody {
    //事業所ID
    int id;
}