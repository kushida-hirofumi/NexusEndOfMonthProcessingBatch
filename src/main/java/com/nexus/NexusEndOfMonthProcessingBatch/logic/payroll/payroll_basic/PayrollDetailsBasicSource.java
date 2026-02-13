package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_basic;

import com.nexus.NexusEndOfMonthProcessingBatch.utility.InterestRateUtility;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyNumberUtility;
import org.apache.commons.lang3.StringUtils;

public class PayrollDetailsBasicSource {

    public static PayrollDetailsBasic create(PayrollDetailsBasicInterface entity) {
        PayrollDetailsBasic result = new PayrollDetailsBasic();
        if(entity==null) return result;
        result.holiday = MyNumberUtility.nullCheckDouble(entity.getHoliday());
        //標準月額
        result.hyoujun_getugaku = MyNumberUtility.nullCheckInt(entity.getStandardMonthlyFee());
        //業務手当
        //基本給-(基本給/20*(休み+研修期間(日)))
        result.gyoumuteate = entity.getDeemedOvertimePay()!=null && entity.getDeemedOvertimePay()>0 ?
                entity.getDeemedOvertimePay()-((double) entity.getDeemedOvertimePay() / 20 * (result.holiday + 0))
                : 0;
        //成果給
        result.seikakyuu = MyNumberUtility.nullCheckInt(entity.getPayForResults());
        //控除
        //（-1 * 基本給 / 20 * 休み）　小数点切り捨て
        result.koujo = entity.getBasicSalary()!=null ?
                -1 * Math.floor((double) entity.getBasicSalary() / 20 * result.holiday)
                : 0;
        //基本給
        result.basicSalary = MyNumberUtility.nullCheckInt(entity.getBasicSalary());
        //時間外
        result.overtimeHour = entity.getOvertimeHour()!=null ? InterestRateUtility.roundUp(entity.getOvertimeHour(), 0) : 0;
        //休日出勤
        result.holidayWorkHour = entity.getHolidayWorkHour()!=null ? InterestRateUtility.roundUp(entity.getHolidayWorkHour(), 0) : 0;
        //夜勤
        result.nightShift = entity.getNightShift()!=null ? InterestRateUtility.roundUp(entity.getNightShift(), 0) : 0;
        // 基本給/160*1.25[四捨五入]
        result.jikangai_kakaku = entity.getBasicSalary()!=null ? InterestRateUtility.roundUp((result.basicSalary / 160 * 1.25), 0) : 0;
        // 基本給/160*0.35[四捨五入]
        result.kyuujitu_kakaku = entity.getBasicSalary()!=null ? InterestRateUtility.roundUp((result.basicSalary / 160 * 0.35), 0) : 0;
        // 基本給/160*0.25[四捨五入]
        result.shinyakinmu_kakaku = entity.getBasicSalary()!=null ? InterestRateUtility.roundUp((result.basicSalary / 160 * 0.25), 0) : 0;
        //時間外h[四捨五入]
        result.jikangai_jikan = entity.getOvertimeHour()!=null ? (long) result.overtimeHour : 0;
        //休日出勤h[四捨五入]
        result.kyuujitu_jikan = entity.getHolidayWorkHour()!=null ? (long) result.holidayWorkHour : 0;
        //夜勤h[四捨五入]
        result.shinyakinmu_jikan = entity.getNightShift()!=null ? (long) result.nightShift : 0;
        //if(時間外h>=60.5,基本給/160*1.25*60+基本給/160*1.5(時間外h-60),基本給/160*1.25*時間外h)から更にK10セルの業務手当をひく[四捨五入]
        result.jikangai = 0;
        if(entity.getBasicSalary()!=null) {
            if(entity.getOvertimeHour()!=null && entity.getHolidayWorkHour()!=null && entity.getNightShift()!=null) {
                if(result.overtimeHour>=60.5) {
                    result.jikangai = result.basicSalary/160*1.25*60+result.basicSalary/160*1.5 * (result.overtimeHour-60);
                } else {
                    result.jikangai = result.basicSalary/160*1.25*result.overtimeHour;
                }
                result.jikangai -= result.gyoumuteate;
                result.jikangai = InterestRateUtility.roundUp( result.jikangai, 0 );
            }
        }
        //基本給/160*0.35*時間外h[四捨五入]
        result.kyuujitu = 0;
        if(entity.getBasicSalary()!=null) {
            if(entity.getHolidayWorkHour()!=null) {
                result.kyuujitu = InterestRateUtility.roundUp( result.basicSalary / 160 * 0.35 * result.holidayWorkHour, 0 );
            }
        }
        //基本給/160*0.25*夜勤h[四捨五入]
        result.shinyakinmu = 0;
        if(entity.getBasicSalary()!=null) {
            if(entity.getNightShift()!=null) {
                result.shinyakinmu = InterestRateUtility.roundUp( result.basicSalary / 160 * 0.25 * result.nightShift, 0 );
            }
        }

        result.syokumuteate = MyNumberUtility.nullCheckDouble(entity.getWorkAllowance());
        result.koutuu = MyNumberUtility.nullCheckInt(entity.getTransportationExpenses());
        result.keihi = MyNumberUtility.nullCheckInt(entity.getBusinessTripCostsExpenses());
        result.shikaku_housyoukin = MyNumberUtility.nullCheckInt(entity.getQualificationReward());
        result.shikaku_jukenhi = MyNumberUtility.nullCheckInt(entity.getQualificationExaminationFee());

        //介護保険の有無
        result.kaigohoken_no_umu = !StringUtils.isBlank(entity.getPresenceOrAbsenceOfLongTermCareInsurance()) && entity.getPresenceOrAbsenceOfLongTermCareInsurance().equals("あり");
        //途中退職
        result.totyuu_taisyoku = !StringUtils.isBlank(entity.getZeroPremiumsForEarlyRetirement()) && entity.getZeroPremiumsForEarlyRetirement().equals("該当");


        //扶養者数
        result.huyousyasuu = MyNumberUtility.nullCheckInt(entity.getDependentsNumber());
        //住民税
        result.juuminzei = MyNumberUtility.nullCheckInt(entity.getResidentTax());
        //前借交通費
        result.maegari_koutuuhi = MyNumberUtility.nullCheckInt(entity.getAdvanceTransportationExpenses());
        //技術手当て・教育講師
        result.gijutu_teate_kyouiku_koushi = MyNumberUtility.nullCheckInt(entity.getTechnicalAllowanceInstructorFee());
        return result;
    }

}