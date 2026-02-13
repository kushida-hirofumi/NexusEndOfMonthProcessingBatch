package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom;

import lombok.Getter;
import lombok.Setter;

/**
 * 月末処理の会社プルダウン
 */
@Getter
@Setter
public class EompPullDownCompanyEntity {
    //会社ID
    int companyId;
    //会社名
    String name;
    //会社略称
    String companyShortName;
    //表示名
    String viewName;
    /**
     * TKS上での会社ID
     * TKSの会社情報と紐づけている場合
     */
    int tksCompanyId;
}