package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.seed;

/**
 * 「分析 & 種」を生成するのに利用するエンティティに実装するインターフェース
 */
public interface PayrollSeedInterface {
    //合計金額
    double maximumPrice();
}