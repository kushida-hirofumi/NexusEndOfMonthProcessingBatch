package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.supplementary_information;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfMonthProcessingSheet02Entity;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.pull_down.EompSheet1PullDownMap;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.pull_down.EompSheet2PullDownMap;

public class EompSupplementaryInfoDataSource {

    public static EompSupplementaryInfoData create(EompSheet2PullDownMap eompSheet2PullDownMap) {
        EompSupplementaryInfoData eompSupplementaryInfoData = new EompSupplementaryInfoData();
        eompSupplementaryInfoData.setEmployeeName(eompSheet2PullDownMap.getEmployeeName());
        eompSupplementaryInfoData.setFamilyName(eompSheet2PullDownMap.getFamilyName());
        eompSupplementaryInfoData.setFirstName(eompSheet2PullDownMap.getFirstName());
        eompSupplementaryInfoData.setContractorCompanyName(eompSheet2PullDownMap.getContractorCompanyName());
        eompSupplementaryInfoData.setCounterSalesEmployeeName(eompSheet2PullDownMap.getCounterSalesEmployeeName());
        eompSupplementaryInfoData.eompEmployeeNameObject = new EompEmployeeNameObject(eompSupplementaryInfoData);
        return eompSupplementaryInfoData;
    }

    public static EompSupplementaryInfoData create1(EompSheet1PullDownMap eompSheet1PullDownMap) {
        EompSupplementaryInfoData eompSupplementaryInfoData = new EompSupplementaryInfoData();
        eompSupplementaryInfoData.setEmployeeName(eompSheet1PullDownMap.getEmployeeName());
        eompSupplementaryInfoData.setContractorCompanyName(eompSheet1PullDownMap.getContractorCompanyName());
        eompSupplementaryInfoData.setCounterSalesEmployeeName(eompSheet1PullDownMap.getCounterSalesEmployeeName());
        String[] strList = eompSheet1PullDownMap.getEmployeeName().split(" ");
        eompSupplementaryInfoData.eompEmployeeNameObject = new EompEmployeeNameObject(eompSupplementaryInfoData);
        eompSupplementaryInfoData.familyName = strList[1];
        eompSupplementaryInfoData.firstName = strList[2];
        return eompSupplementaryInfoData;
    }

    public static EompSupplementaryInfoData create2(NexusEndOfMonthProcessingSheet02Entity entity, EompSheet2PullDownMap eompSheet2PullDownMap) {
        EompSupplementaryInfoData eompSupplementaryInfoData = new EompSupplementaryInfoData();
        eompSupplementaryInfoData.setEmployeeName(entity.getEmployeeName());
        eompSupplementaryInfoData.setFamilyName(entity.getFamilyName());
        eompSupplementaryInfoData.setFirstName(entity.getFirstName());
        eompSupplementaryInfoData.setContractorCompanyName(eompSheet2PullDownMap.getContractorCompanyName());
        eompSupplementaryInfoData.setCounterSalesEmployeeName(eompSheet2PullDownMap.getCounterSalesEmployeeName());
        eompSupplementaryInfoData.eompEmployeeNameObject = new EompEmployeeNameObject(eompSupplementaryInfoData);
        return eompSupplementaryInfoData;
    }

}