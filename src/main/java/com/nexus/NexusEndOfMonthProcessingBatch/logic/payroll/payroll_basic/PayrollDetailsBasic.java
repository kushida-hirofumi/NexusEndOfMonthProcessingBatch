package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_basic;

import lombok.Getter;

/**
 * 給与支給明細データの基礎情報
 */
@Getter
public class PayrollDetailsBasic {

    double holiday;
    //標準月額
    int hyoujun_getugaku;
    //基本給
    double basicSalary;
    //業務手当
    //基本給-(基本給/20*(休み+研修期間(日)))
    double gyoumuteate;
    //成果給
    int seikakyuu;
    //控除
    double koujo;
    //時間外
    double overtimeHour;
    //休日出勤
    double holidayWorkHour;
    //夜勤
    double nightShift;
    // 基本給/160*1.25[四捨五入]
    double jikangai_kakaku;
    // 基本給/160*0.35[四捨五入]
    double kyuujitu_kakaku;
    // 基本給/160*0.25[四捨五入]
    double shinyakinmu_kakaku;
    //時間外h[四捨五入]
    long jikangai_jikan;
    //休日出勤h[四捨五入]
    long kyuujitu_jikan;
    //夜勤h[四捨五入]
    long shinyakinmu_jikan;
    //if(時間外h>=60.5,基本給/160*1.25*60+基本給/160*1.5(時間外h-60),基本給/160*1.25*時間外h)から更にK10セルの業務手当をひく[四捨五入]
    double jikangai;
    //基本給/160*0.35*時間外h[四捨五入]
    double kyuujitu;
    //基本給/160*0.25*夜勤h[四捨五入]
    double shinyakinmu;
    //職務手当
    double syokumuteate;
    //交通費
    int koutuu;
    //経費
    int keihi;
    //資格報奨金
    int shikaku_housyoukin;
    //資格受験費
    int shikaku_jukenhi;
    //介護保険の有無
    boolean kaigohoken_no_umu;
    //途中退職
    boolean totyuu_taisyoku;
    //扶養者数
    int huyousyasuu;
    //住民税
    int juuminzei;
    //前借交通費
    int maegari_koutuuhi;
    //技術手当て・教育講師
    int gijutu_teate_kyouiku_koushi;
}