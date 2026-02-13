package com.nexus.NexusEndOfMonthProcessingBatch.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 月末処理機能
 * 利率計算を行うユーティリティ
 */
public class InterestRateUtility {

    public static double percent(double value, double percent) {
        if(!Double.isFinite(value) || !Double.isFinite(percent)) return 0;
        return (value / 100) * percent;
    }

    /**
     * 切り捨て計算
     * @param value     計算する値
     * @param newScale  切り捨てる小数点以下の位
     * @return  出力値
     */
    public static double roundDown(double value, int newScale) {
        if(!Double.isFinite(value) || value == 0) return 0;
        return BigDecimal.valueOf(value).setScale(newScale, RoundingMode.DOWN).doubleValue();
    }

    /**
     * 四捨五入計算
     * @param value     計算する値
     * @param newScale  四捨五入する小数点以下の位
     * @return  出力値
     */
    public static double roundUp(double value, int newScale) {
        if(!Double.isFinite(value) || value == 0) return 0;
        return BigDecimal.valueOf(value).setScale(newScale, RoundingMode.HALF_UP).doubleValue();
    }

}