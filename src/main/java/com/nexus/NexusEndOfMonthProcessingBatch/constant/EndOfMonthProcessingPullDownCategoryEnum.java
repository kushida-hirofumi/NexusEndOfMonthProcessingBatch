package com.nexus.NexusEndOfMonthProcessingBatch.constant;

import lombok.AllArgsConstructor;

/**
 * 月末処理機能のプルダウン生成用
 * DBから表示用情報を取得するタイプのプルダウンは分類をこのenumで管理する
 */
@AllArgsConstructor
public enum EndOfMonthProcessingPullDownCategoryEnum {
    EMPLOYEE("社員検索", "employeeId,hireDate"),
    EMPLOYEE2("社員検索2", "employeeId"),
    EMPLOYEE3("社員検索3", "freeeEmployeeId,companyShortName,employeeId,familyName,firstName,egCompanyName,hireDate"),
    HALF_SALESMAN_EMPLOYEE("折半氏名検索", "halfEmployeeId"),
    DOCUMENT_CREATOR_EMPLOYEE("書類作成者検索", "documentCreatorEmployeeId"),
    MANAGE_EMPLOYEE("担当者検索", "manageEmployeeId"),
    COUNTER_SALES_EMPLOYEE("窓口営業検索", "counterSalesEmployeeId"),
    NORMAL_COMPANY("通常会社検索", ""),
    CONTRACTOR_COMPANY("請求書会社検索", "contractorCompanyId"),
    HALF_AFFILIATION_COMPANY("折半所属会社検索", "halfAffiliationCompanyId");
    final String name;
    final String targetParamKeys;
    public String[] targetParamKeySplit() {
        return targetParamKeys.split(",");
    }
}