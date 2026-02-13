package com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 【クラス分類】
 * レスポンス
 * *
 * 【api分類】
 * 人事労務API
 * *
 * 【データ名】
 * 社員情報リスト
 */
@Data
public class FreeeApiHrEmployeeListDto {
    @Data
    public static class Employee {

        /**
         * 健康保険の規定
         */
        @Data
        public static class HealthInsuranceRule {
            //標準報酬月額
            @JsonProperty("standard_monthly_remuneration")
            int standardMonthlyRemuneration;
        }

        /**
         * 厚生年金保険の規定
         */
        @Data
        public static class WelfarePensionInsuranceRule {
            //標準報酬月額
            @JsonProperty("standard_monthly_remuneration")
            int standardMonthlyRemuneration;
        }

        //社員ID
        @JsonProperty("id")
        int id;

        //FreeeID (会社名 + FreeeID の表記)
        @JsonProperty("num")
        String num;

        //健康保険
        @JsonProperty("health_insurance_rule")
        Employee.HealthInsuranceRule healthInsuranceRule;

        //厚生年金保険
        @JsonProperty("welfare pension insurance rule")
        Employee.WelfarePensionInsuranceRule welfarePensionInsuranceRule;

        /**
         * @return  標準月額
         */
        public int getStandardMonthlyAmount() {
            int result = 0;
            if(healthInsuranceRule!=null) result += healthInsuranceRule.standardMonthlyRemuneration;
            if(welfarePensionInsuranceRule!=null) result += welfarePensionInsuranceRule.standardMonthlyRemuneration;
            return result;
        }
    }

    @JsonProperty("employees")
    List<Employee> employees = new ArrayList<>();
}