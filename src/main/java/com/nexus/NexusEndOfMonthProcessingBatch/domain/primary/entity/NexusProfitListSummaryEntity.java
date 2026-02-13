package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.TksMasterCompanyEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.TksMasterEmployeeEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.dto.FreeeApiHrEmployeeListDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.dto.FreeeApiHrEmployeePayrollStatementsListDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.kaikei.dto.FreeeApiAccountingCompanyDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 利益一覧情報のエンティティ
 */
@Data
public class NexusProfitListSummaryEntity {
    //計算結果ID
    Integer summaryId;

    //会社ID
    int companyId;

    //会社名
    String companyName;

    //担当者ID
    int contactId;

    //担当者氏名
    String contactName;

    //社員ID
    int employeeId;

    //社員名
    String employeeName;

    //支払日
    LocalDate paymentDate;

    //請求額
    double billingAmount;

    //原価
    double costPrice;

    //基本給
    double basicPayAmount;

    //成果給
    double performancePay;

    //標準月額
    double standardMonthlyAmount;

    //住民税
    double residentTax;

    //削除フラグ
    boolean deleteFlg;

    //登録ユーザーID
    Integer registeredUserId;

    //登録日時
    LocalDateTime registeredDate;

    //更新ユーザーID
    Integer updateUserId;

    //更新日時
    LocalDateTime updateDate;

    /**
     *
     * @param ld    支払日
     * @param billingAmount 請求額
     * @param freeeApiCompanyDto    所属会社情報
     * @param employeeDto   社員情報
     * @param freeeApiEmployeePayrollStatementsDto  給与明細情報
     * @param tksMasterEmployeeEntity       TKSの社員情報
     */
    public void setting(LocalDate ld, double billingAmount, FreeeApiAccountingCompanyDto freeeApiCompanyDto, FreeeApiHrEmployeeListDto.Employee employeeDto, FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements freeeApiEmployeePayrollStatementsDto, TksMasterCompanyEntity tksMasterCompanyEntity, TksMasterEmployeeEntity tksMasterEmployeeEntity) {
        if(ld==null || freeeApiCompanyDto ==null || employeeDto==null || freeeApiEmployeePayrollStatementsDto ==null || tksMasterEmployeeEntity==null) return;
        this.companyId = tksMasterEmployeeEntity.getCompanyId();
        this.companyName = tksMasterCompanyEntity.getCompanyName();
        this.contactId = tksMasterEmployeeEntity.getManagerId()!=null ? tksMasterEmployeeEntity.getManagerId() : 0;
        this.contactName = tksMasterEmployeeEntity.getManagerEmployeeName();
        this.employeeId = tksMasterEmployeeEntity.getID();
        this.employeeName = tksMasterEmployeeEntity.getEmployeeName();
        this.paymentDate = ld;
        //請求額
        this.billingAmount = billingAmount;
        //原価
        this.costPrice = freeeApiEmployeePayrollStatementsDto.getCostPrice();
        //基本給
        this.basicPayAmount = freeeApiEmployeePayrollStatementsDto.getBasicPayAmount();
        //成果給
        this.performancePay = freeeApiEmployeePayrollStatementsDto.getPerformancePay();
        //標準月額
        this.standardMonthlyAmount = employeeDto.getStandardMonthlyAmount();
        //住民税
        this.residentTax = freeeApiEmployeePayrollStatementsDto.getResidentTax();
    }
}