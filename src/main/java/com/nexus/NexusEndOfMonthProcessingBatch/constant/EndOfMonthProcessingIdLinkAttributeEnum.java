package com.nexus.NexusEndOfMonthProcessingBatch.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 月末処理のID紐付けテーブルの属性を定義
 */
@AllArgsConstructor
@Getter
public enum EndOfMonthProcessingIdLinkAttributeEnum {

    INVOICE_ID_LINK(0, "請求書リンク");

    private final Integer id;

    private final String name;


    public static EndOfMonthProcessingIdLinkAttributeEnum valueOfName(String name) {
        if(StringUtils.isBlank(name)) return null;
        for(EndOfMonthProcessingIdLinkAttributeEnum node : EndOfMonthProcessingIdLinkAttributeEnum.values()) {
            if(node.name.equals(name)) return node;
        }
        return null;
    }

}