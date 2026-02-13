package com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.kaikei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 【クラス分類】
 * レスポンス
 * *
 * 【api分類】
 * 会計API
 * *
 * 【データ名】
 * 事業所情報
 */
@Data
public class FreeeApiAccountingCompanyDto {
    @Data
    public static class Company {
        //事業所ID
        @JsonProperty("id")
        Integer id;
        //事業所名
        @JsonProperty("name")
        String name;
    }

    @JsonProperty("company")
    Company company;

}