package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.seed;

import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_sub.PayrollDetailsSub;

/**
 * 「分析 & 種」の生成を行う
 */
public class PayrollSeedSource {
    public static PayrollSeed create(PayrollSeedInterface entity, PayrollDetailsSub payrollDetailsSub) {
        PayrollSeed payrollDetailsSeed = new PayrollSeed();
        if(entity==null || payrollDetailsSub==null) return payrollDetailsSeed;
        //入金
        payrollDetailsSeed.nyuukin = payrollDetailsSub.getSyoukei();
        //課税小計 - 会社総負担額
        payrollDetailsSeed.kanri_tesuuryo_nasi = entity.maximumPrice() - payrollDetailsSub.getKaisya_sou_hutangaku();
        //管理手数料
        payrollDetailsSeed.kanri_tesuuryo = Math.round(payrollDetailsSeed.kanri_tesuuryo_nasi * 0.25);
        //折半
        payrollDetailsSeed.seppan = Math.round(payrollDetailsSeed.kanri_tesuuryo_nasi*25/200);
        //管理手数料ｰ折半
        payrollDetailsSeed.kanritesuuryo_seppan = payrollDetailsSeed.kanri_tesuuryo - payrollDetailsSeed.seppan;
        //入金-管理手数料
        //IF(AI2-AJ2>AI2,0,IF(AR2=CM36,ROUNDDOWN(AI2-AJ2,0),ROUNDDOWN(AI2-(CM36+AR2),0)))
        payrollDetailsSeed.nyuukin_kanritesuuryo = (
                //IF(AR2=CM36
                payrollDetailsSeed.kanritesuuryo_seppan==payrollDetailsSeed.seppan_margin ?
                        //ROUNDDOWN(AI2-AJ2,0)
                        (Math.round(payrollDetailsSeed.nyuukin - payrollDetailsSeed.kanri_tesuuryo))
                        //ROUNDDOWN(AI2-(CM36+AR2),0)
                        : Math.round(payrollDetailsSeed.nyuukin - (payrollDetailsSeed.seppan_margin - payrollDetailsSeed.kanritesuuryo_seppan)));
        //折半マージン
        payrollDetailsSeed.seppan_margin = payrollDetailsSeed.kanri_tesuuryo - payrollDetailsSeed.kanritesuuryo_seppan;
        return payrollDetailsSeed;
    }
}