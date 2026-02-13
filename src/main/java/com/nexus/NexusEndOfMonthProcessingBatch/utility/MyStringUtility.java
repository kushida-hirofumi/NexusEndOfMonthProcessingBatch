package com.nexus.NexusEndOfMonthProcessingBatch.utility;

import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;

public class MyStringUtility {

    static final String HalfWidthEmptyString = " ";

    static final String DoubleByteEmptyString = "　";

    public static String deleteSpace(String text) {
        if (StringUtils.isBlank(text)) return "";
        text = text.replace(HalfWidthEmptyString, "");
        return text.replace(DoubleByteEmptyString, "");
    }

    /**
     * 文字列が数値に変換できるか判定
     * @param s 対象文字列
     * @return  変換可能ならtrue
     */
    public static boolean isNumeric(String s) {
        if(StringUtils.isBlank(s)) return false;
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static double parseDoubleByString(String src) {
        return MyStringUtility.isNumeric(src) ? Double.parseDouble(src) : 0;
    }

    public static int parseIntByString(String src) {
        return MyStringUtility.isNumeric(src) ? (int) Double.parseDouble(src) : 0;
    }

    /**
     * 数値をカンマ入りの数字に変換する
     * @param value         対象文字列
     * @return  変換後の文字列
     */
    public static String translateToNumberCommaByInteger(int value) {
        try {
            String numberText = NumberFormat.getNumberInstance().format(value);
            return String.format("%s", numberText);
        } catch (NumberFormatException e) {
            return "";
        }
    }
    public static String translateToNumberCommaByDouble(double value) {
        try {
            String numberText = NumberFormat.getNumberInstance().format(value);
            return String.format("%s", numberText);
        } catch (NumberFormatException e) {
            return "";
        }
    }

}