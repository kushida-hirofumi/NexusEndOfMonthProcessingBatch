package com.nexus.NexusEndOfMonthProcessingBatch.logic.pull_down;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessingEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.EompPullDownEmployeeEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.custom.TksEompPullDownEmployeeEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * プルダウンマップ
 * 月末処理「給与計算シート」
 */
@Getter
@Setter
public class EompSheet0PullDownMap extends EndOfMonthProcessingPullDownDataPrimitive {
    //氏名
    String employeeName;
    //入社日
    String hireDate;
    //書類作成氏名
    String documentCreatorEmployeeName;

    public static EompSheet0PullDownMap create(NexusEndOfProcessingEntity entity,
                                               List<TksEompPullDownEmployeeEntity> tksEompPullDownEmployeeEntityList,
                                               List<EompPullDownEmployeeEntity> eompPullDownEmployeeEntityList) {
        EompSheet0PullDownMap result = new EompSheet0PullDownMap();
        result.recordId = entity.getId();
        for(TksEompPullDownEmployeeEntity entity1 : tksEompPullDownEmployeeEntityList) {
            if(entity.getEmployeeId()!=null && entity.getEmployeeId().equals(entity1.getEmployeeId())) {
                result.employeeName = entity1.getName();
                result.hireDate = entity1.getHireDate();
            }
        }
        //氏名が抽出できなかった場合
        if(StringUtils.isBlank(result.employeeName)) {
            result.employeeName = entity.getEmployeeName();
        }
        for(EompPullDownEmployeeEntity entity1 : eompPullDownEmployeeEntityList) {
            if(entity.getDocumentCreatorEmployeeId()!=null && entity.getDocumentCreatorEmployeeId().equals(entity1.getEmployeeId())) {
                result.documentCreatorEmployeeName = entity1.getName();
            }
        }
        return result;
    }
}