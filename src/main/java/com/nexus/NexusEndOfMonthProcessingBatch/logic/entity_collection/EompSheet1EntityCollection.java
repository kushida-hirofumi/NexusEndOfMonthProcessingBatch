package com.nexus.NexusEndOfMonthProcessingBatch.logic.entity_collection;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.*;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_basic.PayrollDetailsBasic;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_invoice.PayrollDetailsInvoice;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_sub.PayrollDetailsSub;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.seed.PayrollSeed;
import lombok.Getter;

import java.util.List;

@Getter
public class EompSheet1EntityCollection {
    NexusEndOfProcessingInvoiceEntryEntity nexusEndOfProcessingInvoiceEntryEntity;
    List<NexusEndOfProcessInvoiceEntrySubEntity> nexusEndOfProcessInvoiceEntrySubEntityList;
    NexusEndOfMonthProcessingIdLinkEntity nexusEndOfMonthProcessingIdLinkEntity;
    NexusEndOfProcessingEntity nexusEndOfProcessingEntity;
    List<NexusEndOfProcessingSubEntity> nexusEndOfProcessingSubEntityList;
    InsuranceAndPensionTableEntity insuranceAndPensionTableEntity;

    PayrollDetailsBasic payrollDetailsBasic;
    PayrollDetailsSub payrollDetailsSub;
    PayrollSeed payrollDetailsSeed;
    PayrollDetailsInvoice payrollDetailsInvoice;
}