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
 * 社員の取得を行う
 */
@Data
public class FreeeApiHrEmployeesRequestBody {
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
     * 締め日支払い日設定が翌月払いの従業員情報の場合は、 指定したmonth + 1の値が検索結果として返します。
     * 翌月払いの従業員の2022/01の従業員情報を取得する場合は、year=2021,month=12を指定してください。
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
    /**
     * trueを指定すると給与計算対象外の従業員情報をレスポンスに含めます。
     */
    boolean withNoPayrollCalculation;

    public FreeeApiHrEmployeesRequestBody(LocalDateTime ld) {
        this.year = ld.getYear();
        this.month = ld.getMonthValue();
    }
}