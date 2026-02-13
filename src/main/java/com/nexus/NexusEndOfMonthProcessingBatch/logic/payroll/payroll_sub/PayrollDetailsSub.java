package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_sub;

import lombok.Getter;

@Getter
public class PayrollDetailsSub {
    /**
     * 小計
     * J20  =SUM(I13:I19)
     */
    double syoukei;
    //支給額
    //(基本給+業務手当+成果給+控除) + (時間外勤務手当+休日勤務手当+深夜勤務手当+職務手当+技術手当・教育講師手当)
    double shikyuugaku;
    //総支給額
    //支給額+交通費(非課税分）+経費(非課税分）+資格報奨金(課税分）+資格受験費・経費(非課税分）
    double soushikyuugaku;
    //介護保険の有無
    boolean kaigohoken_no_umu;
    //健康保険
    //if(途中退職=●,0,if(介護保険=●,round(標準月額*(健康保険料率+介護保険料率)/2-0.1),round(標準月額*(健康保険料率)/2-0.1)
    double kenkou_hoken;
    //厚生年金
    //if(途中退職=●,0,round(標準月額*厚生年金保険料率/2-0.1)
    float kousei_nenkin;
    //健康保険2
    double kenkou_hoken_2;
    //厚生年金2
    double kousei_nenkin_2;
    //雇用保険  上
    //ROUNDDOWN((${総支給額}-(${経費(非課税分）}+ ${資格受験費・経費(非課税分）}))*${雇用保険　上}/1000,0)
    double koyou_hoken;
    //このフォーマットにはないが、()でくくって右の数字を表示→支給額と資格報奨金の合計額から、健康保険、厚生年金、健康保険2、厚生年金2、雇用保険の合計を引いた金額
    double syotokuzei;
    //差引支給額
    //支給額-(健康保険+厚生年金+雇用保険+所得税+住民税)+交通費+経費+資格報奨金+資格受験費　K18-(K24+K25+K29+K30+K31)+N19+N20+N21+N22
    double sashikihi_shikyuugaku;
    //支払給与額
    //差引支給額ー前借交通費の金額
    double shiharai_kyuuyogaku;
    //雇用保険  下
    //ROUNDDOWN((${総支給額}-(${経費(非課税分）} + ${資格受験費・経費(非課税分）})) * ${事業主負担の雇用保険料率(%)}/1000,0)
    double koyou_hoken_2;
    //労災保険
    //( ( ${総支給額} - ${経費(非課税分）} ) / 1000 ) * ${労働保険料率}
    double rousai_hoken;
    //児童福祉手当て拠出金
    //標準月額*児童福祉手当拠出金料率
    double jidou_hukushi_teate_kyosyutukin;
    //会社総負担額
    //総支給額+健康保険+厚生年金+健康保険2+厚生年金2+雇用保険+労災保険+児童福祉手当て拠出金　K23＋K35～K42
    double kaisya_sou_hutangaku;
    //福利厚生費
    //K35～K42の合算
    double hukuri_kouseihi;

}