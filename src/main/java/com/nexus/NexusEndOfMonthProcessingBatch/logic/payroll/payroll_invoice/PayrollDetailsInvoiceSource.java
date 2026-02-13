package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_invoice;

import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_sub.PayrollDetailsSub;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.InterestRateUtility;

public class PayrollDetailsInvoiceSource {
    public static PayrollDetailsInvoice create(
            PayrollDetailsInvoiceInterface entity,
            PayrollDetailsSub payrollDetailsSub,
            Integer total
    ) {
        PayrollDetailsInvoice payrollDetailsInvoice = new PayrollDetailsInvoice();
        if(entity==null) return payrollDetailsInvoice;
        if(total==null || total==0) {
            payrollDetailsInvoice.genkaritu = entity.genkaritu();
        } else {
            //小計
            payrollDetailsInvoice.syokei = total;
            //原価率
            //会社総負担額/契約元会社への入金額[小数点第三位で四捨五入]
            payrollDetailsInvoice.genkaritu = InterestRateUtility.roundUp( (payrollDetailsSub.getKaisya_sou_hutangaku() / total) * 100, 2);
        }
        return payrollDetailsInvoice;
    }
}