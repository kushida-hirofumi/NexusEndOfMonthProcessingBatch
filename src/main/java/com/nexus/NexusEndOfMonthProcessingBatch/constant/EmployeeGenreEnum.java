package com.nexus.NexusEndOfMonthProcessingBatch.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 社員区分管理
 * 社員一覧画面などで利用する社員区分項目を定義する
 */
@AllArgsConstructor
@Getter
public enum EmployeeGenreEnum {

    ENGINEER("技術者"),
    PROUD_ENGINEER_ONLY("社内技術者のみ"),
    BP_ENGINEER_ONLY("BP技術者のみ"),
    SALESMAN("営業");

    private final String name;

    public static EmployeeGenreEnum valueOfName(String value) {
        for (EmployeeGenreEnum node : EmployeeGenreEnum.values()) {
            if (node.name.equals(value)) return node;
        }
        return null;
    }

}