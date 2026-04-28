package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.dto.FreeeApiHrEmployeePayrollStatementsListDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Freeeの人事労務情報のテーブルのエンティティ
 */
@Data
public class NexusFreeeHumanResourcesAndLaborInfoEntity {
    //Freee会社ID
    String freeeId;
    //支払日
    LocalDate payDate;
    //基本給
    int basicSalary;
    //職務手当
    int jobAllowance;
    //業務手当
    int businessAllowances;
    //登録ユーザーID
    Integer registeredUserId;
    //登録日時
    LocalDateTime registeredDate;
    //更新ユーザーID
    Integer updateUserId;
    //更新日時
    LocalDateTime updateDate;


    /**
     * FreeeApiから取得した給与明細情報からエンティティ設定
     * @param employeePayrollStatementsListDto  給与明細情報
     */
    public void setting(FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements employeePayrollStatementsListDto) {
        //FreeeID
        freeeId = employeePayrollStatementsListDto.getEmployeeNum();
        //支払日
        payDate = employeePayrollStatementsListDto.getPayDate();
        //基本給
        basicSalary = (int) employeePayrollStatementsListDto.getBasicPayAmount();
        //業務手当
        businessAllowances = (int) employeePayrollStatementsListDto.getWorkAllowance();
        //職務手当
        jobAllowance = (int) employeePayrollStatementsListDto.getJobAllowance();
    }
}