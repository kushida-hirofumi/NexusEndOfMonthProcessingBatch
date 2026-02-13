package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_invoice;

import lombok.Getter;

/**
 * 給与明細の請求情報
 */
@Getter
public class PayrollDetailsInvoice {
    //小計
    double syokei;
    //原価率
    double genkaritu;

    ///原価率取得
    public String genkaritu_percent() {
        double dValue = genkaritu;
        if(dValue==0) return "0%";
        return dValue + "%";
    }

}