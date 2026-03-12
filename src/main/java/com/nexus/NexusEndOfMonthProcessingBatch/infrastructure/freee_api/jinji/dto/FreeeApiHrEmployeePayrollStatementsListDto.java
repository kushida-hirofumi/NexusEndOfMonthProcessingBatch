package com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
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
 * 給与明細情報リスト
 */
@Data
public class FreeeApiHrEmployeePayrollStatementsListDto {
    @Data
    public static class EmployeePayrollStatements {
        /**
         * 支払い情報
         */
        @Data
        public static class Payment {
            //名称
            @JsonProperty("name")
            String name;
            //金額
            @JsonProperty("amount")
            double amount;
        }

        /**
         * 従業員ID
         */
        @JsonProperty("employee_id")
        int employeeId;

        /**
         * FreeeID
         */
        @JsonProperty("employee_num")
        String employeeNum;

        /**
         * 支払日
         */
        @JsonProperty("pay_date")
        LocalDate payDate;

        /**
         * 基本給
         */
        @JsonProperty("basic_pay_amount")
        double basicPayAmount;

        /**
         * 法定福利費の会社負担分の合計（健康保険、厚生年金、雇用保険等）
         */
        @JsonProperty("total_deduction_employer_share")
        double totalDeductionEmployerShare;

        /**
         * 総支払額
         */
        @JsonProperty("gross_payment_amount")
        double grossPaymentAmount;

        /**
         * 支払い情報
         */
        @JsonProperty("payments")
        List<Payment> payments = new ArrayList<>();

        /**
         * 控除項目
         */
        @JsonProperty("deductions")
        List<Payment> deductions = new ArrayList<>();

        /**
         * 控除_雇用主負担
         */
        @JsonProperty("deductions_employer_share")
        List<Payment> deductionsEmployerShareList = new ArrayList<>();

        /**
         * @return  原価
         */
        public double getCostPrice() {
            return totalDeductionEmployerShare + grossPaymentAmount;
        }

        /**
         * 支払い情報リストから支払い金額を抽出する
         * @param name  抽出する項目名
         * @return  金額
         */
        double extractPayments(String name) {
            if(StringUtils.isBlank(name)) return 0;
            EmployeePayrollStatements.Payment payment = payments.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
            return payment!=null ? payment.amount : 0;
        }

        /**
         * @return  成果給
         */
        public double getPerformancePay() {
            return extractPayments("成果給");
        }

        /**
         * @return  業務手当
         */
        public double getWorkAllowance() {
            return extractPayments("業務手当");
        }

        /**
         * @return  職務手当
         */
        public double getJobAllowance() {
            return extractPayments("職務手当");
        }

        /**
         * @return  住民税
         */
        public double getResidentTax() {
            for(Payment payment : deductions) {
                if(payment.name.equals("住民税")) return payment.amount;
            }
            return 0;
        }
    }

    @JsonProperty("employee_payroll_statements")
    List<EmployeePayrollStatements> employeePayrollStatements = new ArrayList<>();
}