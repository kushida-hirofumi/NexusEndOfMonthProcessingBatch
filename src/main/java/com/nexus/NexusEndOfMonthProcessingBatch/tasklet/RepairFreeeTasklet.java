package com.nexus.NexusEndOfMonthProcessingBatch.tasklet;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02Entity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusFreeeHumanResourcesAndLaborInfoEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusLinkingInformationBetweenTksAndFreeeEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.logger.CustomLogger;
import com.nexus.NexusEndOfMonthProcessingBatch.service.NexusEndOfMonthProcessingSheet02Service;
import com.nexus.NexusEndOfMonthProcessingBatch.service.NexusFreeeHumanResourcesAndLaborInfoService;
import com.nexus.NexusEndOfMonthProcessingBatch.service.NexusLinkingInformationBetweenTksAndFreeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Freee関連で一部情報が壊れている場合修復する処理を行う
 */
@Component
@RequiredArgsConstructor
public class RepairFreeeTasklet implements Tasklet {

    @Autowired
    CustomLogger customLogger;

    @Autowired
    NexusLinkingInformationBetweenTksAndFreeeService nexusLinkingInformationBetweenTksAndFreeeService;

    @Autowired
    NexusEndOfMonthProcessingSheet02Service nexusEndOfMonthProcessingSheet02Service;

    @Autowired
    NexusFreeeHumanResourcesAndLaborInfoService nexusFreeeHumanResourcesAndLaborInfoService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        customLogger.print("Freee関連の情報の修復を行う 開始");
        List<NexusFreeeHumanResourcesAndLaborInfoEntity> nexusFreeeHumanResourcesAndLaborInfoEntities = nexusFreeeHumanResourcesAndLaborInfoService.findAll();
        List<NexusLinkingInformationBetweenTksAndFreeeEntity> freeeEntities = nexusLinkingInformationBetweenTksAndFreeeService.findAll();

        StringBuilder stringBuilder = new StringBuilder();
        for(NexusEndOfMonthProcessingSheet02Entity entity : nexusEndOfMonthProcessingSheet02Service.findAll()) {
            if(entity.getFreeeId()==null) continue;
            if(repairFreeeId(entity, freeeEntities) || repairFreeeHumanResources(entity, nexusFreeeHumanResourcesAndLaborInfoEntities)) {
                stringBuilder.append("FreeeID: ").append(entity.getFreeeId()).append("  氏名: ").append(entity.getFamilyName()).append(entity.getFirstName()).append("  支払日: ").append(entity.getWorkingDate()).append("  基本給: ").append(entity.getBasicSalary()).append("  業務手当: ").append(entity.getBusinessAllowance()).append("  職務手当: ").append(entity.getJobAllowance()).append("\n");
                nexusEndOfMonthProcessingSheet02Service.update(entity);
            }
        }
        if(!stringBuilder.isEmpty()) customLogger.print(stringBuilder.toString());
        customLogger.print("Freee関連の情報の修復を行う 終了");
        return RepeatStatus.FINISHED;
    }

    /**
     * 月処理レコードに登録されたFreeeIDが古い場合新しいFreeeIdを格納する
     * @param entity    月末処理レコード
     * @param freeeEntities TKSの社員IDとFreeeIDの紐付け情報
     * @return  データを格納した・・true
     */
    boolean repairFreeeId(NexusEndOfMonthProcessingSheet02Entity entity, List<NexusLinkingInformationBetweenTksAndFreeeEntity> freeeEntities) {
        NexusLinkingInformationBetweenTksAndFreeeEntity freeeEntity = freeeEntities.stream().filter(node->node.getTksData().equals(entity.getEmployeeId())).findFirst().orElse(null);
        if(freeeEntity == null) return false;
        if(entity.getFreeeId().equals(freeeEntity.getFreeeData())) return false;
        entity.setFreeeId(freeeEntity.getFreeeData()); // 更新する
        return true;
    }

    /**
     * Freeeの人事情報がDBに入っている場合は月末処理レコードにデータを格納しておく
     * @param entity    月末処理レコード
     * @param nexusFreeeHumanResourcesAndLaborInfoEntities  Freee人事労務情報
     * @return  データを格納した・・true
     */
    boolean repairFreeeHumanResources(NexusEndOfMonthProcessingSheet02Entity entity, List<NexusFreeeHumanResourcesAndLaborInfoEntity> nexusFreeeHumanResourcesAndLaborInfoEntities) {
        if(entity.checkSourceInvoiceBtn()) return false;
        NexusFreeeHumanResourcesAndLaborInfoEntity nexusFreeeHumanResourcesAndLaborInfoEntity = nexusFreeeHumanResourcesAndLaborInfoEntities.stream()
                .filter(node -> node.getFreeeId().equals(entity.getFreeeId()) && node.getPayDate().getYear()==entity.getWorkingDate().getYear() && node.getPayDate().getMonth()==entity.getWorkingDate().getMonth())
                .findFirst().orElse(null);
        if(nexusFreeeHumanResourcesAndLaborInfoEntity == null) return false;
        if(entity.getBasicSalary()==nexusFreeeHumanResourcesAndLaborInfoEntity.getBasicSalary()
            && entity.getBusinessAllowance()==nexusFreeeHumanResourcesAndLaborInfoEntity.getBusinessAllowances()
            && entity.getJobAllowance()==nexusFreeeHumanResourcesAndLaborInfoEntity.getJobAllowance()) return false;
        entity.setBasicSalary(nexusFreeeHumanResourcesAndLaborInfoEntity.getBasicSalary());
        entity.setBusinessAllowance(nexusFreeeHumanResourcesAndLaborInfoEntity.getBusinessAllowances());
        entity.setJobAllowance(nexusFreeeHumanResourcesAndLaborInfoEntity.getJobAllowance());
        return true;
    }
}