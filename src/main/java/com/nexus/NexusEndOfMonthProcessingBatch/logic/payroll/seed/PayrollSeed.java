package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.seed;

import lombok.Data;

/**
 * 分析＆種
 */
@Data
public class PayrollSeed {

    /**
     * 入金
     * excelの参照
     *  AI2    =$ｓ.$I$20
     */
    double nyuukin = 0;

    /**
     * 管理手数料
     * excelの参照
     *  AT2  =L2-O2
     */
    double kanri_tesuuryo_nasi = 0;

    /**
     * 管理手数料
     * excelの参照
     *  PD(25%)
     *  S2  =ROUNDDOWN(AT2*0.25,0)
     *  AJ2  =$S$2
     */
    double kanri_tesuuryo = 0;

    /**
     * 折半
     * excelの参照
     *  AC2     =ROUNDDOWN(AT2*25/200,0)
     */
    double seppan = 0;

    /**
     * 管理手数料ｰ折半
     * excelの参照
     *  AR2    =AJ2-AC2
     */
    double kanritesuuryo_seppan = 0;

    /**
     * 入金-管理手数料
     * excelの参照
     *  AL2     =IF(AI2-AJ2>AI2,0,IF(AR2=CM36,ROUNDDOWN(AI2-AJ2,0),ROUNDDOWN(AI2-(CM36+AR2),0)))
     */
    double nyuukin_kanritesuuryo = 0;

    /**
     * 折半マージン
     * excelの参照
     *  CM36    =AJ2-AR2
     */
    double seppan_margin = 0;

}