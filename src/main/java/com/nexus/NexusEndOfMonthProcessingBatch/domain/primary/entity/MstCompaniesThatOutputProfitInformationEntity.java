package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import lombok.Data;

/**
 * 利益情報を出力する会社情報のエンティティ
 */
@Data
public class MstCompaniesThatOutputProfitInformationEntity {
    //紐付ける会社ID
    Integer tksCompanyId;
    //会社名
    String companyName;
    //会社略称
    String companyShortName;
}