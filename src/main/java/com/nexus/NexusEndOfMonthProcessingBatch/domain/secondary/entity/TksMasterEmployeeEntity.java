package com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity;

import lombok.Data;

@Data
public class TksMasterEmployeeEntity {
    //社員ID
    Integer ID;
    //所属会社ID;
    Integer companyId;
    //名字
    String familyName;
    //名前
    String firstName;
    //PCメールアドレス
    String mailAddressPc;
    //担当営業ID
    Integer managerId;
    //担当営業氏名
    String managerEmployeeName;

    public String getEmployeeName() {
        return familyName + " " + firstName;
    }
}