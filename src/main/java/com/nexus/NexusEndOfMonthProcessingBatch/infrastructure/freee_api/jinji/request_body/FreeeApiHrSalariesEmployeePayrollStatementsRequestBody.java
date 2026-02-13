package com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.request_body;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 【クラス分類】
 * リクエストボディ
 * *
 * 【api分類】
 * 人事労務API
 * *
 * 【データ名】
 * 給与明細の取得を行う
 */
@Data
public class FreeeApiHrSalariesEmployeePayrollStatementsRequestBody {
    /**
     * 事業所ID
     */
    int companyId;
    /**
     * 従業員情報を取得したい年
     */
    int year;
    /**
     * 従業員情報を取得したい月
     */
    int month;
    /**
     * 取得レコードの件数 (デフォルト: 50, 最小: 1, 最大: 100)
     */
    int limit = 50;
    /**
     * 取得レコードのオフセット (デフォルト: 0)
     */
    int offset;

    public FreeeApiHrSalariesEmployeePayrollStatementsRequestBody(LocalDateTime ld) {
        this.year = ld.getYear();
        this.month = ld.getMonthValue();
    }
}