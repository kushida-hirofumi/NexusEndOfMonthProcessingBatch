package com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.custom;

import lombok.Getter;
import lombok.Setter;

/**
 * 月末処理の社員プルダウン
 */
@Getter
@Setter
public class TksEompPullDownEmployeeEntity {
    //所属会社ID
    int affiliationCompanyId;
    //所属会社略称
    String companyShortName;
    //社員ID
    int employeeId;
    //社員名
    String name;
    //氏名
    String familyName;
    //氏名
    String firstName;
    //入社日
    String hireDate;
}