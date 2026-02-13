package com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity;

import lombok.Data;

@Data
public class TksMasterCompanyEntity {
    //会社ID
    Integer ID;
    //会社名
    String companyName;
    //会社略称
    String companyShortName;
}