package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.supplementary_information;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class EompEmployeeNameObject {
    //所属会社の略称+氏名
    String jointName;
    //氏名
    String employeeSimpleName;

    public EompEmployeeNameObject(String jointName, String employeeSimpleName) {
        this.jointName = StringUtils.isBlank(jointName) ? "" : jointName;
        this.employeeSimpleName = StringUtils.isBlank(employeeSimpleName) ? "" : employeeSimpleName;
    }

    public EompEmployeeNameObject(EompSupplementaryInfoData eompSupplementaryInfoData) {
        this.jointName = eompSupplementaryInfoData.getEmployeeName();
        this.employeeSimpleName = eompSupplementaryInfoData.getFamilyName() + " " + eompSupplementaryInfoData.getFirstName();
    }
}