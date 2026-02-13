package com.nexus.NexusEndOfMonthProcessingBatch.logic.pull_down;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02Entity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.EompPullDownCompanyEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.EompPullDownEmployeeEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.custom.TksEompPullDownEmployeeEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * プルダウンマップ
 * 月末処理「月末処理シート」
 */
@Getter
@Setter
public class EompSheet2PullDownMap extends EndOfMonthProcessingPullDownDataPrimitive {
    //所属会社ID
    Integer affiliationCompanyId;
    //氏名
    String employeeName;
    //氏名(苗字)
    String familyName;
    //氏名(名前)
    String firstName;
    //入社日
    String hireDate;
    //契約元会社
    String contractorCompanyName;
    //書類作成者
    String documentCreatorEmployeeName;
    //折半　所属
    String halfAffiliation;
    //折半　氏名
    String halfEmployeeName;
    //窓口営業氏名
    String counterSalesEmployeeName;


    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("recordId").append("=").append(recordId).append(", ");
        if(affiliationCompanyId!=null) stringBuilder.append("affiliationCompanyId").append("=").append(affiliationCompanyId).append(", ");
        if(!StringUtils.isBlank(employeeName)) stringBuilder.append("employeeName").append("=").append(employeeName).append(", ");
        if(!StringUtils.isBlank(familyName)) stringBuilder.append("familyName").append("=").append(familyName).append(", ");
        if(!StringUtils.isBlank(firstName)) stringBuilder.append("firstName").append("=").append(firstName).append(", ");
        if(!StringUtils.isBlank(hireDate)) stringBuilder.append("hireDate").append("=").append(hireDate).append(", ");
        if(!StringUtils.isBlank(contractorCompanyName)) stringBuilder.append("contractorCompanyName").append("=").append(contractorCompanyName).append(", ");
        if(!StringUtils.isBlank(documentCreatorEmployeeName)) stringBuilder.append("documentCreatorEmployeeName").append("=").append(documentCreatorEmployeeName).append(", ");
        if(!StringUtils.isBlank(halfAffiliation)) stringBuilder.append("halfAffiliation").append("=").append(halfAffiliation).append(", ");
        if(!StringUtils.isBlank(halfEmployeeName)) stringBuilder.append("halfEmployeeName").append("=").append(halfEmployeeName).append(", ");
        if(!StringUtils.isBlank(counterSalesEmployeeName)) stringBuilder.append("counterSalesEmployeeName").append("=").append(counterSalesEmployeeName);
        return "{" + stringBuilder + "}";
    }


    public static EompSheet2PullDownMap create(NexusEndOfMonthProcessingSheet02Entity nexusEndOfMonthProcessingSheet02Entity,
                                 List<TksEompPullDownEmployeeEntity> tksEompPullDownEmployeeEntityList,
                                 List<EompPullDownCompanyEntity> eompPullDownCompanyEntityList,
                                 List<EompPullDownEmployeeEntity> eompPullDownEmployeeEntityList) {
        EompSheet2PullDownMap eompSheet2PullDownMap = new EompSheet2PullDownMap();
        eompSheet2PullDownMap.setRecordId(nexusEndOfMonthProcessingSheet02Entity.getId());

        for(TksEompPullDownEmployeeEntity tksEompPullDownEmployeeEntity : tksEompPullDownEmployeeEntityList) {
            if (nexusEndOfMonthProcessingSheet02Entity.getEmployeeId()!=null && tksEompPullDownEmployeeEntity.getEmployeeId()==nexusEndOfMonthProcessingSheet02Entity.getEmployeeId()) {
                eompSheet2PullDownMap.setAffiliationCompanyId(tksEompPullDownEmployeeEntity.getAffiliationCompanyId());
                eompSheet2PullDownMap.setEmployeeName(tksEompPullDownEmployeeEntity.getName());
                eompSheet2PullDownMap.setFamilyName(tksEompPullDownEmployeeEntity.getFamilyName());
                eompSheet2PullDownMap.setFirstName(tksEompPullDownEmployeeEntity.getFirstName());
                eompSheet2PullDownMap.setHireDate(tksEompPullDownEmployeeEntity.getHireDate());
            }
            if (nexusEndOfMonthProcessingSheet02Entity.getHalfEmployeeId()!=null && tksEompPullDownEmployeeEntity.getEmployeeId()==nexusEndOfMonthProcessingSheet02Entity.getHalfEmployeeId()) {
                eompSheet2PullDownMap.setHalfEmployeeName(tksEompPullDownEmployeeEntity.getName());
            }
        }

        for(EompPullDownCompanyEntity eompPullDownCompanyEntity : eompPullDownCompanyEntityList) {
            if(nexusEndOfMonthProcessingSheet02Entity.getContractorCompanyId()!=null && eompPullDownCompanyEntity.getCompanyId()==nexusEndOfMonthProcessingSheet02Entity.getContractorCompanyId()) eompSheet2PullDownMap.setContractorCompanyName(eompPullDownCompanyEntity.getName());
            if(nexusEndOfMonthProcessingSheet02Entity.getHalfAffiliationCompanyId()!=null && eompPullDownCompanyEntity.getCompanyId()==nexusEndOfMonthProcessingSheet02Entity.getHalfAffiliationCompanyId()) eompSheet2PullDownMap.setHalfAffiliation(eompPullDownCompanyEntity.getName());
        }

        for(EompPullDownEmployeeEntity eompPullDownEmployeeEntity : eompPullDownEmployeeEntityList) {
            if(nexusEndOfMonthProcessingSheet02Entity.getDocumentCreatorEmployeeId()!=null && eompPullDownEmployeeEntity.getEmployeeId()==nexusEndOfMonthProcessingSheet02Entity.getDocumentCreatorEmployeeId()) eompSheet2PullDownMap.setDocumentCreatorEmployeeName(eompPullDownEmployeeEntity.getName());
            if(nexusEndOfMonthProcessingSheet02Entity.getCounterSalesEmployeeId()!=null && eompPullDownEmployeeEntity.getEmployeeId()==nexusEndOfMonthProcessingSheet02Entity.getCounterSalesEmployeeId()) eompSheet2PullDownMap.setCounterSalesEmployeeName(eompPullDownEmployeeEntity.getName());
        }
        return eompSheet2PullDownMap;
    }
}