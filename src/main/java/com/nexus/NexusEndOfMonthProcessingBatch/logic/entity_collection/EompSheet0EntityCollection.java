package com.nexus.NexusEndOfMonthProcessingBatch.logic.entity_collection;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.*;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_basic.PayrollDetailsBasic;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_invoice.PayrollDetailsInvoice;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_sub.PayrollDetailsSub;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.seed.PayrollSeed;
import lombok.Getter;

import java.util.List;

@Getter
public class EompSheet0EntityCollection {
    NexusEndOfProcessingEntity nexusEndOfProcessingEntity;
    List<NexusEndOfProcessingSubEntity> nexusEndOfProcessingSubEntityList;
    NexusEndOfProcessingSubAdjustmentAmountEntity adjustmentAmountEntity;
    List<NexusEndOfMonthProcessingIdLinkEntity> nexusEndOfMonthProcessingIdLinkEntityList;
    List<EompSheet1EntityCollection> invoiceList;
    InsuranceAndPensionTableEntity insuranceAndPensionTableEntity;
    List<PayrollSeed> payrollDetailsSeedList;
    PayrollDetailsBasic payrollDetailsBasic;
    PayrollDetailsSub payrollDetailsSub;
    PayrollDetailsInvoice payrollDetailsInvoice;
}