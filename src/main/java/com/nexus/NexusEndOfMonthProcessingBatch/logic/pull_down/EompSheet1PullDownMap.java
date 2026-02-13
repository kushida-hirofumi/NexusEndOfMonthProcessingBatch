package com.nexus.NexusEndOfMonthProcessingBatch.logic.pull_down;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessingInvoiceEntryEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.EompPullDownCompanyEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.EompPullDownEmployeeEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.custom.TksEompPullDownEmployeeEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * プルダウンマップ
 * 月末処理「請求書シート」
 */
@Getter
@Setter
public class EompSheet1PullDownMap extends EndOfMonthProcessingPullDownDataPrimitive {
    //氏名
    String employeeName;
    //契約元会社
    String contractorCompanyName;
    //折半　氏名
    String halfEmployeeName;
    //担当者
    String manageEmployeeName;
    //窓口営業　氏名
    String counterSalesEmployeeName;
    //書類作成者
    String documentCreatorEmployeeName;

    public static EompSheet1PullDownMap create(NexusEndOfProcessingInvoiceEntryEntity entity,
                                               List<TksEompPullDownEmployeeEntity> tksEompPullDownEmployeeEntityList,
                                               List<EompPullDownCompanyEntity> eompPullDownCompanyEntityList,
                                               List<EompPullDownEmployeeEntity> eompPullDownEmployeeEntityList) {
        EompSheet1PullDownMap result = new EompSheet1PullDownMap();
        result.recordId = entity.getId();
        for(TksEompPullDownEmployeeEntity entity1 : tksEompPullDownEmployeeEntityList) {
            if(entity.getEmployeeId()!=null && entity.getEmployeeId().equals(entity1.getEmployeeId())) {
                result.employeeName = entity1.getName();
            }
            if(entity.getHalfEmployeeId()!=null && entity.getHalfEmployeeId().equals(entity1.getEmployeeId())) {
                result.halfEmployeeName = entity1.getName();
            }
        }
        //氏名が抽出できなかった場合
        if(StringUtils.isBlank(result.employeeName)) {
            result.employeeName = entity.getEmployeeName();
        }
        for(EompPullDownCompanyEntity entity1 : eompPullDownCompanyEntityList) {
            if(entity.getContractorCompanyId()!=null && entity.getContractorCompanyId().equals(entity1.getCompanyId())) {
                result.contractorCompanyName = entity1.getName();
            }
        }
        for(EompPullDownEmployeeEntity entity1 : eompPullDownEmployeeEntityList) {
            if(entity.getManagerEmployeeId()!=null && entity.getManagerEmployeeId().equals(entity1.getEmployeeId())) {
                result.manageEmployeeName = entity1.getName();
            }
            if(entity.getCounterSalesEmployeeId()!=null && entity.getCounterSalesEmployeeId().equals(entity1.getEmployeeId())) {
                result.counterSalesEmployeeName = entity1.getName();
            }
            if(entity.getDocumentCreatorId()!=null && entity.getDocumentCreatorId().equals(entity1.getEmployeeId())) {
                result.documentCreatorEmployeeName = entity1.getName();
            }
        }
        return result;
    }
}