package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import com.nexus.NexusEndOfMonthProcessingBatch.interface_code.EndOfMonthProcessingEntityPrimitive;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.PayrollInterface;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.InterestRateUtility;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 月処理キット情報のエンティティ
 * 分類：担当Sシート
 */
@Getter
@Setter
public class NexusEndOfProcessingEntity implements PayrollInterface, EndOfMonthProcessingEntityPrimitive {
    //ID
    Integer id;
    //社員ID
    Integer employeeId;
    //氏名
    String employeeName;
    //メモ
    String memo;
    //作業日付
    LocalDateTime workingDate;
    //成果給
    Integer payForResults;
    //稼働時間
    Double workTime;
    //交通費
    Integer transportationExpenses;
    //扶養家族数
    Integer dependentsNumber;
    //介護保険の有無
    String presenceOrAbsenceOfLongTermCareInsurance;
    //休み
    Double holiday;
    //資格褒賞金
    Integer qualificationReward;
    //資格受験費
    Integer qualificationExaminationFee;
    //出張　費用・経費
    Integer businessTripCostsExpenses;
    //時間外h
    Double overtimeHour;
    //休日出勤h
    Double holidayWorkHour;
    //夜勤h
    Double nightShift;
    //技術手当・講師代
    Integer technicalAllowanceInstructorFee;
    //数量
    Double quantity;
    //職務手当
    Integer workAllowance;
    //前借交通費
    Integer advanceTransportationExpenses;
    //途中退職で保険料ゼロ
    String zeroPremiumsForEarlyRetirement;
    //住民税
    Integer residentTax;
    //基本給
    Integer basicSalary;
    //標準月額
    Integer standardMonthlyFee;
    //見なし残業代
    Integer deemedOvertimePay;
    //単価
    Integer unitPrice;
    //備考(EGへも開示)
    String remarks;
    //相殺備考
    String offsettingNotes;
    //書類作成者の社員ID
    Integer documentCreatorEmployeeId;
    //直近付与日
    LocalDateTime mostRecentGrantDate;
    //付与日数
    Integer grantedDays;
    //前年付与日
    LocalDateTime lastYearGrantDate;
    //付与日数
    Integer grantedDays2;
    //有休残日数
    Double numberOfRemainingPaidHolidays;
    //登録ユーザーID
    Integer registeredUserId;
    //登録日時
    LocalDateTime registeredDate;
    //更新ユーザーID
    Integer updateUserId;
    //更新日時
    LocalDateTime updateDate;

    //原価率
    public double genkaritu() {
        if(unitPrice==null) return 0;
        double result = (double) (
                (payForResults!=null ? payForResults : 0)
                        + (transportationExpenses!=null ? transportationExpenses : 0)
                        + (qualificationReward!=null ? qualificationReward : 0)
                        + (qualificationExaminationFee!=null ? qualificationExaminationFee : 0)
                        + (businessTripCostsExpenses!=null ? businessTripCostsExpenses : 0)
                        + (technicalAllowanceInstructorFee!=null ? technicalAllowanceInstructorFee : 0)
                        + (workAllowance!=null ? workAllowance : 0)
                        + (advanceTransportationExpenses!=null ? advanceTransportationExpenses : 0)
                        + (residentTax!=null ? residentTax : 0)
                        + (basicSalary!=null ? basicSalary : 0)
                        + (standardMonthlyFee!=null ? standardMonthlyFee : 0)
                        + (deemedOvertimePay!=null ? deemedOvertimePay : 0)
        ) / unitPrice;
        return InterestRateUtility.roundUp( result, 2 );
    }
}