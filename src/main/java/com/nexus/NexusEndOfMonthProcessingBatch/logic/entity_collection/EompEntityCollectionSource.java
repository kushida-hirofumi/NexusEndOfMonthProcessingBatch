package com.nexus.NexusEndOfMonthProcessingBatch.logic.entity_collection;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.InsuranceAndPensionTableEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02Entity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02SubIrregularEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.TksMasterCompanyEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.TksMasterEmployeeEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.interface_code.EndOfMonthProcessingEntityPrimitive;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.ExcessDeductionStatusEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.supplementary_information.EompEmployeeNameObject;
import com.nexus.NexusEndOfMonthProcessingBatch.service.InsuranceAndPensionTableService;
import com.nexus.NexusEndOfMonthProcessingBatch.service.NexusEndOfMonthProcessingSheet02SubIrregularService;
import com.nexus.NexusEndOfMonthProcessingBatch.service.TksMasterCompanyService;
import com.nexus.NexusEndOfMonthProcessingBatch.service.TksMasterEmployeeService;
import com.nexus.NexusEndOfMonthProcessingBatch.service.custom.EompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EompEntityCollectionSource {

    @Autowired
    NexusEndOfMonthProcessingSheet02SubIrregularService nexusEndOfMonthProcessingSheet02SubIrregularService;

    @Autowired
    InsuranceAndPensionTableService insuranceAndPensionTableService;

    @Autowired
    TksMasterEmployeeService tksMasterEmployeeService;

    @Autowired
    TksMasterCompanyService tksMasterCompanyService;

    @Autowired
    EompService eompService;

    public List<EompSheet2EntityCollection> createEndOfMonthProcessingSheet2EntityCollectionList(List<NexusEndOfMonthProcessingSheet02Entity> entityList) {
        List<EompSheet2EntityCollection> result = new ArrayList<>();
        List<NexusEndOfMonthProcessingSheet02SubIrregularEntity> irregularEntities = nexusEndOfMonthProcessingSheet02SubIrregularService.findByParentEntities(entityList);
        EndOfMonthProcessingEntityPrimitive[] endOfMonthProcessingEntityPrimitives = entityList.toArray(new EndOfMonthProcessingEntityPrimitive[0]);
        List<InsuranceAndPensionTableEntity> insuranceAndPensionTableEntityList = insuranceAndPensionTableService.findByEntityList(endOfMonthProcessingEntityPrimitives);
        List<TksMasterEmployeeEntity> tksMasterEmployeeEntityList = tksMasterEmployeeService.findByIds(entityList.stream().map(NexusEndOfMonthProcessingSheet02Entity::getEmployeeId).filter(Objects::nonNull).distinct().collect(Collectors.toList()));
        List<TksMasterCompanyEntity> tksMasterCompanyEntityList = tksMasterCompanyService.findByIds(tksMasterEmployeeEntityList.stream().map(TksMasterEmployeeEntity::getCompanyId).filter(Objects::nonNull).distinct().collect(Collectors.toList()));
        List<ExcessDeductionStatusEntity> excessDeductionStatusEntityList = eompService.findExcessDeductionStatusData(entityList.stream().map(NexusEndOfMonthProcessingSheet02Entity::getId).distinct().collect(Collectors.toList()));
        for(NexusEndOfMonthProcessingSheet02Entity entity : entityList) {
            EompSheet2EntityCollection collection = new EompSheet2EntityCollection();
            collection.nexusEndOfMonthProcessingSheet02Entity = entity;
            collection.irregularEntities = irregularEntities.stream().filter(node->node.getParentId().equals(entity.getId())).collect(Collectors.toList());
            collection.insuranceAndPensionTableEntity = insuranceAndPensionTableService.extract(insuranceAndPensionTableEntityList, entity);
            TksMasterEmployeeEntity tksMasterEmployeeEntity = tksMasterEmployeeEntityList.stream().filter(node->node.getID().equals(entity.getEmployeeId())).findFirst().orElse(null);
            if(tksMasterEmployeeEntity!=null) {
                TksMasterCompanyEntity tksMasterCompanyEntity = tksMasterCompanyEntityList.stream().filter(node -> node.getID().equals(tksMasterEmployeeEntity.getCompanyId())).findFirst().orElse(null);
                String employeeName = tksMasterEmployeeEntity.getFamilyName() + " " + tksMasterEmployeeEntity.getFirstName();
                collection.eompEmployeeNameObject = new EompEmployeeNameObject(
                        (tksMasterCompanyEntity!=null ? tksMasterCompanyEntity.getCompanyShortName() : "") + " " + employeeName,
                        employeeName
                );
            }
            collection.excessDeductionStatusEntity = excessDeductionStatusEntityList.stream().filter(node -> node.getId()==entity.getId()).findFirst().orElse(null);
            result.add(collection);
        }
        return result;
    }

}