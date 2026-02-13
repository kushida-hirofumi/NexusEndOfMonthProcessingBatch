package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_basic;

public interface PayrollDetailsBasicInterface {
    //作業日付
    Integer getPayForResults();
    //交通費
    Integer getTransportationExpenses();
    //扶養家族数
    Integer getDependentsNumber();
    //介護保険の有無
    String getPresenceOrAbsenceOfLongTermCareInsurance();
    //休み
    Double getHoliday();
    //資格褒賞金
    Integer getQualificationReward();
    //資格受験費
    Integer getQualificationExaminationFee();
    //出張　費用・経費
    Integer getBusinessTripCostsExpenses();
    //時間外h
    Double getOvertimeHour();
    //休日出勤h
    Double getHolidayWorkHour();
    //夜勤h
    Double getNightShift();
    //技術手当・講師代
    Integer getTechnicalAllowanceInstructorFee();
    //職務手当
    Integer getWorkAllowance();
    //前借交通費
    Integer getAdvanceTransportationExpenses();
    //途中退職で保険料ゼロ
    String getZeroPremiumsForEarlyRetirement();
    //住民税
    Integer getResidentTax();
    //基本給
    Integer getBasicSalary();
    //標準月額
    Integer getStandardMonthlyFee();
    //見なし残業代
    Integer getDeemedOvertimePay();
}