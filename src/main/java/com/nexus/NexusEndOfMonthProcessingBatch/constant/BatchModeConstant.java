package com.nexus.NexusEndOfMonthProcessingBatch.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BatchModeConstant {
    //テスト
    none("test"),

    //アクセストークン更新
    refreshToken("refreshToken"),

    //FreeeApiから情報を取り込む
    importInfoFromFreeeApi("importInfoFromFreeeApi"),

    ;

    final String key;

    public static BatchModeConstant valueOfKey(String key) {
        for(BatchModeConstant node : BatchModeConstant.values()) {
            if(node.key.equals(key)) return node;
        }
        return null;
    }
}