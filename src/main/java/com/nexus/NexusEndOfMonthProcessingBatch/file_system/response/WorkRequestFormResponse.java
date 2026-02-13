package com.nexus.NexusEndOfMonthProcessingBatch.file_system.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 出金依頼書の情報のマップ
 */
@Getter
@Setter
public class WorkRequestFormResponse {
    String 出金額;
    String 備考1;
    String 相殺備考;
    String 請求額;
    String 適用先;
    String 入金予定日;
    String 備考2;
    String 作成者;
    String その他;
}