package com.nexus.NexusEndOfMonthProcessingBatch.logic.entity_collection;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.InsuranceAndPensionTableEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02Entity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02SubIrregularEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.ExcessDeductionStatusEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.supplementary_information.EompEmployeeNameObject;
import lombok.Getter;

import java.util.List;

@Getter
public class EompSheet2EntityCollection {
    NexusEndOfMonthProcessingSheet02Entity nexusEndOfMonthProcessingSheet02Entity;
    List<NexusEndOfMonthProcessingSheet02SubIrregularEntity> irregularEntities;
    InsuranceAndPensionTableEntity insuranceAndPensionTableEntity;
    EompEmployeeNameObject eompEmployeeNameObject;
    ExcessDeductionStatusEntity excessDeductionStatusEntity;
}