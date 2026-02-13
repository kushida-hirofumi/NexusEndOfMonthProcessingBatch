package com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FreeeApiの処理で利用する定数
 */
public class FreeeApiConstant {

    public static class BaseUrl {
        //公開API
        public static final String publicUrl = "https://accounts.secure.freee.co.jp/public_api/";
        //会計API
        public static final String accountingUrl = "https://api.freee.co.jp/api/1/";
        //人事労務API
        public static final String hrUrl = "https://api.freee.co.jp/hr/api/v1/";
    }

    public interface PathListEnum {
        String getPath();
    }

    /**
     * 公開API
     */
    @AllArgsConstructor
    @Getter
    public enum PublicUrl implements PathListEnum {
        //アクセストークン
        accessToken("token"),
        ;
        final String path;
        public String getPath() {
            return BaseUrl.publicUrl + path;
        }
    }

    /**
     * 会計API
     */
    @AllArgsConstructor
    @Getter
    public enum AccountingUrl implements PathListEnum {
        //会社情報
        companiesCompanyId("companies/{id}"),
        ;
        final String path;
        public String getPath() {
            return BaseUrl.accountingUrl + path;
        }
    }

    /**
     * 人事労務API
     */
    @AllArgsConstructor
    @Getter
    public enum HrUrl implements PathListEnum {
        //全期間社員情報
        companiesEmployees("companies/{company_id}/employees"),
        //社員情報
        employees("employees"),
        //給与情報
        salariesEmployeePayrollStatements("salaries/employee_payroll_statements"),
        ;
        final String path;
        public String getPath() {
            return BaseUrl.hrUrl + path;
        }
    }

}