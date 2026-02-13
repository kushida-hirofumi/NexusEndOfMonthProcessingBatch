package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_sub;

public interface PayrollDetailsSubInterface {
    //介護保険の有無
    String getPresenceOrAbsenceOfLongTermCareInsurance();
    //単価
    Integer getUnitPrice();
}