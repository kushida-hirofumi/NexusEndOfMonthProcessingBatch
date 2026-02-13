package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom;

import lombok.Getter;
import lombok.Setter;

/**
 * 超過控除ステータス
 */
@Getter
@Setter
public class ExcessDeductionStatusEntity {
    public enum Status {
        控除, 超過
    }

    //ID
    int id;
    //ステータス
    Status status;
    //数量
    double quantity;
    //単価
    int tanka;

    protected ExcessDeductionStatusEntity() {
    }

    public boolean equals(ExcessDeductionStatusEntity value) {
        return this.status.equals(value.status)
                && this.quantity==value.quantity
                && this.tanka==value.tanka;
    }
}